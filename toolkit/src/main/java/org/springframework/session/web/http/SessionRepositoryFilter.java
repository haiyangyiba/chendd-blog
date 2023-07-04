/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.session.web.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Switches the {@link HttpSession} implementation to be backed by a
 * {@link Session}.
 *
 * The {@link SessionRepositoryFilter} wraps the
 * {@link HttpServletRequest} and overrides the methods to get an
 * {@link HttpSession} to be backed by a
 * {@link Session} returned by the
 * {@link SessionRepository}.
 *
 * The {@link SessionRepositoryFilter} uses a {@link HttpSessionIdResolver} (default
 * {@link CookieHttpSessionIdResolver}) to bridge logic between an
 * {@link HttpSession} and the
 * {@link Session} abstraction. Specifically:
 *
 * <ul>
 * <li>The session id is looked up using
 * {@link HttpSessionIdResolver#resolveSessionIds(HttpServletRequest)}
 * . The default is to look in a cookie named SESSION.</li>
 * <li>The session id of newly created {@link Session} is sent
 * to the client using
 * <li>The client is notified that the session id is no longer valid with
 * {@link HttpSessionIdResolver#expireSession(HttpServletRequest, HttpServletResponse)}
 * </li>
 * </ul>
 *
 * <p>
 * The SessionRepositoryFilter must be placed before any Filter that access the
 * HttpSession or that might commit the response to ensure the session is overridden and
 * persisted properly.
 * </p>
 *
 * @param <S> the {@link Session} type.
 * @since 1.0
 * @author Rob Winch
 * @author Vedran Pavic
 * @author Josh Cummings
 */
@Order(SessionRepositoryFilter.DEFAULT_ORDER)
public class SessionRepositoryFilter<S extends Session> extends OncePerRequestFilter {

	private static final String SESSION_LOGGER_NAME = SessionRepositoryFilter.class.getName().concat(".SESSION_LOGGER");

	private static final Log SESSION_LOGGER = LogFactory.getLog(SESSION_LOGGER_NAME);

	/**
	 * The session repository request attribute name.
	 */
	public static final String SESSION_REPOSITORY_ATTR = SessionRepository.class.getName();

	/**
	 * Invalid session id (not backed by the session repository) request attribute name.
	 */
	public static final String INVALID_SESSION_ID_ATTR = SESSION_REPOSITORY_ATTR + ".invalidSessionId";

	private static final String CURRENT_SESSION_ATTR = SESSION_REPOSITORY_ATTR + ".CURRENT_SESSION";

	/**
	 * The default filter order.
	 */
	public static final int DEFAULT_ORDER = Integer.MIN_VALUE + 50;

	private final SessionRepository<S> sessionRepository;

	private HttpSessionIdResolver httpSessionIdResolver = new CookieHttpSessionIdResolver();

	/**
	 * Creates a new instance.
	 * @param sessionRepository the <code>SessionRepository</code> to use. Cannot be null.
	 */
	public SessionRepositoryFilter(SessionRepository<S> sessionRepository) {
		if (sessionRepository == null) {
			throw new IllegalArgumentException("sessionRepository cannot be null");
		}
		this.sessionRepository = sessionRepository;
	}

	/**
	 * Sets the {@link HttpSessionIdResolver} to be used. The default is a
	 * {@link CookieHttpSessionIdResolver}.
	 * @param httpSessionIdResolver the {@link HttpSessionIdResolver} to use. Cannot be
	 * null.
	 */
	public void setHttpSessionIdResolver(HttpSessionIdResolver httpSessionIdResolver) {
		if (httpSessionIdResolver == null) {
			throw new IllegalArgumentException("httpSessionIdResolver cannot be null");
		}
		this.httpSessionIdResolver = httpSessionIdResolver;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		request.setAttribute(SESSION_REPOSITORY_ATTR, this.sessionRepository);

		SessionRepositoryRequestWrapper wrappedRequest = new SessionRepositoryRequestWrapper(request, response);
		SessionRepositoryResponseWrapper wrappedResponse = new SessionRepositoryResponseWrapper(wrappedRequest,
				response);
		///验证spring session 过滤器的拦截路径
		//System.out.println("SessionRepositoryFilter：" + request.getRequestURL().toString() + "---" + request.getRequestURI());
		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		}
		finally {
			wrappedRequest.commitSession();
		}
	}

	@Override
	protected void doFilterNestedErrorDispatch(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		doFilterInternal(request, response, filterChain);
	}

	/**
	 * Allows ensuring that the session is saved if the response is committed.
	 *
	 * @author Rob Winch
	 * @since 1.0
	 */
	private final class SessionRepositoryResponseWrapper extends OnCommittedResponseWrapper {

		private final SessionRepositoryRequestWrapper request;

		/**
		 * Create a new {@link SessionRepositoryResponseWrapper}.
		 * @param request the request to be wrapped
		 * @param response the response to be wrapped
		 */
		SessionRepositoryResponseWrapper(SessionRepositoryRequestWrapper request, HttpServletResponse response) {
			super(response);
			if (request == null) {
				throw new IllegalArgumentException("request cannot be null");
			}
			this.request = request;
		}

		@Override
		protected void onResponseCommitted() {
			this.request.commitSession();
		}

	}

	/**
	 * A {@link HttpServletRequest} that retrieves the
	 * {@link HttpSession} using a
	 * {@link SessionRepository}.
	 *
	 * @author Rob Winch
	 * @since 1.0
	 */
	private final class SessionRepositoryRequestWrapper extends HttpServletRequestWrapper {

		private final HttpServletResponse response;

		private S requestedSession;

		private boolean requestedSessionCached;

		private String requestedSessionId;

		private Boolean requestedSessionIdValid;

		private boolean requestedSessionInvalidated;

		private SessionRepositoryRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
			super(request);
			this.response = response;
		}

		/**
		 * Uses the {@link HttpSessionIdResolver} to write the session id to the response
		 * and persist the Session.
		 */
		private void commitSession() {
			HttpSessionWrapper wrappedSession = getCurrentSession();
			if (wrappedSession == null) {
				if (isInvalidateClientSession()) {
					SessionRepositoryFilter.this.httpSessionIdResolver.expireSession(this, this.response);
				}
			}
			else {
				S session = wrappedSession.getSession();
				clearRequestedSessionCache();
				SessionRepositoryFilter.this.sessionRepository.save(session);
				String sessionId = session.getId();
				if (!isRequestedSessionIdValid() || !sessionId.equals(getRequestedSessionId())) {
					SessionRepositoryFilter.this.httpSessionIdResolver.setSessionId(this, this.response, sessionId);
				}
			}
		}

		@SuppressWarnings("unchecked")
		private HttpSessionWrapper getCurrentSession() {
			return (HttpSessionWrapper) getAttribute(CURRENT_SESSION_ATTR);
		}

		private void setCurrentSession(HttpSessionWrapper currentSession) {
			if (currentSession == null) {
				removeAttribute(CURRENT_SESSION_ATTR);
			}
			else {
				setAttribute(CURRENT_SESSION_ATTR, currentSession);
			}
		}

		@Override
		@SuppressWarnings("unused")
		public String changeSessionId() {
			HttpSession session = getSession(false);

			if (session == null) {
				throw new IllegalStateException(
						"Cannot change session ID. There is no session associated with this request.");
			}

			return getCurrentSession().getSession().changeSessionId();
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			if (this.requestedSessionIdValid == null) {
				S requestedSession = getRequestedSession();
				if (requestedSession != null) {
					requestedSession.setLastAccessedTime(Instant.now());
				}
				return isRequestedSessionIdValid(requestedSession);
			}
			return this.requestedSessionIdValid;
		}

		private boolean isRequestedSessionIdValid(S session) {
			if (this.requestedSessionIdValid == null) {
				this.requestedSessionIdValid = session != null;
			}
			return this.requestedSessionIdValid;
		}

		private boolean isInvalidateClientSession() {
			return getCurrentSession() == null && this.requestedSessionInvalidated;
		}

		@Override
		public HttpSessionWrapper getSession(boolean create) {
			HttpSessionWrapper currentSession = getCurrentSession();
			if (currentSession != null) {
				return currentSession;
			}
			S requestedSession = getRequestedSession();
			if (requestedSession != null) {
				if (getAttribute(INVALID_SESSION_ID_ATTR) == null) {
					requestedSession.setLastAccessedTime(Instant.now());
					this.requestedSessionIdValid = true;
					currentSession = new HttpSessionWrapper(requestedSession, getServletContext());
					currentSession.markNotNew();
					setCurrentSession(currentSession);
					return currentSession;
				}
			}
			else {
				// This is an invalid session id. No need to ask again if
				// request.getSession is invoked for the duration of this request
				if (SESSION_LOGGER.isDebugEnabled()) {
					SESSION_LOGGER.debug(
							"No session found by id: Caching result for getSession(false) for this HttpServletRequest.");
				}
				setAttribute(INVALID_SESSION_ID_ATTR, "true");
			}
			if (!create) {
				return null;
			}
			if (SESSION_LOGGER.isDebugEnabled()) {
				SESSION_LOGGER.debug(
						"A new session was created. To help you troubleshoot where the session was created we provided a StackTrace (this is not an error). You can prevent this from appearing by disabling DEBUG logging for "
								+ SESSION_LOGGER_NAME,
						new RuntimeException("For debugging purposes only (not an error)"));
			}
			S session = SessionRepositoryFilter.this.sessionRepository.createSession();
			session.setLastAccessedTime(Instant.now());
			currentSession = new HttpSessionWrapper(session, getServletContext());
			setCurrentSession(currentSession);
			return currentSession;
		}

		@Override
		public HttpSessionWrapper getSession() {
			return getSession(true);
		}

		@Override
		public String getRequestedSessionId() {
			if (this.requestedSessionId == null) {
				getRequestedSession();
			}
			return this.requestedSessionId;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			RequestDispatcher requestDispatcher = super.getRequestDispatcher(path);
			return new SessionCommittingRequestDispatcher(requestDispatcher);
		}

		private S getRequestedSession() {
			if (!this.requestedSessionCached) {
				List<String> sessionIds = SessionRepositoryFilter.this.httpSessionIdResolver.resolveSessionIds(this);
				for (String sessionId : sessionIds) {
					if (this.requestedSessionId == null) {
						this.requestedSessionId = sessionId;
					}
					S session = SessionRepositoryFilter.this.sessionRepository.findById(sessionId);
					if (session != null) {
						this.requestedSession = session;
						this.requestedSessionId = sessionId;
						break;
					}
				}
				this.requestedSessionCached = true;
			}
			return this.requestedSession;
		}

		private void clearRequestedSessionCache() {
			this.requestedSessionCached = false;
			this.requestedSession = null;
			this.requestedSessionId = null;
		}

		/**
		 * Allows creating an HttpSession from a Session instance.
		 *
		 * @author Rob Winch
		 * @since 1.0
		 */
		private final class HttpSessionWrapper extends HttpSessionAdapter<S> {

			HttpSessionWrapper(S session, ServletContext servletContext) {
				super(session, servletContext);
			}

			@Override
			public void invalidate() {
				super.invalidate();
				SessionRepositoryRequestWrapper.this.requestedSessionInvalidated = true;
				setCurrentSession(null);
				clearRequestedSessionCache();
				SessionRepositoryFilter.this.sessionRepository.deleteById(getId());
			}

		}

		/**
		 * Ensures session is committed before issuing an include.
		 *
		 * @since 1.3.4
		 */
		private final class SessionCommittingRequestDispatcher implements RequestDispatcher {

			private final RequestDispatcher delegate;

			SessionCommittingRequestDispatcher(RequestDispatcher delegate) {
				this.delegate = delegate;
			}

			@Override
			public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
				this.delegate.forward(request, response);
			}

			@Override
			public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
				SessionRepositoryRequestWrapper.this.commitSession();
				this.delegate.include(request, response);
			}

		}

	}

}

package cn.chendd.core.mail;

/**
 * MailRuntimeException
 *
 * @author chendd
 * @date 2022/4/18 22:23
 */
public class MailRuntimeException extends RuntimeException {

    public MailRuntimeException() {
        super();
    }

    public MailRuntimeException(String message) {
        super(message);
    }

    public MailRuntimeException(Throwable cause) {
        super(cause);
    }

    public MailRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

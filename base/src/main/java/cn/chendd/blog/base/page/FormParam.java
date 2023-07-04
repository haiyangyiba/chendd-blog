package cn.chendd.blog.base.page;

import org.springframework.beans.BeanUtils;

/**
 * 参数类封装
 *
 * @author chendd
 * @date 2019/10/26 21:31
 */
public class FormParam<T> {

    public T convert(T source){
        BeanUtils.copyProperties(this , source);
        return source;
    }

}

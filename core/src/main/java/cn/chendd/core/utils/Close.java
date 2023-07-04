package cn.chendd.core.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO流Close工具类
 *
 * @author chendd
 * @date 2020/1/1 10:24
 */
public class Close {

    public static void close(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
                closeable = null;
            } catch (IOException e) {}
        }
    }

    public static void close(Closeable ...closeables){
        if(closeables != null && closeables.length > 0){
            for (Closeable closeable : closeables) {
                close(closeable);
            }
        }
    }

}

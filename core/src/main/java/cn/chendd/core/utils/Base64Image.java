package cn.chendd.core.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * Base64与图片的互转
 *
 * @author chendd
 * @date 2020/6/20 16:43
 */
public class Base64Image {

    public static String convert2File(String base64Text , String destFilePath) throws IOException {
        try (OutputStream out = new FileOutputStream(destFilePath)){
            byte bytes[] = Base64.decodeBase64(base64Text);
            File destFile = new File(destFilePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            for (int i = 0, lens = bytes.length; i < lens; i++) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            out.write(bytes);
        } catch (IOException e) {
            throw e;
        }
        return destFilePath;
    }

}

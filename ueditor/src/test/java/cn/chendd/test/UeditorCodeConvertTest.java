package cn.chendd.test;

import cn.chendd.ueditor.UeditorCodeConvert;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试将Ueditor中使用的代码编辑器转换为Prism插件语法支持的格式
 *
 * @author chendd
 * @date 2022/3/11 16:28
 */
public class UeditorCodeConvertTest {

    @Test
    public void convert() throws Exception {
        File file = new File(getClass().getResource("/test-code-convert.html").getFile());
        String content = FileUtils.readFileToString(file, CharsetNames.UTF_8);
        System.out.println(UeditorCodeConvert.code2Prims(content));
    }

}

package cn.chendd.test;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试读取生成ueditor.all.js与ueditor.all.css
 * 从源代码中按顺序读取
 * @author chendd
 * @date 2020/8/17 14:34
 */
public class UeditorAllTest {

    @Test
    public void testUeditorAllJs() throws Exception {

        File apiFile = new File(getClass().getResource("/statics/plugins/ueditor/editor_api.js").getPath());
        String apiContent = FileUtils.readFileToString(apiFile , Charsets.UTF_8);
        Matcher libMatch = Pattern.compile("'(.*\\.js)'" , Pattern.CASE_INSENSITIVE).matcher(apiContent);
        List<String> fileList = Lists.newArrayList();
        while(libMatch.find()) {
            fileList.add(libMatch.group(1));
        }
        File folder = new File(getClass().getResource("/statics/plugins/ueditor/dist/").getPath());
        File ueditorAllJs = new File(apiFile.getParent() , "ueditor.all.js");
        if(ueditorAllJs.exists()) {
            ueditorAllJs.delete();
        }
        for (String fileName : fileList) {
            File srcFile = new File(folder, fileName);
            String fileContent = FileUtils.readFileToString(srcFile , Charsets.UTF_8);
            String fileSplit = "\r\n/*" + srcFile.getName() + "*/\r\n";
            FileUtils.writeStringToFile(ueditorAllJs , fileSplit , Charsets.UTF_8 , true);
            FileUtils.writeStringToFile(ueditorAllJs , fileContent , Charsets.UTF_8 , true);
        }
        System.out.println("ueditor.all.js文件生成完毕，路径为：" + ueditorAllJs.getAbsolutePath());
    }

    @Test
    public void testUeditorAllCss() throws Exception {
        File apiFile = new File(getClass().getResource("/statics/plugins/ueditor/themes/default/_css/ueditor.css").getPath());
        String cssContent = FileUtils.readFileToString(apiFile , Charsets.UTF_8);
        Matcher libMatch = Pattern.compile("\"(.*\\.css)\"" , Pattern.CASE_INSENSITIVE).matcher(cssContent);
        List<String> fileList = Lists.newArrayList();
        while(libMatch.find()) {
            fileList.add(libMatch.group(1));
        }
        File destFolder = new File(getClass().getResource("/statics/plugins/ueditor/").getPath());
        File ueditorAllCss = new File(destFolder , "ueditor.all.css");
        if(ueditorAllCss.exists()) {
            ueditorAllCss.delete();
        }
        for (String fileName : fileList) {
            File srcFile = new File(apiFile.getParent(), fileName);
            String fileContent = FileUtils.readFileToString(srcFile , Charsets.UTF_8);
            String fileSplit = "\r\n/*" + srcFile.getName() + "*/\r\n";
            FileUtils.writeStringToFile(ueditorAllCss , fileSplit , Charsets.UTF_8 , true);
            FileUtils.writeStringToFile(ueditorAllCss , fileContent , Charsets.UTF_8 , true);
        }
        System.out.println("ueditor.all.css文件生成完毕，路径为：" + ueditorAllCss.getAbsolutePath());
    }

}

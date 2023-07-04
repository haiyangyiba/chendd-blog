package cn.chendd.toolkit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 百度站长平台收录功能
 *
 * @author chendd
 * @date 2023/3/12 1:50
 */
public class BaiduSiteTest {

    public static void main(String[] args) throws Exception {
        String urlToken = "http://data.zz.baidu.com/urls?site=https://www.chendd.cn&token=TODO";
        String urls = FileUtils.readFileToString(new File("G:\\www.chendd.cn\\site.txt"), "utf-8");
        new BaiduSiteTest().postUrl(urlToken, urls);
    }


    /**
     * 百度site提交，https://ziyuan.baidu.com/site/index
     */
    public String postUrl(String urlToken , String urls) throws Exception {

        if(StringUtils.isEmpty(urlToken)) {
            throw new RuntimeException("urlToken参数不能为空");
        }
        int a = urlToken.indexOf("site=");
        int b = urlToken.indexOf("token=");
        if(a == -1 || b == -1) {
            throw new RuntimeException("urlToken参数不合法");
        }
        final String result = this.postUrls(urlToken, urls);
        System.out.println("操作结果：" + result);
        new Thread(new Runnable() {

            @Override
            public void run() {
                //将数据存储更新至数据库

            }
        }).start();
        return result;
    }

    private String postUrls(String urlToken , String url) throws Exception{
        String urls[] = StringUtils.split(url , "\r\n");
        return this.postUrls(urlToken, urls);
    }

    private String postUrls(String urlToken , String urls[]) throws Exception {
        String result = null;
        OutputStreamWriter os = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlToken);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Host", "data.zz.baidu.com");
            urlConn.setRequestProperty("User-Agent", "curl/7.12.1");
            urlConn.setRequestProperty("Content-Length", "83");
            urlConn.setRequestProperty("Content-Type", "text/plain");
            StringBuilder paramBuilder = new StringBuilder();
            for (String u : urls) {
                if(StringUtils.isBlank(u)) {
                    continue;
                }
                paramBuilder.append(u).append('\n');
            }
            os = new OutputStreamWriter(urlConn.getOutputStream());
            os.append(paramBuilder.toString());
            os.flush();
            br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line = null;
            StringBuilder resultBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                resultBuilder.append(line);
            }
            result = resultBuilder.toString();
        } catch(Exception e){
            throw new Exception("请求发生错误：" + e.getMessage() , e);
        } finally {
            IOUtils.close(os);
            IOUtils.close(br);
        }
        return result;
    }


}

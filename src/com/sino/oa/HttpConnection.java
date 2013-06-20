package com.sino.oa;
 import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by IntelliJ IDEA.

 * To change this template use File | Settings | File Templates.
 */
public class HttpConnection {
     private String endpoint = "http://10.218.64.75/sso/ssoautho.nsf/(ssologinauth)?openagent";

    public String validate(String uid, String dynpswd) {
        String res = "";
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(endpoint + "&uid=" + uid + "&dynpswd=" + dynpswd + "&appcode=zcgl");

            //以post方式请求
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setRequestMethod("GET");
//            dynpswd = StrUtil.replaceStr(dynpswd,"(","");
//            dynpswd = StrUtil.replaceStr(dynpswd,")","");
//            System.out.println("dynpswd = " + dynpswd);

            String username = "&";
            httpurlconnection.getOutputStream().write(username.getBytes());
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();

            //获取响应代码
            int code = httpurlconnection.getResponseCode();
//            System.out.println("code = " + code);

            //获取页面内容
            java.io.InputStream in = httpurlconnection.getInputStream();
            java.io.BufferedReader breader = new BufferedReader(new InputStreamReader(in, "gb2312"));
            String str = breader.readLine();
            while (str != null) {
//                System.out.println(str);
                res += str;
                str = breader.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpurlconnection != null)
                httpurlconnection.disconnect();
        }
        return res;

    }
}

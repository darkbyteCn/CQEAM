package com.sino.flow.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wwb.
 * User: demo
 * Date: 2007-4-9
 * Time: 9:24:54
 * 此类是用来读客户端传过来的数据
 */
public class ClientReader {
    //从客户端读取数据，以JSON的格式返回
    public String readToJSON(HttpServletRequest req) throws IOException {
        StringBuffer json = new StringBuffer();
        String line = null;
        BufferedReader br = req.getReader();
        while ((line = br.readLine()) != null) {
            json.append(line);
        }
        return unescape(json.toString());
    }

    /**
     * 解码 说明：本方法保证 不论参数src是否经过escape()编码，均能得到正确的“解码”结果
     *
     * @param src
     */
    private static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

}

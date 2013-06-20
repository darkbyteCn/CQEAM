package com.sino.sms.service.tinysoap.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 最简单的基于java的soap方案，只需要指定uri地址，以及soap body的内容即可 尽量不要做生产用途
 *
 * TinySoap.send(uri, soapbody);
 *
 * @author 张晓辉
 *
 */
public class TinySoap {
	private static final String SOAP_HEAD = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><soapenv:Body>";
	private static final String SOAP_FOOT = "</soapenv:Body></soapenv:Envelope>";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static final int TIMEOUT = 1 * 60 * 1000;

	/**
	 * 发送soap内容到指定的uri
	 *
	 * @param uri
	 * @param soapbody
	 * @return
	 * @throws IOException
	 */
	protected static String send(String uri, String soapbody, String soapAction) {

		HttpURLConnection connection = null;
		StringBuffer sb = new StringBuffer();
		OutputStream os = null;
		InputStream is = null;

		try {
			URL u = new URL(uri);
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"text/xml; charset=UTF-8");
			connection.setRequestProperty("SOAPAction", soapAction);

//			connection.setConnectTimeout(TIMEOUT);
			System.setProperty("sun.net.client.defaultConnectTimeout", "1000");
			System.setProperty("sun.net.client.defaultReadTimeout", "1000");

//			int a = connection.getReadTimeout();
//			System.out.println(a);
			// 输入
			connection.setDoOutput(true);
			os = connection.getOutputStream();

			sb.append(XML_HEADER);
			sb.append(SOAP_HEAD);
			sb.append(soapbody);
			sb.append(SOAP_FOOT);
			os.write(sb.toString().getBytes("UTF-8"));
			os.flush();

			// 读取输出
			sb = new StringBuffer();

			is = connection.getInputStream();

			appendStream(sb, is);

		} catch (IOException e) {
			System.out.println("连接异常" + e);
			appendErrorStream(connection, sb);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStream(is);
			closeStream(os);

			if (connection != null) {
				connection.disconnect();
			}
		}

		return sb.toString();
	}

	private static void closeStream(InputStream s) {
		if (s != null) {
			try {
				s.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

	private static void closeStream(OutputStream s) {
		if (s != null) {
			try {
				s.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

	private static void appendErrorStream(HttpURLConnection connection,
			StringBuffer sb) {
		try {
			InputStream is = connection.getErrorStream();

			appendStream(sb, is);

		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
	}

	private static void appendStream(StringBuffer sb, InputStream is)
			throws IOException {
		if (is == null) {
			return;
		}

		BufferedReader ei = new BufferedReader(new InputStreamReader(is));
		String line;

		while (ei.ready()) {
			line = ei.readLine();
			sb.append(line);
		}
	}

}

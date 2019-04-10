package com.mail.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class HttpUtil {
	static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isproxy
     *               是否使用代理模式
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,boolean isproxy) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            if(isproxy){//使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            }else{
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // 打开和URL之间的连接

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");    // POST方法


            // 设置通用的请求属性

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    /*
     * 发送json数据
     */
    public static String getJsonData(JSONObject jsonParam,String urls) {
		StringBuffer sb=new StringBuffer();
		String result="";
		try {
			;
			// 创建url资源
			URL url = new URL(urls);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);
                        // 设置允许输入
                        conn.setDoInput(true);
                       // 设置不用缓存
                       conn.setUseCaches(false);
                       // 设置传递方式
                       conn.setRequestMethod("POST");
                       // 设置维持长连接
                        conn.setRequestProperty("Connection", "Keep-Alive");
                       // 设置文件字符集:
                       conn.setRequestProperty("Charset", "UTF-8");
                       // 转换为字节数组
                       byte[] data = (jsonParam.toString()).getBytes();
                      // 设置文件长度
                       conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                      // 设置文件类型:
                      conn.setRequestProperty("contentType", "application/json");
                        // 开始连接请求
                       conn.connect();		
                    OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
			// 写入请求的字符串
			out.write((jsonParam.toString()).getBytes());
			out.flush();
			out.close();
 
			System.out.println(conn.getResponseCode());
			
			// 请求返回的状态
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
				System.out.println("连接成功");
				// 请求返回的数据
				InputStream in1 = conn.getInputStream();
				// 将获取流转为base64格式
				byte[] data1 = null;
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				byte[] buff = new byte[100];
				int rc = 0;
				while ((rc = in1.read(buff, 0, 100)) > 0) {
					swapStream.write(buff, 0, rc);
				}
				data1 = swapStream.toByteArray();
				
				result = new String(Base64.getEncoder().encode(data1));
			}
 
		} catch (Exception e) {
 
		}
		
		return result;
 
	}
    
    public static String getLocalIp() throws UnknownHostException {
    	return InetAddress.getLocalHost().getHostAddress();
    }
    
    public static String getLocalName() throws UnknownHostException {
    	return InetAddress.getLocalHost().getHostName();
    }
    
    public static void main(String[] args) throws UnknownHostException {
    	System.out.println(getLocalIp());
    	System.out.println(getLocalName());
    }
}

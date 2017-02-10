package com.bierbobo.rainbow.util;


import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
 * 但不够简便；
 *
 * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
 * 4.以输入流的形式获取返回内容 5.关闭输入流
 *
 * @author H__D
 *
 */
public class HttpConnectionUtil {

    static int fileLength =0;
    /**
     *
     * @param urlPath
     *            下载路径
     *            下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String uuid) {

        String downloadDir="d:/download";
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            //httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            fileLength+= httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();

            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            System.out.println("file fileFullName---->" + fileFullName);
            System.out.println("file length---->" + httpURLConnection.getContentLength());

            //System.out.println(fileFullName.indexOf("?"));
            //fileFullName = fileFullName.substring(0, fileFullName.indexOf("?"));

            fileFullName=uuid+".xlsx";
            String path = downloadDir + File.separatorChar + fileFullName;

//            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                //System.out.println("下载了-------> " + len * 100 / fileLength +"%\n");
            }
            bin.close();
            out.close();




        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }

    public static void main(String[] args) {

        try {
            List<String> files = IOUtils.readLines(HttpConnectionUtil.class.getResourceAsStream("/s.txt"), "UTF-8");
            System.out.println(files);

            for (String file : files) {

                String[] split = file.split("\\|");
//                System.out.println(split[0]+"|||"+split[1]);
                downloadFile(split[1],split[0]);
            }

            System.out.println(fileLength);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 下载文件测试
        //downloadFile("http://storage.jd.com/biipexport/%E6%A8%AA%E5%90%91%E5%AF%BC%E5%87%BA_bjwangleicx_20171249138376a2595-8529-415e-93ea-cca6fe644fd1.xlsx?Expires=1800579742&AccessKey=dd2679f03fa6bc45a21806d4d76dc436af4f939b&Signature=TYcCYs1sL1vIkylt557389W5Vhc%3D", "d:/download");

    }

}

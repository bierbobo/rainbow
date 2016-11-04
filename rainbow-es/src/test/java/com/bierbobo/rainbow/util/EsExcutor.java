package com.bierbobo.rainbow.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lifubo on 2016/9/4.
 */
public class EsExcutor {

    /**
     * 从一个文件读入到String
     * @param FilePath
     * @return
     */
    public static String ReadFileToString(String FilePath)
    {
        FileInputStream fis = null;
        BufferedReader br = null;
        try
        {
            fis = new FileInputStream(FilePath);
            br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //构建成String
        StringBuffer sb = new StringBuffer();
        String temp = null;
        try
        {
            while((temp = br.readLine()) != null)
            {
                if(temp.trim().length()>0){
                    sb.append(temp+'\n');
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getExecCode(String FilePath) {


        StringBuilder allCode = new StringBuilder();
        StringBuilder notUsedCode = new StringBuilder();


        try {
            String src=ReadFileToString(FilePath);
            allCode.append(src);
            /**
             * 1、做/* 注释的正则匹配
             *
             *     通过渐进法做注释的正则匹配，因为/*注释总是成对出现
             * 当匹配到一个/*时总会在接下来的内容中会首先匹配到"*\\/",
             * 因此在获取对应的"*\\/"注释时只需要从当前匹配的/*开始即可，
             * 下一次匹配时只需要从上一次匹配的结尾开始即可
             * （这样对于大文本可以节省匹配效率）——
             * 这就是渐进匹配法
             *


             * */
            Pattern leftpattern = Pattern.compile("/\\*");
            Matcher leftmatcher = leftpattern.matcher(src);
            Pattern rightpattern = Pattern.compile("\\*/");
            Matcher rightmatcher = rightpattern.matcher(src);

            /**
             * begin 变量用来做渐进匹配的游标 {@value}
             * 初始值为文件开头
             * **/
            int begin = 0;
            while(leftmatcher.find(begin))
            {
                rightmatcher.find(leftmatcher.start());
                int end = rightmatcher.end();
                notUsedCode.append(src.substring(leftmatcher.start(), end)).append("\n");
                allCode.delete(leftmatcher.start(),end );

                src=allCode.toString();
                leftmatcher = leftpattern.matcher(src);
                rightmatcher = rightpattern.matcher(src);

                /** 为输出时格式的美观 **/
//                    notUsedCode.append('\n');
                begin = 0;
            }
            /**
             * 2、对//注释进行匹配（渐进匹配法）
             * 匹配方法是 // 总是与 \n 成对出现
             * */
            begin = 0;
            Pattern leftpattern1 = Pattern.compile("//");
            Matcher leftmatcher1 = leftpattern1.matcher(src);
            Pattern rightpattern1 = Pattern.compile("\n");
            Matcher rightmatcher1 = rightpattern1.matcher(src);

            while(leftmatcher1.find(begin))
            {
                rightmatcher1.find(leftmatcher1.start());
                int end = rightmatcher1.end();
                notUsedCode.append(src.substring(leftmatcher1.start(),end ));
                allCode.delete(leftmatcher1.start(),end);
                src=allCode.toString();
//                System.out.println(src);
                leftmatcher1 = leftpattern1.matcher(src);
                rightmatcher1 = rightpattern1.matcher(src);
                begin = 0;
            }
            System.out.print("注释代码：");
            System.out.print(notUsedCode.toString());

            String replace = allCode.toString().replace("\n", " ");
            System.out.print("执行代码：");
            System.out.println(replace);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCode.toString().replace("\n"," ");
    }



    public static void main(String[] args) {

        String execCode = getExecCode("c:/ss.txt");

        execCode = execCode.replace("\"","\\\"");
        execCode = execCode.replace("\'", "\"");
        System.out.println("转换后的代码："+execCode);
        exec(execCode);


    }

    private static void exec(String execCode) {
        BufferedReader br=null;
        try {
//            Process proc=Runtime.getRuntime().exec("tasklist");
//            Process proc=Runtime.getRuntime().exec("curl -XPUT localhost:9200/customer?pretty");
            Process proc=Runtime.getRuntime().exec(execCode);
            br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
            @SuppressWarnings("unused")
            String line=null;
            System.out.println("打印控制台输出信息:");
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

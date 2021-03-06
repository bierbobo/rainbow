package com.bierbobo.rainbow.util.json.sample;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建样本
 * @author accountwcx@qq.com
 *
 */
public class SampleBuilder {

    public static void main(String[] args) {
        int sampleSize = 100000;
        String jsonDataPath = "d:\\samples_json.dat";
        String objectDataPath = "d:\\samples_object.dat";

        buildJsonSamples(sampleSize, 10, 10, jsonDataPath);
        buildObjectSamples(sampleSize, 10, 10, objectDataPath);
    }

    public static List<String> loadJSONSamples(String filePath) {
        List<String> list = new LinkedList<String>();

        File file = new File(filePath);
        if (!file.exists()) {
            return list;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null){
                list.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<SampleEntity> loadSamples(String filePath) {
        List<SampleEntity> list = new LinkedList<SampleEntity>();

        File file = new File(filePath);
        if (!file.exists()) {
            return list;
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            list = (List<SampleEntity>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        }

        return list;
    }

    /**
     * 创建样本
     *
     * @param sampleSize 样本数量
     * @param listSize 样本List长度
     * @param mapKeyNum 样本Map的Key数量
     * @return 样本List
     */
    public static List<SampleEntity> buildSamples(int sampleSize, int listSize, int mapKeyNum) {
        List<SampleEntity> list = new LinkedList<SampleEntity>();
        for (int i = 0; i < sampleSize; i++) {
            list.add(new SampleEntity(listSize, mapKeyNum));
        }

        return list;
    }

    /**
     * 用默认参数创建样本，其中listSize默认为10，mapKeyNum默认为10。
     *
     * @param sampleSize
     * @return 样本List
     */
    public static List<SampleEntity> buildSamples(int sampleSize) {
        List<SampleEntity> list = new LinkedList<SampleEntity>();
        for (int i = 0; i < sampleSize; i++) {
            list.add(new SampleEntity());
        }

        return list;
    }

    /**
     * 创建样本，并把样本JSON序列化，保存到文件中。
     *
     * @param sampleSize 样本数量
     * @param listSize 样本List长度
     * @param mapKeyNum 样本Map中Key的数量
     * @param filePath 样本输出的文件路径
     */
    public static void buildJsonSamples(int sampleSize, int listSize, int mapKeyNum, String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        List<SampleEntity> list = buildSamples(sampleSize, listSize, mapKeyNum);

        StringBuilder sb = new StringBuilder();
        for (SampleEntity item : list) {
            sb.append(JSON.toJSONString(item));
            sb.append("\n");
        }

        BufferedWriter bw = null;
        try {
            file.createNewFile();

            bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void buildJsonSamples(int sampleSize, String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        List<SampleEntity> list = buildSamples(sampleSize);

        StringBuilder sb = new StringBuilder();
        for (SampleEntity item : list) {
            sb.append(JSON.toJSONString(item));
            sb.append("\n");
        }

        BufferedWriter bw = null;
        try {
            file.createNewFile();

            bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void buildObjectSamples(int sampleSize, String filePath) {
        List<SampleEntity> list = buildSamples(sampleSize);

        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        ObjectOutputStream oos = null;
        try {
            file.createNewFile();

            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(list);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 生成样本对象，并保存到指定文件
     *
     * @param sampleSize 样本大小
     * @param listSize 样本中List字段长度
     * @param mapKeyNum 样本中Map对象Key数量
     * @param filePath 样本输出的路径
     */
    public static void buildObjectSamples(int sampleSize, int listSize, int mapKeyNum, String filePath) {
        List<SampleEntity> list = buildSamples(sampleSize, listSize, mapKeyNum);

        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        ObjectOutputStream oos = null;
        try {
            file.createNewFile();

            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(list);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
package com.bierbobo.rainbow.test;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by lifubo on 2016/10/17.
 */
public class SortTest {

    public static final String DISTRIBUTION_SORT = ",633,631,630,628,625,619,617,616,615,545,682,608,605,609,7,610,316,8,9,5,4,10,3,6,-1,"; //配送中心排序，按照倒序进行，列表之外的排在后面

    public static void main(String[] args) {

        List<DistributionInfo> distributionList = new ArrayList<DistributionInfo>();

//        String s="[{\"distributionId\":6,\"distributionName\":\"北京\"},{\"distributionId\":3,\"distributionName\":\"上海\"},{\"distributionId\":10,\"distributionName\":\"广州\"},{\"distributionId\":4,\"distributionName\":\"成都\"},{\"distributionId\":5,\"distributionName\":\"武汉\"},{\"distributionId\":9,\"distributionName\":\"沈阳\"},{\"distributionId\":8,\"distributionName\":\"济南\"},{\"distributionId\":316,\"distributionName\":\"西安\"},{\"distributionId\":610,\"distributionName\":\"青岛\"},{\"distributionId\":7,\"distributionName\":\"南京\"},{\"distributionId\":609,\"distributionName\":\"厦门\"},{\"distributionId\":605,\"distributionName\":\"重庆\"},{\"distributionId\":608,\"distributionName\":\"郑州\"},{\"distributionId\":682,\"distributionName\":\"固安\"},{\"distributionId\":545,\"distributionName\":\"杭州\"},{\"distributionId\":615,\"distributionName\":\"太原\"},{\"distributionId\":616,\"distributionName\":\"南宁\"},{\"distributionId\":617,\"distributionName\":\"哈尔滨\"},{\"distributionId\":619,\"distributionName\":\"长沙\"},{\"distributionId\":625,\"distributionName\":\"EPT配送中心\"},{\"distributionId\":628,\"distributionName\":\"兰州\"},{\"distributionId\":630,\"distributionName\":\"乌鲁木齐\"},{\"distributionId\":631,\"distributionName\":\"合肥\"},{\"distributionId\":633,\"distributionName\":\"昆明\"},{\"distributionId\":322,\"distributionName\":\"福州\"},{\"distributionId\":601,\"distributionName\":\"天津\"},{\"distributionId\":603,\"distributionName\":\"深圳\"},{\"distributionId\":606,\"distributionName\":\"苏州\"},{\"distributionId\":607,\"distributionName\":\"宁波\"},{\"distributionId\":614,\"distributionName\":\"石家庄\"},{\"distributionId\":618,\"distributionName\":\"大连\"},{\"distributionId\":629,\"distributionName\":\"包头\"},{\"distributionId\":632,\"distributionName\":\"宿迁\"},{\"distributionId\":634,\"distributionName\":\"贵阳\"},{\"distributionId\":635,\"distributionName\":\"南昌\"},{\"distributionId\":636,\"distributionName\":\"长春\"},{\"distributionId\":644,\"distributionName\":\"佛山\"},{\"distributionId\":648,\"distributionName\":\"烟台\"},{\"distributionId\":649,\"distributionName\":\"襄阳\"},{\"distributionId\":652,\"distributionName\":\"金华\"},{\"distributionId\":653,\"distributionName\":\"汕头\"},{\"distributionId\":654,\"distributionName\":\"海口\"},{\"distributionId\":655,\"distributionName\":\"南充\"},{\"distributionId\":658,\"distributionName\":\"银川\"},{\"distributionId\":669,\"distributionName\":\"北京分销配送中心\"},{\"distributionId\":670,\"distributionName\":\"上海分销配送中心\"},{\"distributionId\":671,\"distributionName\":\"广州分销配送中心\"},{\"distributionId\":672,\"distributionName\":\"成都分销配送中心\"},{\"distributionId\":673,\"distributionName\":\"武汉分销配送中心\"},{\"distributionId\":674,\"distributionName\":\"沈阳分销配送中心\"},{\"distributionId\":676,\"distributionName\":\"西安分销配送中心\"},{\"distributionId\":678,\"distributionName\":\"驻马店\"},{\"distributionId\":679,\"distributionName\":\"绵阳\"},{\"distributionId\":680,\"distributionName\":\"自贡\"},{\"distributionId\":681,\"distributionName\":\"潍坊\"},{\"distributionId\":683,\"distributionName\":\"唐山\"},{\"distributionId\":684,\"distributionName\":\"南通\"},{\"distributionId\":685,\"distributionName\":\"温州（禁用）\"},{\"distributionId\":686,\"distributionName\":\"北京移动配送中心\"},{\"distributionId\":689,\"distributionName\":\"上海移动配送中心\"},{\"distributionId\":690,\"distributionName\":\"广州移动配送中心\"},{\"distributionId\":691,\"distributionName\":\"成都移动配送中心\"},{\"distributionId\":692,\"distributionName\":\"武汉移动配送中心\"},{\"distributionId\":693,\"distributionName\":\"沈阳移动配送中心\"},{\"distributionId\":694,\"distributionName\":\"西安移动配送中心\"},{\"distributionId\":696,\"distributionName\":\"呼和浩特\"},{\"distributionId\":699,\"distributionName\":\"南沙保税配送中心\"},{\"distributionId\":700,\"distributionName\":\"广州产地配送中心\"},{\"distributionId\":701,\"distributionName\":\"南海\"},{\"distributionId\":702,\"distributionName\":\"香港IMP配送中心\"},{\"distributionId\":708,\"distributionName\":\"北京大件移动配送中心\"},{\"distributionId\":709,\"distributionName\":\"上海洋山\"},{\"distributionId\":711,\"distributionName\":\"温州\"},{\"distributionId\":713,\"distributionName\":\"合肥大件移动配送中心\"},{\"distributionId\":714,\"distributionName\":\"厦门大件移动配送中心\"},{\"distributionId\":715,\"distributionName\":\"南充大件移动配送中心\"},{\"distributionId\":716,\"distributionName\":\"长沙大件移动配送中心\"},{\"distributionId\":717,\"distributionName\":\"沈阳大件移动（禁用）\"},{\"distributionId\":718,\"distributionName\":\"西安大件移动配送中心\"},{\"distributionId\":719,\"distributionName\":\"杭州保税\"},{\"distributionId\":720,\"distributionName\":\"宁波保税\"},{\"distributionId\":721,\"distributionName\":\"沈阳大件移动配送中心\"},{\"distributionId\":725,\"distributionName\":\"潍坊大件移动配送中心\"},{\"distributionId\":726,\"distributionName\":\"汕头大件移动配送中心\"},{\"distributionId\":727,\"distributionName\":\"驻马店大件移动配送中心\"},{\"distributionId\":728,\"distributionName\":\"大连大件移动配送中心\"},{\"distributionId\":729,\"distributionName\":\"青岛药品\"},{\"distributionId\":730,\"distributionName\":\"郑州保税\"},{\"distributionId\":731,\"distributionName\":\"东莞\"},{\"distributionId\":732,\"distributionName\":\"茂名\"},{\"distributionId\":733,\"distributionName\":\"浦东\"},{\"distributionId\":734,\"distributionName\":\"柳州\"},{\"distributionId\":736,\"distributionName\":\"济南大件移动配送中心\"},{\"distributionId\":737,\"distributionName\":\"青岛大件移动配送中心\"},{\"distributionId\":738,\"distributionName\":\"石家庄大件移动配送中心\"},{\"distributionId\":739,\"distributionName\":\"太原大件移动配送中心\"},{\"distributionId\":740,\"distributionName\":\"呼和浩特大件移动配送中心\"},{\"distributionId\":741,\"distributionName\":\"南京大件移动配送中心\"},{\"distributionId\":742,\"distributionName\":\"宿迁大件移动配送中心\"},{\"distributionId\":743,\"distributionName\":\"杭州大件移动配送中心\"},{\"distributionId\":744,\"distributionName\":\"宁波大件移动配送中心\"},{\"distributionId\":745,\"distributionName\":\"金华大件移动配送中心\"},{\"distributionId\":746,\"distributionName\":\"温州大件移动配送中心\"},{\"distributionId\":747,\"distributionName\":\"福州大件移动配送中心\"},{\"distributionId\":748,\"distributionName\":\"南宁大件移动配送中心\"},{\"distributionId\":749,\"distributionName\":\"佛山大件移动配送中心\"},{\"distributionId\":750,\"distributionName\":\"南海大件移动配送中心\"},{\"distributionId\":751,\"distributionName\":\"海口大件移动配送中心\"},{\"distributionId\":752,\"distributionName\":\"成都大件移动配送中心\"},{\"distributionId\":753,\"distributionName\":\"武汉大件移动配送中心\"},{\"distributionId\":754,\"distributionName\":\"郑州大件移动配送中心\"},{\"distributionId\":755,\"distributionName\":\"南昌大件移动配送中心\"},{\"distributionId\":756,\"distributionName\":\"襄阳大件移动配送中心\"},{\"distributionId\":757,\"distributionName\":\"哈尔滨大件移动配送中心\"},{\"distributionId\":758,\"distributionName\":\"长春大件移动配送中心\"},{\"distributionId\":759,\"distributionName\":\"乌鲁木齐大件移动配送中心\"},{\"distributionId\":760,\"distributionName\":\"成都图书EDI\"},{\"distributionId\":761,\"distributionName\":\"重庆保税\"},{\"distributionId\":762,\"distributionName\":\"济宁\"},{\"distributionId\":763,\"distributionName\":\"天津保税\"},{\"distributionId\":765,\"distributionName\":\"台州\"},{\"distributionId\":767,\"distributionName\":\"洛阳\"},{\"distributionId\":768,\"distributionName\":\"衡阳\"},{\"distributionId\":771,\"distributionName\":\"香港屯门\"},{\"distributionId\":-1,\"distributionName\":\"全国\"}]";
//        List<DistributionInfo> distributionInfos = JSONArray.parseArray(s, DistributionInfo.class);
//        System.out.println(distributionInfos);

        Map<Integer,Integer> map = new LinkedHashMap<>();
        String[] str = StringUtils.split(DISTRIBUTION_SORT, ",");
        if(str!=null){
            for(int i=0;i<str.length;i++){
                String distribution = str[i];
                if(StringUtils.isNotBlank(distribution)){
                    map.put(Integer.valueOf(distribution),str.length-i);
                }
            }
        }
        System.out.println(map);


//        List<String> list=new ArrayList<>();
//        String[] split = DISTRIBUTION_SORT.split(",");
//        for (int i = split.length - 1; i >= 0; i--) {
//            list.add(split[i]);
//        }
/*

        Collections.sort(distributionInfos, new DisComparator());
        for (int i = 0; i < distributionInfos.size(); i++) {
            try {
                System.out.println(distributionInfos.get(i).getDistributionId()+"==================="+list.get(i));
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
*/

    }
}


class DisComparator implements Comparator,Serializable {
    private static Map<Integer,Integer> map = new HashMap<Integer, Integer>();
    static{
        String[] str = StringUtils.split(SortTest.DISTRIBUTION_SORT, ",");
        if(str!=null){
            for(int i=0;i<str.length;i++){
                String distribution = str[i];
                if(StringUtils.isNotBlank(distribution)){
                    map.put(Integer.valueOf(distribution),str.length-i);
                }
            }
        }
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            Method method1 = o1.getClass().getMethod("get"+ StringUtils.capitalize("distributionId"));
            Method method2 = o2.getClass().getMethod("get"+ StringUtils.capitalize("distributionId"));
            Object dis1 = method1.invoke(o1, null);
            Object dis2 = method2.invoke(o2, null);
            if(map.get(dis1)==null){
                return 1;
            }else if(map.get(dis2)==null){
                return -1;
            }
            return map.get(dis1).compareTo(map.get(dis2));
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

}


/**
 * 配送中心信息
 * @author tangling
 *
 */
class DistributionInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8508010289266239831L;
    /**
     * 配送中心id
     */
    private Integer distributionId;
    /**
     * 配送中心名称
     */
    private String distributionName;
    /**
     * 一个配送下所有的提醒的widList【所有分页的】
     */
    private Set<Long> disWidList;

    public Integer getDistributionId() {
        return distributionId;
    }
    public void setDistributionId(Integer distributionId) {
        this.distributionId = distributionId;
    }
    public String getDistributionName() {
        return distributionName;
    }
    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }
    public Set<Long> getDisWidList() {
        return disWidList;
    }
    public void setDisWidList(Set<Long> disWidList) {
        this.disWidList = disWidList;
    }

    @Override
    public String toString() {
        return "DistributionInfo{" +
                "distributionId=" + distributionId +
                ", distributionName='" + distributionName + '\'' +
                '}';
    }
}
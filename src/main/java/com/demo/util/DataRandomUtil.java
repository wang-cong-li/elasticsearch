package com.demo.util;

import com.demo.entity.WechatUser;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

public class DataRandomUtil {

    private static Random random = new Random();
    private static final char [] sexs = "男女".toCharArray();
    private static final char [] firstName = "赵钱孙李周吴郑王冯陈卫蒋沈韩杨朱秦许何吕施张孔曹严金魏陶姜谢邹窦章苏潘葛范彭谭夏胡".toCharArray();

    public static String  randomSex(){
        return String.valueOf(sexs[Math.abs(random.nextInt() % 2)]);
    }

    public static String randomWxId(){
        return UUID.randomUUID().toString();
    }

    public  static Double[] randomLonLat(Double myLon,Double myLat){
        double min = 0.000001;//坐标范围，最小1米
        double max = 0.00002;//坐标范围，最大1000米

        //随机生成一组长沙附近的坐标
        double s = random.nextDouble() % (max - min + 1) + max;
        //格式化保留6位小数
        DecimalFormat df = new DecimalFormat("######0.000000");
        String slon = df.format(s + myLon);
        String slat = df.format(s + myLat);
        Double dlon = Double.valueOf(slon);
        Double dlat = Double.valueOf(slat);
        return new Double[]{dlat,dlon};
    }

    public static String randomNickName(String sex){
        String xing = String.valueOf(firstName[Math.abs(random.nextInt() % (firstName.length))]);
        String name = "";
        if ("男".equals(sex)){
            name = "先生";
        }  else{
            name = "女士";
        }
        return  xing + name;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(randomNickName(randomSex()));
        }
    }

    public static WechatUser randomWechatUser(Double myLon,Double myLat){
        String wxId = randomWxId();
        String sex = randomSex();
        String nickName = randomNickName(sex);
        Double[] lonlat = randomLonLat(myLon,myLat);
        WechatUser user = new WechatUser();
        user.setWxId(wxId);
        user.setSex(sex);
        user.setNickName(nickName);
        GeoPoint geoPoint = new GeoPoint(lonlat[1],lonlat[0]);
        user.setLocation(geoPoint);
        return user;
    }


}

package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        // 加载sqlite驱动
        Class.forName("org.sqlite.JDBC");
        Connection c = null;
        // 连接db文件
        c = DriverManager.getConnection("jdbc:sqlite:/Users/jianchao/Downloads/Q1.DB");
        Statement statement = c.createStatement();
        // 执行器执行查询语句
        ResultSet resultSet = statement.executeQuery("SELECT * from json_data_52e3cb5d23853bbea3dbe693b65345d9bb7966c1");
        // 第一小题答案
        ArrayList<Integer> answer1 = new ArrayList<>();
        // 第二小题答案
        ArrayList<Integer> answer2 = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String s = resultSet.getString("data");
            Map map = null;
            // 查询结果返回为对象，即数据返回非数组时
            if(!(JSON.parse(s) instanceof JSONArray)){
                map = (Map) JSON.parse(s);
                // 利用set容器存放所有的key
                Set<String> setKey = map.keySet();
                // 遍历所有的key
                for(String s1 : setKey){
                    Object value = map.get(s1);
                    // value的数据类型是JsonObject且为空时，添加入链表
                    if(value instanceof JSONObject && ((JSONObject) value).isEmpty()){
                        answer1.add(id);
                    }
                    // value数据类型为JsonArray时
                    if(value instanceof JSONArray){
                        JSONArray jsonArray = (JSONArray) value;
                        // 当数组下标不为-1，即有null时，添加到链表
                        if(-1 !=jsonArray.indexOf(null)){
                            answer2.add(id);
                        }
                    }
                }
            }else {
                // 查询结果返回为数组
                JSONArray array = (JSONArray) JSON.parse(s);
                // 当数组下标不为-1，即有null时，添加到链表
                if(-1 != array.indexOf(null)){
                    answer2.add(id);
                }
            }
        }
        System.out.println("第一题答案:"+answer1.toString());
        System.out.println("第二题答案:"+answer2.toString());
        resultSet.close();
        statement.close();
        c.close();


//        File file = new File("/Users/jianchao/Downloads/Q1.DB");
//        final String CHARSET = "UTF-8";
//        List<String> list = new ArrayList<>();
//        FileInputStream fileInputStream = new FileInputStream(file);
//        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream,CHARSET));
//        String line;
//        while((line = br.readLine()) != null){
//            list.add(line);
//            System.out.println(list.get(0));
//        }
//        fileInputStream.close();
//        br.close();
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] buffer = new byte[1024];
//        int bytes;
//        while((bytes = fileInputStream.read(buffer))!=-1){
//            for(int i=0;i<bytes;i++){
//                System.out.print(buffer[i]);
//            }
//            System.out.println(fileInputStream.read(buffer));
//        };
//        fileInputStream.close();;
    }
}
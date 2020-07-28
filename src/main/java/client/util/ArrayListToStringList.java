package client.util;

import configuration_and_constant.Constant;
import entity.FileModel;

import java.io.File;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 对上传下载表格信息的处理的工具类
 * 主要逻辑是将ArrayList转换为String类型的二维数组
 * @date 2020-07-07 16:25
 */
public class ArrayListToStringList {


    private static String[][] data;

    public static String[][] getData(ArrayList<String[]> arrayList){

        int clo = arrayList.size();
        int row = arrayList.get(0).length;
        data = new String[clo][row];

        for(int i = clo -1; i >=0;i--){
            for(int j = 0; j < row; j++){
                data[clo -1 -i][j] = arrayList.get(i)[j];
            }
        }
        return data;
    }


    public static String[][] flushData(ArrayList<String[]> arrayList,String name,String size,Long length){
        int clo = arrayList.size();
        for(int i = clo -1; i >= 0;i--){
            if(name.equals(arrayList.get(i)[0]) && size.equals(arrayList.get(i)[1])){
                if(!String.valueOf(length).equals(size)){
                    arrayList.get(i)[2] = String.valueOf(length);
                }else{
                    arrayList.get(i)[2] = "传输已完成！文件地址：" + Constant.DEFAULT_PATH + name;
                }
            }
        }
        return getData(arrayList);
    }

    public static String[][] receiveFlushData(ArrayList<String[]> arrayList,String name,String size,Long length){
        int clo = arrayList.size();
        for(int i = clo -1; i >= 0;i--){
            if(name.equals(arrayList.get(i)[0]) && size.equals(arrayList.get(i)[1])){
                if(!String.valueOf(length).equals(size)){
                    arrayList.get(i)[2] = String.valueOf(length);
                }else{
                    arrayList.get(i)[2] = "传输已完成！文件地址：" + Constant.UPLOAD_PATH+ name;
                }
            }
        }
        return getData(arrayList);
    }


    public static ArrayList flushData(ArrayList<String[]> arrayList,String name,String size){
        int clo = arrayList.size();
        for(int i = clo -1 - 1; i >= 0;i--){
            if(name.equals(arrayList.get(i)[0]) && size.equals(arrayList.get(i)[1])){
                arrayList.remove(i);
            }
        }
        return arrayList;
    }
}

package client.util;

import lombok.Data;

import java.io.File;

/**
 * @author LvHao
 * @Description : 目录信息
 * @date 2020-07-06 10:28
 */
public class DefaultMsg {

    /**
     * 目录信息
     */

    private static String filePath = null;

    public static void setFilePath(String s){
        filePath = s+ File.separator;
    }

    public static String getFilePath(){
        return filePath;
    }
}

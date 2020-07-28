package client.util;

/**
 * @author LvHao
 * @Description : 判断系统类型 windows linux
 * @date 2020-07-04 19:37
 */
public class OSinfo {
    public static OS getOS(){
        if(System.getProperty("os.name").equalsIgnoreCase("linux")){
            return OS.LINUX;
        }else{
            return OS.WINdOWS;
        }
    }

    public enum OS {
        WINdOWS,
        LINUX
    }
}

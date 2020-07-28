package client.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author LvHao
 * @Description : 返回本机地址的工具类
 * 主动模式给服务端提供客户端的数据端口的IP信息
 * @date 2020-07-03 11:43
 */
public class IPUtil {
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

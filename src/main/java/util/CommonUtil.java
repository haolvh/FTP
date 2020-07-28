package util;

import java.util.Random;

/**
 * 通用的工具类
 *
 * @author yinchao
 * @date 2020/7/3 09:59
 */
public class CommonUtil {
    /**
     * int 转化为 byte 数组
     */
    public static byte[] IntegerToBytes(Integer data) {
        byte[] result = new byte[4];
        result[0] = (byte) ((data & 0xFF000000) >> 24);
        result[1] = (byte) ((data & 0x00FF0000) >> 16);
        result[2] = (byte) ((data & 0x0000FF00) >> 8);
        result[3] = (byte) ((data & 0x000000FF) >> 0);
        return result;
    }

    /**
     * byte 数组转化成 int
     *
     * @param offset byte 数组开始下标
     */
    public static int byte4ToInt(byte[] bytes, int offset) {
        int b0 = bytes[offset] & 0xFF;
        int b1 = bytes[offset + 1] & 0xFF;
        int b2 = bytes[offset + 2] & 0xFF;
        int b3 = bytes[offset + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    public static byte[] ensureLength(byte[] data, int length) {
        if (data.length == length) {
            return data;
        }
        byte[] bytes = new byte[length];
        int offset = length - 1;
        for (int i = data.length - 1; i >= 0; i--) {
            bytes[offset--] = data[i];
        }
        return bytes;
    }

    public static Integer generateRandomPort() {
        // fixme: 重复端口处理
        return new Random().nextInt(8976) + 1024;
    }

}

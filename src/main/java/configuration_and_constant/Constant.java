package configuration_and_constant;

import java.io.File;

/**
 * @author yinchao
 * @date 2020/7/2 17:27
 */
public class Constant {
    /**
     * 首部字段表示即将传输命令/数据的大小
     */
    public static final Integer HEAD_BYTE_SIZE = 8;

    /**
     * 命令端口号
     */
    public static final Integer COMMANDPORT = 8081;

    /**
     * 命令/数据最大大小: 2^32 G
     */
    public static final Integer MAXIMUM_COMMAND_BUFFER_SIZE = (int) Math.pow(2, 64);
    /**
     * 远程目录访问的目标文件夹
     */
    public static final String DEFAULT_FILE_PATH = System.getProperty("user.home");
    /**
     * 客户端的下载目录
     */
//    public static final String DEFAULT_PATH = "C:\\Download\\";
    public static final String DEFAULT_PATH = "D:\\下载\\";
    /**
     * 服务端的上传目录
     */
//    public static final String UPLOAD_PATH="C:\\UPLOAD\\";
    public static final String UPLOAD_PATH="D:\\上传\\";
    /**
     *  每次传输的字节量大小
     */
    public static final Integer DATASIZE=1024;
}

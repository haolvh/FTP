package entity;

import java.io.Serializable;

/**
 * 类型
 *
 * @author yinchao
 * @date 2020/7/2 10:41
 */
public enum OperateType implements Serializable {
    CONNECT,
    // 上传
    UPLOAD,
    // 下载
    DOWNLOAD,
    // 暂停
    PAUSE,
    // 查看文件
    FILE_PATH,
    //返回上级目录
    RETURN_FATHER_DIR,
    // 删除远程文件
    DELETE,
    // 错误信息
    ERROR
}

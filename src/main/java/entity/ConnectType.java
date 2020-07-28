package entity;

import java.io.Serializable;

/**
 * 连接类型
 * @author yinchao
 * @date 2020/7/4 22:18
 */
public enum ConnectType implements Serializable {
    // 被动
    PASSIVE,
    // 主动
    INITIATIVE
}

package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @类名 Filecol
 * @描述 用于客户端请求文件的通信
 * @作者 heguicai
 * @创建日期 2020/7/3 下午3:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileCol {
    int operationType;
    String filePath;
}
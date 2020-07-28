package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @类名 FileCol
 * @描述 用于传输文件结构
 * @作者 heguicai
 * @创建日期 2020/7/3 上午11:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileModel implements Serializable {
    //文件名
    String fileName;
    //文件路径
    String filePath;
    //文件大小
    String fileSize;
    //修改时间
    String changeTime;
    //文件类型
    FileEnum fileType;
}
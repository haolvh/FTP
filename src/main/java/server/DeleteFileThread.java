package server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * @author yinchao
 * @date 2020/7/8 01:26
 */
@Data
@AllArgsConstructor
@Slf4j
public class DeleteFileThread implements Callable {
    String fileName;

    @Override
    public Boolean call() {
        if(fileName==null){
            return Boolean.FALSE;
        }
        return delete(new File(fileName));
    }

    private Boolean delete(File file){
         if (!file.exists()) {
            log.info("文件不存在");
            return Boolean.FALSE;
        }
        if (file.isFile()) {
            if (file.delete()) {
                log.info("删除文件{}成功", file.getAbsolutePath());
                return Boolean.TRUE;
            } else {
                log.error("删除失败");
                return Boolean.FALSE;
            }
            // 如果是文件夹,递归删除
        } else {
            Arrays.stream(file.listFiles()).forEach(item -> {
                delete(item);
                item.delete();
            });
            return file.delete();
        }
    }
}

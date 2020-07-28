package server;

import configuration_and_constant.Constant;
import entity.FileModel;
import lombok.*;
import util.FileUtil;

import java.io.*;

/**
 * @类名 ExceptFileByByte
 * @描述 用字节数组的形式接受文件
 * @作者 heguicai
 * @创建日期 2020/7/4 下午5:02
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptFileByByte implements Runnable{
    DataInputStream dis;
    FileModel fileModel;


    /**
     * @describe 用字节数组的形式接受文件
     * @param dis
     * @param fileModel
     * @throws IOException
     */
    public  void breakPoint(DataInputStream dis, FileModel fileModel) throws IOException {
        RandomAccessFile rad=null;
        try {
            File file = new File(Constant.UPLOAD_PATH  + fileModel.getFileName() + ".temp");
            rad = new RandomAccessFile(Constant.UPLOAD_PATH +  fileModel.getFileName()  + ".temp", "rw");
            long fileLength=Long.valueOf(fileModel.getFileSize());
            long point = 0;
            if (file.exists() && file.isFile()) {
                point = file.length();
            }
            rad.seek(point);
            byte[] value = new byte[Constant.DATASIZE];
            while (true) {
                int length = dis.read(value);
                if (length == -1) {
                    break;
                }
                rad.write(value);
                point += length;
                if (point == fileLength) {
                    break;
                }
            }
            //dis.close();
            rad.close();
            //文件重命名
            if (point >= fileLength) {
                file.renameTo(new File(Constant.UPLOAD_PATH + fileModel.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
//            dis.close();
            rad.close();
        }

    }

    @SneakyThrows
    @Override
    public void run() {
        breakPoint(dis,fileModel);
    }
}

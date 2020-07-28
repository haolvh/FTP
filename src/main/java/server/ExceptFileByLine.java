package server;

import client.socket.SocketUtil;
import configuration_and_constant.Constant;
import entity.FileModel;
import lombok.*;
import util.FileUtil;

import java.io.*;
import java.net.Socket;

/**
 * @类名 ExceptFileByLine
 * @描述 用字符的形式接受文件
 * @作者 heguicai
 * @创建日期 2020/7/6 下午5:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptFileByLine implements Runnable{
InputStream inputStream;
FileModel fileModel;


    /**
     * 字符文件以行的形式接收
     * @param cin
     * @param fileModel
     * @throws IOException
     */
    public void breakoutPoint(InputStream cin,FileModel fileModel) throws IOException {
   BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(cin));
    int fileLength=Integer.valueOf(fileModel.getFileSize());
    File tempFile=new File(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp");
    FileWriter   fileWriter=new FileWriter(tempFile);
    int tempLength= 0;
    try {
        tempLength = FileUtil.getFileLine(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp");
        String value=null;
        while ((value=bufferedReader.readLine())!=null)
        {
            fileWriter.write(value+"\r\n");
            fileWriter.flush();
            tempLength++;
        }
        fileWriter.close();
        bufferedReader.close();
        if(tempLength>=fileLength)
        {
            boolean pan=tempFile.renameTo(new File(Constant.UPLOAD_PATH + fileModel.getFileName()));
            System.out.println("更名成功？："+pan);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        fileWriter.close();
        bufferedReader.close();
    }

}


    @SneakyThrows
    @Override
    public void run() {
        breakoutPoint(inputStream,fileModel);
    }
}
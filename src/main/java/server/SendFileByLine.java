package server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import util.FileUtil;

import java.io.*;

/**
 * @author yinchao
 * @date 2020/7/4 18:50
 */
@AllArgsConstructor
@Slf4j()
@Builder
public class SendFileByLine implements Runnable {
    private String filePath;
    private OutputStream sendOutputStream;
    private int fileLength;

    @SneakyThrows
    @Override
    public void run() {
          sendFile(sendOutputStream,fileLength,filePath);
    }


    /**
     * @describe 字符文件以行的形式传输
     * @param sendOutputStream
     * @param fileLength
     * @param filePath
     * @throws IOException
     */
    public void sendFile(OutputStream sendOutputStream,int fileLength,String filePath) throws IOException {
        int fileLine= 0;
        try {
            fileLine = FileUtil.getFileLine(filePath);
            File file=new File(filePath);
            FileReader fileReader=new FileReader(file);
            LineNumberReader lineNumberReader=new LineNumberReader(fileReader);
            lineNumberReader.skip(fileLength);
            String str;
//            PrintWriter printWriter=new PrintWriter(sendOutputStream);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(sendOutputStream);
            while(fileLength<=fileLine)
            {
                str=lineNumberReader.readLine();
                outputStreamWriter.write(str+"\r\n");
                outputStreamWriter.flush();
//                printWriter.println(str);
                fileLength++;
            }
            System.out.println(fileLength);
            System.out.println(fileLine);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            sendOutputStream.close();
        }

    }
}

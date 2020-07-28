package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 文本文件的上传线程  包括断点续传
 * @date 2020-07-08 2:44
 */
@Data
@RequiredArgsConstructor
public class TxtFileSendThread implements Runnable{

    private String[] clo = new String[3];
    private String[] tableInfo = {"文件名","文件大小","传输状态"};
    private DefaultTableModel model;
    private long point;

    @NonNull
    private OutputStream outputStream;
    @NonNull
    private FileModel fileModel;
    @NonNull
    private ClientFrame clientFrame;
    @NonNull
    private JTable jTable;
    @NonNull
    private ArrayList<String[]> data;
    @NonNull
    private int num;


    @Override
    public void run() {
        try{
            while (true){
                int fileLine = FileUtil.getFileLine(fileModel.getFilePath());
                int fileLength = Integer.parseInt(fileModel.getFileSize());
                jTable.setValueAt(fileLength,num,2);
                File file = new File(fileModel.getFilePath());
                FileReader fileReader = new FileReader(file);
                LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
                lineNumberReader.skip(fileLength);
                String str;
                PrintWriter printWriter = new PrintWriter(outputStream);
                while(fileLength <= fileLine){
                    str = lineNumberReader.readLine();
                    printWriter.println(str);
                    fileLength++;
                    jTable.setValueAt(fileLength,num,2);
                }
                fileReader.close();
                lineNumberReader.close();
                printWriter.close();
                if(clientFrame.getDataSocket() != null){
                    clientFrame.getDataSocket().close();
                    clientFrame.setDataSocket(null);
                }
                model = new DefaultTableModel(ArrayListToStringList.flushData(data,fileModel.getFileName(), String.valueOf(fileLine), (long) fileLine),tableInfo);
                jTable.setModel(model);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import configuration_and_constant.Constant;
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
 * @Description : 文本文件接受的线程  包括了断点续传原理
 * @date 2020-07-08 2:02
 */
@Data
@RequiredArgsConstructor
public class TxtFileReceiveThread implements Runnable{

    private File tempFile;
    private int fileLength;
    private BufferedReader bufferedReader;
    private String[] clo = new String[3];
    private String[] tableInfo = {"文件名","文件大小","传输状态"};
    private DefaultTableModel model;

    @NonNull
    private InputStream inputStream;
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
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                fileLength = Integer.valueOf(fileModel.getFileSize());
                tempFile=new File(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
                int tempLength= FileUtil.getFileLine(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
                jTable.setValueAt(tempLength,num,2);
                FileWriter fileWriter = new FileWriter(tempFile);
                String value = null;
                while ((value=bufferedReader.readLine()) != null){
                    fileWriter.write(value + "\r\n");
                    tempLength++;
                    jTable.setValueAt(tempLength,num,2);
                }
                fileWriter.close();
                bufferedReader.close();

                model = new DefaultTableModel(ArrayListToStringList.flushData(data,fileModel.getFileName(),fileModel.getFileSize(), (long) tempLength),tableInfo);

                if(tempLength>=fileLength)
                {
                    model = new DefaultTableModel(ArrayListToStringList.flushData(data,fileModel.getFileName(),fileModel.getFileSize(), (long) fileLength),tableInfo);
                    tempFile.renameTo(new File(Constant.DEFAULT_PATH + fileModel.getFileName()));
                }
                jTable.setModel(model);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

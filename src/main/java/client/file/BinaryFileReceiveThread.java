package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import configuration_and_constant.Constant;
import entity.FileModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 接受二进制文件的线程 包括断点续传
 * @date 2020-07-07 19:03
 */
@RequiredArgsConstructor
public class BinaryFileReceiveThread implements Runnable{

    private File tempFile;
    private RandomAccessFile randomAccessFile;
    private long fileLength;
    private DataInputStream dataInputStream;
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
                dataInputStream = new DataInputStream(inputStream);
                fileLength = Long.parseLong(fileModel.getFileSize());
                tempFile = new File(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
                randomAccessFile = new RandomAccessFile(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp","rw");
                long size = 0;
                if(tempFile.exists() && tempFile.isFile()){
                    size = tempFile.length();
                }
                jTable.setValueAt(size,num,2);
                //从之前的断点地方进行接收
                randomAccessFile.seek(size);
                byte[] value = new byte[Constant.DATASIZE];
                while(clientFrame.getDataSocket() != null){
                    int length = dataInputStream.read(value);
                    if(length == -1){
                        System.out.println("end");
                        break;
                    }
                    randomAccessFile.write(value,0,length);
                    jTable.setValueAt(size,num,2);
                    size += length;
                    if(size >= fileLength){
                        System.out.println("end");
                        break;
                    }
                }
                randomAccessFile.close();
                if(clientFrame .PsvdataSocket != null &&!clientFrame.PsvdataSocket.isConnected()){
                    dataInputStream.close();
                    inputStream.close();
                }
                if(clientFrame.getDataSocket() != null){
                    clientFrame.getDataSocket().close();
                    clientFrame.setDataSocket(null);
                }
                model = new DefaultTableModel(ArrayListToStringList.flushData(data,fileModel.getFileName(),fileModel.getFileSize(),size),tableInfo);
                //对文件重命名
                if(size >= fileLength){
                    model = new DefaultTableModel(ArrayListToStringList.flushData(data,fileModel.getFileName(),fileModel.getFileSize(),fileLength),tableInfo);
                    tempFile.renameTo(new File(Constant.DEFAULT_PATH + fileModel.getFileName()));
                }
                jTable.setModel(model);
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import configuration_and_constant.Constant;
import entity.FileModel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 二进制文件上传的线程  包括断点续传
 * @date 2020-07-07 23:56
 */
@Data
@RequiredArgsConstructor
public class BinaryFileSendThread implements Runnable{

    private DataOutputStream dataOutputStream;;
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


    @SneakyThrows
    @Override
    public void run() {
        RandomAccessFile randomAccessFile = null;
        try{
            while (true){
                dataOutputStream = new DataOutputStream(outputStream);
                point = Long.parseLong(fileModel.getFileSize());
                jTable.setValueAt(point,num,2);
                File file = new File(fileModel.getFilePath());
                randomAccessFile = new RandomAccessFile(file,"rw");
                byte[] value = null;
                long fileLength = file.length();
                try{
                    randomAccessFile.seek(point);
                    value = new byte[(int)(fileLength - point)];
                    if(randomAccessFile.read(value) != (fileLength - point)){
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //每次读取的数量大小
                int sendCont = Constant.DATASIZE;
                int low = 0;
                long size = 0;
                while(clientFrame.getDataSocket() != null) {
                    try {
                        if (low + sendCont >= fileLength - point) {
                            dataOutputStream.write(value, low, (int) (fileLength - point -low));
                            jTable.setValueAt(fileLength,num,2);
                            size = fileLength;
                            randomAccessFile.close();
                            if(clientFrame .PsvdataSocket != null){
                                if(!clientFrame.PsvdataSocket.isConnected()){
                                    dataOutputStream.close();
                                    outputStream.close();
                                }
                            }else{
                                dataOutputStream.close();
                                outputStream.close();
                            }
                            break;
                        } else {
                            dataOutputStream.write(value, low,sendCont);
                            jTable.setValueAt(value.length,num,2);
                            dataOutputStream.flush();
                            low+=sendCont;
                            size = low + point;
                            jTable.setValueAt(low+point,num,2);
                        }
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                        break;
                    }
                }
                randomAccessFile.close();
                if(clientFrame .PsvdataSocket != null){
                    if(!clientFrame.PsvdataSocket.isConnected()){
                        dataOutputStream.close();
                        outputStream.close();
                    }
                }else{
                    dataOutputStream.close();
                    outputStream.close();
                }
                if(clientFrame.getDataSocket() != null){
                    clientFrame.getDataSocket().close();
                    clientFrame.setDataSocket(null);
                }
                model = new DefaultTableModel(ArrayListToStringList.receiveFlushData(data,fileModel.getFileName(), String.valueOf(fileLength), size),tableInfo);
                jTable.setModel(model);
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e){
            //TODO nothing
        }finally {
            randomAccessFile.close();
        }
    }
}

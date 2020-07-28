package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 接受文本文件的处理 新开线程  避免对客户端其他操作的影响
 * @date 2020-07-08 1:53
 */
@RequiredArgsConstructor
public class TxtFileReceiveHandler {

    private static ArrayList<String[]> data = new ArrayList<String[]>();
    private static String[] clo = null;
    private static String[] tableInfo = {"文件名","文件大小","传输状态"};
    private static DefaultTableModel model;

    public static void receiveTxtFile(InputStream inputStream, FileModel fileModel, ClientFrame clientFrame,ArrayList<String[]> data){
        JTable jTable= clientFrame.getJPanel4().getJTable1();
        clo = new String[3];
        clo[0] = fileModel.getFileName();
        clo[1] = fileModel.getFileSize();
        clo[2] = "";
        data.add(clo);

        if(data.size() > 1){
            data = ArrayListToStringList.flushData(data,clo[0],clo[1]);
        }

        model=new DefaultTableModel(ArrayListToStringList.getData(data), tableInfo);
        jTable.setModel(model);

        new Thread(new TxtFileReceiveThread(inputStream, fileModel,clientFrame,jTable,data,0)).start();
    }

}

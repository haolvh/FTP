package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 对二进制文件接受的处理 这里要新开一个线程避免对其他操作的影响
 * @date 2020-07-06 17:00
 */
@Slf4j(topic = "BinaryFileReceiveHandler")
@RequiredArgsConstructor
public class BinaryFileReceiveHandler {

    private static String[] clo = null;
    private static String[] tableInfo = {"文件名","文件大小","传输状态"};
    private static DefaultTableModel model;

    public static void receiveBinaryFile(InputStream inputStream, FileModel fileModel, ClientFrame clientFrame,ArrayList<String[]> data){
        JTable downLoadTable = clientFrame.getJPanel4().getJTable1();
        clo = new String[3];
        clo[0] = fileModel.getFileName();
        clo[1] = fileModel.getFileSize();
        clo[2] = "";
        data.add(clo);

        if(data.size() > 1){
            data = ArrayListToStringList.flushData(data,clo[0],clo[1]);
        }

        model=new DefaultTableModel(ArrayListToStringList.getData(data), tableInfo);
        downLoadTable.setModel(model);

        new Thread(new BinaryFileReceiveThread(inputStream, fileModel,clientFrame,downLoadTable,data,0)).start();
    }
}

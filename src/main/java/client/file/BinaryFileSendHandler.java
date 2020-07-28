package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 二进制文件传输的处理 新开一个线程 避免对客户端其他操作的影响
 * @date 2020-07-06 17:23
 */
@Slf4j(topic = "BinaryFileReceiveHandler")
@RequiredArgsConstructor
public class BinaryFileSendHandler {

    private static String[] clo = null;
    private static String[] tableInfo = {"文件名","文件大小","传输状态"};
    private static DefaultTableModel model;

    public static void sendBinaryFile(OutputStream outputStream, FileModel fileModel, ClientFrame clientFrame,ArrayList<String[]> data){
        JTable jTable = clientFrame.getJPanel4().getJTable2();
        clo = new String[3];
        clo[0] = fileModel.getFileName();
        clo[1] = String.valueOf(new File(fileModel.getFilePath()).length());
        clo[2] = "";
        data.add(clo);

        if(data.size() > 1){
            data = ArrayListToStringList.flushData(data,clo[0],clo[1]);
        }

        model=new DefaultTableModel(ArrayListToStringList.getData(data), tableInfo);
        jTable.setModel(model);


        new Thread(new BinaryFileSendThread(outputStream,fileModel,clientFrame,jTable,data,0)).start();
    }

}

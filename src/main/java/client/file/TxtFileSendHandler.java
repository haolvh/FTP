package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 客户端文本文件的上传处理  新开一个线程  避免对客户端其他操作的影响
 * @date 2020-07-08 2:39
 */
public class TxtFileSendHandler {

    private static String[] clo = null;
    private static String[] tableInfo = {"文件名","文件大小","传输状态"};
    private static DefaultTableModel model;

    public static void sendTxtFile(OutputStream outputStream, FileModel fileModel, ClientFrame clientFrame,ArrayList<String[]> data) throws IOException {
        JTable jTable = clientFrame.getJPanel4().getJTable2();
        clo = new String[3];
        clo[0] = fileModel.getFileName();
        clo[1] = String.valueOf(FileUtil.getFileLine(fileModel.getFilePath()));
        clo[2] = "";
        data.add(clo);

        if(data.size() > 1){
            data = ArrayListToStringList.flushData(data,clo[0],clo[1]);
        }

        model=new DefaultTableModel(ArrayListToStringList.getData(data), tableInfo);
        jTable.setModel(model);

        new Thread(new TxtFileSendThread(outputStream,fileModel,clientFrame,jTable,data,0)).start();
    }

}

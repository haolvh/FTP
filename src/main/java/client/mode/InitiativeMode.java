package client.mode;

import client.file.BinaryFileReceiveHandler;
import client.file.BinaryFileSendHandler;
import client.file.TxtFileReceiveHandler;
import client.file.TxtFileSendHandler;
import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import entity.FileEnum;
import entity.FileModel;
import entity.Protocol;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 主动模式下对服务端命令的处理
 * 重写了上传和下载方法实现了对应的功能
 * @date 2020-07-05 22:14
 */
@Slf4j(topic = "InitiativeModeCli")


public class InitiativeMode extends Mode {

    private FileModel fileModel;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;

    @Override
    public void showServerDir(Protocol protocolFromSocket, ServerFilePanel serverFilePanel, DefaultTableModel model, ClientFrame clientFrame){
        if (protocolFromSocket.getData() != null) {
            log.info(protocolFromSocket.getOperateType().toString());
            int i = 0;
            ArrayList<FileModel> fileList = (ArrayList<FileModel>) protocolFromSocket.getData();
            log.info("客户端文件数量：" + fileList.size());
            String[][] data = new String[fileList.size()][4];
            String filepath = null;
            if (fileList.size() == 0) {
                data = null;
                filepath = DefaultMsg.getFilePath();
            } else {
                for (FileModel f : fileList) {
                    data[i][0] = f.getFileName();
                    data[i][1] = f.getFileSize();
                    data[i][2] = f.getChangeTime();
                    data[i][3] = String.valueOf(f.getFileType());
                    i++;
                }
                i = 0;
                filepath = fileList.get(0).getFilePath().replace(fileList.get(0).getFileName(), "");
            }
            model.setRowCount(0);
            model = new DefaultTableModel(data, tableInfo);
            serverFilePanel.getJTable().setModel(model);
            log.info("请求文件路径：" + filepath);
            serverFilePanel.getJTextField().setText(filepath);
        }
    }

    @Override
    public void download(Protocol protocolFromSocket, ClientFrame clientFrame,ArrayList<String[]> data) {
        try{
            if(clientFrame.getDataSocket() != null){
                System.out.println("socket connect");
                socket = clientFrame.getDataSocket().accept();
                inputStream = socket.getInputStream();
                fileModel = (FileModel)protocolFromSocket.getData();
                if(fileModel.getFileType().equals(FileEnum.BINARY)){
                    BinaryFileReceiveHandler.receiveBinaryFile(inputStream,fileModel,clientFrame,data);
                }else{
                    TxtFileReceiveHandler.receiveTxtFile(inputStream,fileModel,clientFrame,data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upload(Protocol protocolFromSocket, ClientFrame clientFrame,ArrayList<String[]> data){
        try{
            if(clientFrame.getDataSocket() != null){
                socket = clientFrame.getDataSocket().accept();
                outputStream=socket.getOutputStream();
                fileModel = (FileModel)protocolFromSocket.getData();
                if(fileModel.getFileType().equals(FileEnum.BINARY)){
                    BinaryFileSendHandler.sendBinaryFile(outputStream,fileModel,clientFrame,data);
                }else if(fileModel.getFileType().equals(FileEnum.TEXT)){
                    TxtFileSendHandler.sendTxtFile(outputStream,fileModel,clientFrame,data);
                }else{
                    //TODO something
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

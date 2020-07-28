package client.gui.action;

import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.util.GetTaskFilePath;
import client.util.IPUtil;
import configuration_and_constant.Constant;
import entity.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.FileUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.jar.JarOutputStream;

/**
 * @author LvHao
 * @Description : 服务端下载文件监听的实现目前只实现的单个文件的下载
 * @date 2020-07-04 23:12
 */
@Data
@RequiredArgsConstructor
@Slf4j(topic = "DownloadFile")
public class DownloadFile implements ActionListener {

    @NonNull
    private ServerFilePanel serverFilePanel;

    @NonNull
    private ClientFrame clientFrame;

    private JTable jTable;
    private String filePath;
    private String fileType;
    private String[] fileName;
    private ServerSocket socketServer;
    private Protocol protocol;

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            jTable = serverFilePanel.getJTable();
            filePath = serverFilePanel.getJTextField().getText();
            fileName = GetTaskFilePath.getDownloadName(jTable,filePath);
            fileType = GetTaskFilePath.getDownloadFileType(jTable);

            protocol = new Protocol();
            socketServer = new ServerSocket(0);
            log.info(socketServer.toString());

            String oneFile = fileName[0].substring(fileName[0].lastIndexOf(File.separator)+1,fileName[0].length());
            log.info("请求下载文件名：" + oneFile);
            log.info("本地需要建立替代文件名：" + Constant.DEFAULT_PATH + oneFile + ".temp");
            for(int i = 0;i < fileName.length;i++){
                log.info("请求下载文件路径：" + fileName[i]);
            }

            FileModel fileModel = new FileModel();
            fileModel.setFileName(oneFile);
            fileModel.setFilePath(fileName[0]);
            if(fileType.equals("BINARY")){
                fileModel.setFileType(FileEnum.BINARY);
                File file = new File(Constant.DEFAULT_PATH + oneFile + ".temp");
                long size = 0;
                if(file.exists() && file.isFile()){
                    size = file.length();
                }
                fileModel.setFileSize(String.valueOf(size));
            }else if(fileType.equals("TEXT")){
                File file = new File(Constant.DEFAULT_PATH + oneFile + ".temp");
                long size = 0;
                if(file.exists() && file.isFile()){
                    size = FileUtil.getFileLine(file.getName());
                }
                fileModel.setFileSize(String.valueOf(size));
                fileModel.setFileType(FileEnum.TEXT);
            }else{
                fileModel.setFileType(FileEnum.DIR);
            }

            protocol.setData(fileModel);
            protocol.setServiceIp(clientFrame.getJPanel2().getJt1().getText());
            protocol.setCommandPort(Integer.valueOf(clientFrame.getJPanel2().getJt3().getText()));
            protocol.setDataPort(socketServer.getLocalPort());
            protocol.setOperateType(OperateType.DOWNLOAD);
            protocol.setClientIp(IPUtil.getLocalIP());
            protocol.setConnectType(ConnectType.INITIATIVE);
            clientFrame.dataSocket = socketServer;

            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        }catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            new MessageDialog("提示","请先选择文件！",clientFrame).init();
        } catch (IOException ioException) {
            new MessageDialog("提示","无法打开数据端口",clientFrame).init();
        }
    }
}

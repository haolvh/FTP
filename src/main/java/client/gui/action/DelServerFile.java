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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author LvHao
 * @Description : 删除按钮的监听
 * 点击删除按钮给服务端发送删除命令
 * 命令包括需要删除服务端的文件路径
 * @date 2020-07-08 1:04
 */
@Data
@RequiredArgsConstructor
@Slf4j(topic = "DelServerFile")
public class DelServerFile implements ActionListener {

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

            for(int i = 0; i < fileName.length;i++){
                log.info("需要删除的文件路径：" + fileName[0]);
            }
            protocol = new Protocol();
            protocol.setData(fileName[0]);
            protocol.setOperateType(OperateType.DELETE);
            protocol.setConnectType(ConnectType.INITIATIVE);

            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        }catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            new MessageDialog("提示","请先选择文件！",clientFrame).init();
        }
    }
}

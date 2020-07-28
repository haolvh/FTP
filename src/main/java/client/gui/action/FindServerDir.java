package client.gui.action;

import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import client.util.IPUtil;
import entity.ConnectType;
import entity.OperateType;
import entity.Protocol;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author LvHao
 * @Description : 遍历服务端文件夹功能  返回服务端文件夹的上一级
 * @date 2020-07-05 4:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Slf4j(topic = "FindServerDir")
public class FindServerDir extends MouseAdapter {

    private static  boolean flag = false;
    private static int clickNum = 1;
    private String[] tableInfo = {"文件名","大小","日期"};
    private String systemFlag = File.separator;
    private JTable jTable;
    private String filePath;


    @NonNull
    private ServerFilePanel serverFilePanel;
    @NonNull
    private ClientFrame clientFrame;


    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        final java.awt.event.MouseEvent me = e;
        FindServerDir.flag= false;
        if (FindServerDir.clickNum==2) {
            //鼠标点击次数为2调用双击事件
            this.mouseClickedTwice(me);
            //调用完毕clickNum置为1
            FindServerDir.clickNum=1;
            FindServerDir.flag=true;
            return;
        }
        //新建定时器，双击检测间隔为500ms
        java.util.Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            //指示定时器执行次数
            int num = 0;
            @Override
            public void run() {
                // 双击事件已经执行，取消定时器任务
                if(FindServerDir.flag) {
                    num=0;
                    FindServerDir.clickNum=1;
                    this.cancel();
                    return;
                }
                //定时器再次执行，调用单击事件，然后取消定时器任务
                if (num==1) {
                    FindServerDir.flag=true;
                    FindServerDir.clickNum=1;
                    num=0;
                    this.cancel();
                    return;
                }
                clickNum++;
                num++;
            }
        },new Date(), 200);
    }
    private void mouseClickedTwice(java.awt.event.MouseEvent me) {
        // 双击事件
        int row = serverFilePanel.getJTable().rowAtPoint(me.getPoint());
        String fileName = serverFilePanel.getJTextField().getText()+serverFilePanel.getJTable().getValueAt(row,0).toString();
        log.info(fileName);
        DefaultMsg.setFilePath(fileName);
        if((serverFilePanel.getJTable().getValueAt(row,1).toString()).equals("<DIR>")){
            Protocol protocol = new Protocol();
            protocol.setServiceIp(clientFrame.getProtocol().getServiceIp());
            protocol.setCommandPort(clientFrame.getProtocol().getCommandPort());
            protocol.setClientIp(IPUtil.getLocalIP());
            protocol.setConnectType(ConnectType.INITIATIVE);
            protocol.setOperateType(OperateType.FILE_PATH);
            protocol.setData(fileName);
            protocol.setDataPort(clientFrame.getProtocol().getDataPort());
            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        }
    }

}
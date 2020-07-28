package client.gui;

import client.gui.panel.CenterPanel;
import client.gui.panel.DefaultInfoPanel;
import client.gui.panel.TaskPanel;
import client.gui.panel.TopPanel;
import entity.Protocol;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LvHao
 * @Description : 客户端窗口
 * @date 2020-07-03 1:48
 */
@Data
public class ClientFrame extends JFrame {
    public Socket PsvdataSocket = null;
    public ServerSocket dataSocket = null;
    public Socket socket = null;
    public ServerSocket serverSocket=null;
    private ObjectInputStream socketObjectInputStream;
    private ObjectOutputStream socketObjectOutputStream;
    private Protocol protocol = null;
    private DefaultInfoPanel jPanel2;
    private CenterPanel jPanel3;
    private TaskPanel jPanel4;
    private final int WEIGHT = 1000;
    private final int HEIGHT = 720;
    private final ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("FTP图标.jpg"));
    /**
     * 构造函数初始化Client窗体
     */
    public ClientFrame(){
        initLClient();
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return this.socket;
    }

    /**
     * 初始化Client界面
     */
    private void initLClient() {
        //窗体的图标和名称
        setIconImage(icon.getImage());
        setTitle("FTP");
        //窗体大小的设置 居中
        setSize(WEIGHT,HEIGHT);
        setLocationRelativeTo(null);
        //设置背景颜色
        setBackground(Color.WHITE);
        //点击×关闭窗体
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        /**
         * 设置顶部面板
         */
        jPanel2 = new DefaultInfoPanel(this);
        JPanel jPanel1 = new TopPanel(this,jPanel2);
        add(jPanel1,new MyGridBagConstraints(0,0,1,1).init1());

        /**
         * 设置默认信息面板
         */
        add(jPanel2,new MyGridBagConstraints(0,1,1,1).init1());

        /**
         * 设置本地和远程文件面板
         */
        jPanel3 = new CenterPanel(this);
        add(jPanel3,new MyGridBagConstraints(0,2,1.7,1.7).init2());

        /**
         * 设置任务队列面板
         */
        jPanel4 = new TaskPanel();
        add(jPanel4,new MyGridBagConstraints(0,3,0.5,0.5).init2());

    }
}
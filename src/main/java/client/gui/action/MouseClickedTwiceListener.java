package client.gui.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetFiles;
import lombok.*;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author LvHao
 * @Description : 表格鼠标点击两次事件 进入到文件夹的下一级
 * @date 2020-07-03 2:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class MouseClickedTwiceListener extends MouseAdapter {

    private static  boolean flag = false;
    private static int clickNum = 1;
    private String[] tableInfo = {"文件名","大小","日期","文件类型"};
    private String systemFlag = File.separator;

    @NonNull
    private LocalFilePanel localFilePanel;
    @NonNull
    private DefaultTableModel model;

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        final MouseEvent me = e;
        MouseClickedTwiceListener.flag= false;
        if (MouseClickedTwiceListener.clickNum==2) {
            //鼠标点击次数为2调用双击事件
            this.mouseClickedTwice(me);
            //调用完毕clickNum置为1
            MouseClickedTwiceListener.clickNum=1;
            MouseClickedTwiceListener.flag=true;
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
                if(MouseClickedTwiceListener.flag) {
                    num=0;
                    MouseClickedTwiceListener.clickNum=1;
                    this.cancel();
                    return;
                }
                //定时器再次执行，调用单击事件，然后取消定时器任务
                if (num==1) {
                    MouseClickedTwiceListener.flag=true;
                    MouseClickedTwiceListener.clickNum=1;
                    num=0;
                    this.cancel();
                    return;
                }
                clickNum++;
                num++;
            }
        },new Date(), 200);
    }
    private void mouseClickedTwice(MouseEvent me) {
        // 双击事件
        int row = localFilePanel.getJTable().rowAtPoint(me.getPoint());
        String fileName = localFilePanel.getJTextField().getText()+localFilePanel.getJTable().getValueAt(row,0).toString();
        System.out.println(localFilePanel.getJTextField().getText());
        if(!new File(fileName).isFile()){
            model.setRowCount(0);
            String[][] data = GetFiles.getFiles(fileName);
            model = new DefaultTableModel(data,tableInfo);
            localFilePanel.getJTable().setModel(model);
            localFilePanel.getJTextField().setText(fileName+systemFlag);
        }
    }
}
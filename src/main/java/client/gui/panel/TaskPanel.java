package client.gui.panel;

import client.gui.ClientFrame;
import client.gui.table.LocalFileTable;
import client.gui.table.ProgressCellRender;
import lombok.Data;
import lombok.NonNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 上传下载的任务面板
 * @date 2020-07-03 2:11
 */
@Data
public class TaskPanel extends JTabbedPane {
    private String[]  tableInfo = {"文件名","文件大小","传输状态"};
    private String[][] data = null;

    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private JTable jTable1;
    private JTable jTable2;

    public TaskPanel(){
        init();
    }

    private void init(){
        model1=new DefaultTableModel(data, tableInfo);
        jTable1 = new LocalFileTable(model1);
        jTable1.setEnabled(false);

        model2=new DefaultTableModel(data, tableInfo);
        jTable2 = new LocalFileTable(model2);
        jTable2.setEnabled(false);

        add("下载队列",new JScrollPane(jTable1));
        add("上传队列",new JScrollPane(jTable2));
    }
}


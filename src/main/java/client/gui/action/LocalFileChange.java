package client.gui.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetFiles;
import client.util.OSinfo;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

/**
 * @author LvHao
 * @Description : 当前目录里的文件和目录展示
 * @date 2020-07-03 2:06
 */
@Data
@RequiredArgsConstructor
public class LocalFileChange implements ItemListener {

    private String[][] data = null;
    private final String[] tableInfo = {"文件名","大小","日期","文件类型"};

    @NonNull
    private LocalFilePanel localFilePanel;
    @NonNull
    private DefaultTableModel model;

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            model.setRowCount(0);
            data = GetFiles.getFiles(localFilePanel.getJComboBox());
            model = new DefaultTableModel(data,tableInfo);
            localFilePanel.getJTable().setModel(model);
            if(OSinfo.getOS() == OSinfo.OS.LINUX) {
                localFilePanel.getJTextField().setText(String.valueOf(localFilePanel.getJComboBox().getSelectedItem()) + File.separator);
            }else{
                localFilePanel.getJTextField().setText(String.valueOf(localFilePanel.getJComboBox().getSelectedItem()));
            }
        }
    }
}


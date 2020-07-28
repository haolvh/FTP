package client.gui.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetFiles;
import client.util.OSinfo;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author LvHao
 * @Description : 返回上一级目录的处理
 * @date 2020-07-04 20:27
 */
@Data
@RequiredArgsConstructor
public class Return implements ActionListener {

    private String[][] data = null;
    private final String[] tableInfo = {"文件名", "大小", "日期", "文件类型"};
    private String filePath;

    @NonNull
    private LocalFilePanel localFilePanel;
    @NonNull
    private DefaultTableModel model;

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setRowCount(0);
        String firePath = localFilePanel.getJTextField().getText();
        firePath = new File(firePath).getAbsolutePath();
        firePath = new File(firePath).getParent();
        if (firePath == null) {
            data = GetFiles.getFiles(localFilePanel.getJComboBox());
            model = new DefaultTableModel(data, tableInfo);
            localFilePanel.getJTable().setModel(model);

        } else {
            data = GetFiles.getFiles(firePath);
            model = new DefaultTableModel(data, tableInfo);
            localFilePanel.getJTable().setModel(model);
            if (firePath.equals(String.valueOf(localFilePanel.getJComboBox().getSelectedItem()))) {
                if (OSinfo.getOS() == OSinfo.OS.LINUX) {
                    localFilePanel.getJTextField().setText(firePath + File.separator);
                } else {
                    localFilePanel.getJTextField().setText(firePath);
                }
            } else {
                localFilePanel.getJTextField().setText(firePath + File.separator);
            }
        }
    }
}

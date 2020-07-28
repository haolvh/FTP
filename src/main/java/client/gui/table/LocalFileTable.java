package client.gui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author LvHao
 * @Description : 本地文件表格
 * @date 2020-07-03 2:04
 */
public class LocalFileTable extends JTable {

    public LocalFileTable(String[][] rowData, String[] columnNames)
    {
        super(rowData,columnNames);
    }

    public LocalFileTable(DefaultTableModel model){
        super(model);
    }

    /**
     * 设置表格不可更改
     * @param row
     * @param column
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

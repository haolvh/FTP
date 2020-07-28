package client.gui.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-06 23:07
 */
public class ProgressCellRender extends JProgressBar implements TableCellRenderer {

    private int size;
    public ProgressCellRender(int i){
        size = i;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        setStringPainted(true);
        setValue(size);
        return  this;
    }
}

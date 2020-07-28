package client.gui.msg;

import client.gui.ClientFrame;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 信息提示对话框
 * @date 2020-07-05 23:54
 */
@Data
@RequiredArgsConstructor
public class MessageDialog {

    private String[][] data = null;
    private final String[] tableInfo = {"文件名","大小","日期","文件类型"};

    private final int X = 300;
    private final int Y = 200;
    private final ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("error.png"));

    private JDialog jDialog = new JDialog();
    private JLabel jLabel;

    @NonNull
    private String title;
    @NonNull
    private String msg;
    @NonNull
    private ClientFrame clientFrame;

    @SneakyThrows
    public JDialog init(){
        try{
            if(clientFrame.getSocket() != null){
                clientFrame.getSocket().close();
                clientFrame.setSocket(null);
            }
            clientFrame.getJPanel3().getJPanel2().getModel().setRowCount(0);
            DefaultTableModel model = new DefaultTableModel(data,tableInfo);
            clientFrame.getJPanel3().getJPanel2().getJTable().setModel(model);
            clientFrame.getJPanel3().getJPanel2().getJTextField().setText("");
            clientFrame.getJPanel2().getJComboBox().setEnabled(true);
            clientFrame.getJPanel2().getJt1().setEditable(true);
            clientFrame.getJPanel2().getJt2().setEditable(true);
            clientFrame.getJPanel2().getJt3().setEditable(true);
            clientFrame.getJPanel2().getJPasswordField().setEditable(true);
            clientFrame.getJPanel2().remove(clientFrame.getJPanel2().getJLabel());
            clientFrame.getJPanel2().add(clientFrame.getJPanel2().getJButton());
            clientFrame.getJPanel2().updateUI();
            jDialog.setIconImage(icon.getImage());
            jDialog.setTitle(title);

            jLabel = new JLabel(msg,JLabel.CENTER);
            jLabel.setFont(new Font("宋体",Font.PLAIN,16));
            jDialog.add(jLabel);

            jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jDialog.setSize(X,Y);
            jDialog.setLocationRelativeTo(null);
            jDialog.setVisible(true);
            return jDialog;
        }catch (NullPointerException e){
            jDialog.setIconImage(icon.getImage());
            jDialog.setTitle(title);

            jLabel = new JLabel(msg,JLabel.CENTER);
            jLabel.setFont(new Font("宋体",Font.PLAIN,16));
            jDialog.add(jLabel);

            jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jDialog.setSize(X,Y);
            jDialog.setLocationRelativeTo(null);
            jDialog.setVisible(true);
            return jDialog;
        }
    }

    public JDialog del(){
        jDialog = new JDialog();
        jDialog.setTitle(title);

        jLabel = new JLabel(msg,JLabel.CENTER);
        jLabel.setFont(new Font("宋体",Font.PLAIN,16));
        jDialog.add(jLabel);

        jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jDialog.setSize(X,Y);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        return jDialog;
    }

}

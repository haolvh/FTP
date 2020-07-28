package client.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.*;

/**
 * @author LvHao
 * @Description : 面板网格袋布局的工具类
 * @date 2020-07-04 13:21
 */
@RequiredArgsConstructor
public class MyGridBagConstraints extends GridBagConstraints {
    @NonNull
    private int x;
    @NonNull
    private int y;
    @NonNull
    private double width;
    @NonNull
    private double height;

    public GridBagConstraints init1(){
        this.gridx= x;
        this.gridy= y;
        this.gridwidth = (int) width;
        this.gridheight = (int) height;
        this.fill = GridBagConstraints.BOTH;
        return this;
    }

    public GridBagConstraints init2(){
        this.gridx= x;
        this.gridy= y;
        this.weightx = width;
        this.weighty = height;
        this.fill = GridBagConstraints.BOTH;
        return this;
    }
}

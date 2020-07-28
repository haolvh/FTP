package entity;

import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
import server.Port;
import server.SendFileByByte;
import server.SendFileByLine;
import util.FileUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author heguicai
 * @date 2020/7/4 22:31
 */
public class InitiativeMode extends Mode {
    @Override
    public void initialize(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException {
        ArrayList<FileModel> fileList = FileUtil.getFileList(Constant.DEFAULT_FILE_PATH);
        protocolFromSocket.setData(fileList);
        System.out.println(protocolFromSocket.toString());
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
    }

    @Override
    public Socket getDataSocket(String address, Integer port) {
        try {
            super.dataSocket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(super.dataSocket);
        return super.dataSocket;
    }

}

package entity;

import client.util.IPUtil;
import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.Port;
import server.SendFileByByte;
import server.SendFileByLine;
import util.CommonUtil;
import util.FileUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/4 22:32
 */
public class PassiveMode extends Mode{
    @Override
    public void initialize(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException {
        Integer port = CommonUtil.generateRandomPort();
        // 构造协议信息(server 数据端口号)
        protocolFromSocket.setDataPort(port);
        protocolFromSocket.setOperateType(OperateType.CONNECT);
        protocolFromSocket.setServiceIp(IPUtil.getLocalIP());
        protocolFromSocket.setData(FileUtil.getFileList(Constant.DEFAULT_FILE_PATH));
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        // 等待客户端建立 data 端口
        dataSocket = new GenerateDataSocket().generateInPassiveMode(port);
    }

    @Override
    public Socket getDataSocket(String address, Integer port) {
        return this.dataSocket;
    }
}

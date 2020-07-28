package server;

import entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.FileUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * 服务端处理命令输入的线程
 *
 * @author yinchao
 * @date 2020/7/2 17:13
 */
@AllArgsConstructor
@Data
@Slf4j(topic = "ServerCommandHandler")
@NoArgsConstructor
@Builder
public class ServerCommandHandler implements Runnable {
    Socket commandSocket;
    Mode mode;

    @Override
    public void run() {
        log.info("InputHandler is running...");
        if (commandSocket == null) {
            log.error("socket未建立");
            return;
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(commandSocket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(commandSocket.getInputStream()))) {
            // 读入协议信息
            Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
            while(objectInputStream.readObject()!=null) {

            }
            // 如果是被动模式
            if (ConnectType.PASSIVE.equals(protocolFromSocket.getConnectType())) {
                mode = new PassiveMode();
                // 主动模式
            } else {
                mode = new InitiativeMode();
            }
            while (true) {
                log.info(protocolFromSocket.toString());
                switch (protocolFromSocket.getOperateType()) {
                    case CONNECT: {
                        if (mode.authenticate(protocolFromSocket)) {
                            mode.initialize(objectOutputStream, protocolFromSocket);
                        } else {
                            mode.sendAuthenticateFailMessage(objectOutputStream,protocolFromSocket);
                        }
                        break;
                    }
                    case DOWNLOAD: {
                        mode.download(protocolFromSocket, objectOutputStream);
                        break;
                    }
                    case PAUSE: {
                        mode.pause();
                        break;
                    }
                    case FILE_PATH: {
                        Protocol protocol = new Protocol();
                        Object data = mode.getFileList((String) protocolFromSocket.getData());
                        protocol.setData(data);
                        protocol.setOperateType(protocolFromSocket.getOperateType());
                        objectOutputStream.writeObject(protocol);
                        objectOutputStream.writeObject(null);
                        objectOutputStream.flush();
                        break;
                    }
                    case UPLOAD: {
                        mode.upload(protocolFromSocket,objectOutputStream);
                        break;
                    }
                    case RETURN_FATHER_DIR: {
                        FileModel fileModel = (FileModel) protocolFromSocket.getData();
                        String fatherDir = FileUtil.getFatherDir(fileModel.getFilePath());
                        Protocol protocol = new Protocol();
                        ArrayList<FileModel> fileList = FileUtil.getFileList(fatherDir);
                        protocol.setData(fileList);
                        protocol.setOperateType(protocolFromSocket.getOperateType());
                        objectOutputStream.writeObject(protocol);
                        objectOutputStream.writeObject(null);
                        objectOutputStream.flush();
                        break;
                    }
                    case DELETE:{
                        mode.delete((String)protocolFromSocket.getData(),objectOutputStream,protocolFromSocket);
                        break;
                    }
                }
                // 读入协议信息
                protocolFromSocket = (Protocol) objectInputStream.readObject();
                while(objectInputStream.readObject()!=null) {
//                    socket.shutdownInput();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.error("反序列化失败");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e){
            log.error(e.getMessage());
        }
    }
}
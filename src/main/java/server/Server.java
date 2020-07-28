package server;

import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/2 11:49
 */
@Slf4j(topic = "Server")
public class Server {
    ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    /**
     * 执行 server 主要逻辑
     */
    private void start() {
        // 初始化线程池
        threadPoolExecutor = ThreadPool.getThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(Constant.COMMANDPORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ServerCommandHandler serverCommandHandler = ServerCommandHandler.builder().commandSocket(socket).build();
                threadPoolExecutor.submit(serverCommandHandler);
            }
        } catch (IOException e) {
            log.error(e.getClass().getSimpleName(), e);
        }
    }
}

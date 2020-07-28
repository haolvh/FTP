import configuration_and_constant.Constant;
import entity.ConnectType;
import entity.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import util.CommonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author yinchao
 * @date 2020/7/3 08:45
 */
@Slf4j(topic = "ClientTest")
public class ClientTest {
    @Test
    public void testConnection() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", Constant.COMMANDPORT);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        try (OutputStream socketOutputStream = socket.getOutputStream()) {

            byte[] dataContent = ConnectType.PASSIVE.toString().getBytes();
            // 这里不能超出4个字节
            byte[] dataLength = CommonUtil.IntegerToBytes(dataContent.length);
            CommonUtil.ensureLength(dataLength, Constant.HEAD_BYTE_SIZE);
            socketOutputStream.write(dataLength);
            socketOutputStream.flush();
            socketOutputStream.write(dataContent);
            socketOutputStream.flush();
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
    }
}

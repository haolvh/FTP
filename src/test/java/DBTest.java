import entity.User;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import server.DatabaseService;

import java.io.IOException;
import java.sql.*;

/**
 * @author yinchao
 * @date 2020/7/6 17:35
 */
public class DBTest {
    @Test
    public void createDB() {
        Jdbi jdbi = Jdbi.create("jdbc:h2:file:./FTP;AUTO_SERVER=TRUE");
        jdbi.useHandle(handle -> {
            handle.execute("create table user (id Long identity primary key, userName varchar(255) unique, password varchar(255))");
        });
    }

    @Test
    public void testInsert() throws IOException , SQLException,ClassNotFoundException {
        DatabaseService databaseService = new DatabaseService();
        final User user = User.builder().userName("root").password("root").build();
        databaseService.createUser(user);
        System.out.println(databaseService.isValidUser(user));
    }
}

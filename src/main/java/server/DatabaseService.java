package server;

import entity.User;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

/**
 * @author yinchao
 * @date 2020/7/6 14:45
 */
public class DatabaseService {
    public void createUser(User user) {
        final Jdbi jdbi = Jdbi.create("jdbc:h2:file:./FTP;AUTO_SERVER=TRUE");
        jdbi.useHandle(handle -> {
            handle.createUpdate("insert into user (userName,password) values (:userName,:password)")
                    .bindBean(user)
                    .execute();
        });
    }

    public boolean isValidUser(User user) {
        final Jdbi jdbi = Jdbi.create("jdbc:h2:file:./FTP;AUTO_SERVER=TRUE");
        final Optional<Long> result = jdbi.withHandle(handle -> {
            return handle.createQuery("select id from user where userName = :userName and password = :password")
                    .bindBean(user)
                    .mapTo(Long.class)
                    .findOne();
        });
        return result.isPresent();
    }
}

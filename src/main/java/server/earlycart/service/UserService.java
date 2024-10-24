package server.earlycart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import server.earlycart.model.User;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    JdbcTemplate db;

    public String register(String id, String pw, String name, String birth, String phone) {
        try {
            boolean checkId = db.queryForObject("select count(*) from users where id = ?;", Integer.class, id) > 0;
            if (checkId) return "2";
            else {
                db.update("insert into users values (?, ?, ?, ?, ?, 'user', null);", id, pw, name, birth, phone);
                return "1";
            }
        } catch (Exception e) {
            return "0";
        }
    }

    public String login(String id, String pw) {
        try {
            boolean checkLogin = pw.equals(db.queryForObject("select pw from users where id = ?;", String.class, id));
            if (checkLogin) {
                return updateSession(id);
            } else return "0";
        } catch (Exception e) {
            return "0";
        }
    }

    public String updateSession(String id) {
        try {
            Random random = new Random();
            StringBuffer session = new StringBuffer();
            for (int i = 1; i <= 100; i++) {
                int result = random.nextInt(3);
                if (result == 0) session.append((char) (random.nextInt(26) + 65));
                else if (result == 1) session.append((char) (random.nextInt(26) + 97));
                else session.append(random.nextInt(10));
            }
            db.update("update users set session = ? where id = ?;", session.toString(), id);
            return session.toString();
        } catch (Exception e) {
            return "0";
        }
    }

    public User getUserBySession(String session) {
        try {
            User user = db.queryForObject("select * from users where session = ?;",
                    (rs, row) -> {
                        return new User(rs.getString(1), null, rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), null);},
                    session);
            return user;
        } catch (Exception e) {
            return null;
        }

    }
}
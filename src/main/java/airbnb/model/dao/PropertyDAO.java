package airbnb.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyDAO {

    private JdbcTemplate db;

    @Autowired
    public PropertyDAO(JdbcTemplate db) {
        this.db = db;
    }

    public void filter() {
//        String sql = "";
//        try {
//            db.
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }
}

package airbnb.model.dao;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Builder
@Repository
public class PropertyDAO {

    private JdbcTemplate db;

    //todo extra


    @Autowired
    public PropertyDAO(JdbcTemplate db) {
        this.db = db;
    }

    public void filter() {

//        db.
//        String sql = "";
//        try {
//            db.
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }
}

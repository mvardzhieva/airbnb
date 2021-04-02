package airbnb.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DataLoader implements CommandLineRunner {

    private JdbcTemplate db;

    @Autowired
    public DataLoader(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public void run(String... args) throws Exception {
        Files.lines(Paths.get("src/main/resources/data.sql"))
                .forEach(sql -> db.update(sql));
    }
}

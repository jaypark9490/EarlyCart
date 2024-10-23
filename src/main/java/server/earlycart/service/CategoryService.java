package server.earlycart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import server.earlycart.model.Category;
import server.earlycart.model.Item;

import java.util.ArrayList;

@Service
public class CategoryService {
    @Autowired
    JdbcTemplate db;

    public ArrayList<Category> getCategory() {
        ArrayList<Category> categoryList = new ArrayList<>();
        try {
            db.query("select * from categories;",
                    rs -> { categoryList.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(3)));});
            return categoryList;
        } catch (Exception e) {
            return null;
        }
    }

}

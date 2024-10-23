package server.earlycart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import server.earlycart.model.Item;
import server.earlycart.model.User;

import java.util.ArrayList;

@Service
public class ItemService {

    @Autowired
    JdbcTemplate db;

    @Autowired
    ChatGPTService chatGPTService;

    public Item getItemById(String id) {
        try {
            Item item = db.queryForObject("select * from items where id = ?;",
                    (rs, row) -> {
                        return new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6));},
                    id);
            return item;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Item> getItemListByCategory(String category) {
        ArrayList<Item> itemList = new ArrayList<>();
        try {
            db.query("select * from items where category = ? order by rand();",
                    rs -> { itemList.add(new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));},
                    category);
            return itemList;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Item> getItemListByName(String name) {
        ArrayList<Item> itemList = new ArrayList<>();
        try {
            db.query("select * from items where name like ?;",
                    rs -> { itemList.add(new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));},
                    "%" + name + "%");
            return itemList;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Item> getItemListByRandom(String limit) {
        ArrayList<Item> itemList = new ArrayList<>();
        try {
            db.query("select * from items order by rand() limit ?;",
                    rs -> { itemList.add(new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));},
                    limit);
            return itemList;
        } catch (Exception e) {
            return null;
        }
    }

    public String getItemListByChatGPT(String id) {

        StringBuffer data = new StringBuffer();
        data.append("id,name\\n");
        try {
            db.query("select * from items where id != ?;",
                    rs -> {
                        data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n");},
                    id);
        } catch (Exception e) {
            return null;
        }

        String itemName = getItemById(id).getName();
        String prompt =
                "\\n'" + itemName + "' 함께 구입하면 어울리는 상품을 추천해줘." +
                "창의적으로 상품을 추천해줘." +
                "추천순위가 높은 상품은 앞쪽으로 정렬해줘." +
                "대답은 id,name,추천이유로 csv 데이터만 출력해줘." +
                "각 값은 따옴표로 감싸줘.";
        String result = chatGPTService.getResponse(data.toString() + prompt);
        System.out.println(result);
        return result;
    }




}
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

    public ArrayList<Item> getItemListByRandom(int limit) {
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

    public ArrayList<Item> getItemListTest(String id) {

        StringBuilder data = new StringBuilder();
        data.append("id,name\\n");

        try {
            db.query("select * from items;",
                    rs -> {
                        data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n");},
                    id);
        } catch (Exception e) {
            return null;
        }

        String itemName = getItemById(id).getName();

        String prompt = "\\n'" + itemName + "'을 구입했을때 활용방안을 여러가지 설명해줘. 활용방안을 최대한 자세하게 100자 이내로 답변해줘. 답변할때 내가 말한 상품의 단위는 빼줘." +
                "\\n앞에 상품과 함께 추가로 구매하면 좋을것 같은 상품도 추천해줘. 추천상품을 최대한 자세하게 100자 이내로 설명해주고, 답변 맨 뒤에 추천상품 id 목록을 띄어쓰기 없이 콤마로 구분해서 추가해줘. 적어도 5개 이상의 상품을 추천해주고, 추천도가 높은 상품은 목록의 앞쪽으로 정렬해줘." +
                "답변에 활용방안과 추천상품 id를 '<split>' 로 구분할수 있게 대답해줘. 예를들면, '활용방안 + 추천상품 <split>1,2,3,4,5' 이것처럼 답변해줘." +
                "모든 답변은 존댓말로 해줘.";

        String[] result = chatGPTService.getResponse(data.toString() + prompt).split("<split>");

        ArrayList<Item> itemList = new ArrayList<>();

        itemList.add(new Item(9999, result[0], null, 0, 0, null));

        for (int i = 0; i < result[1].split(",").length; i++) {
            if (result[1].split(",")[i] == id) continue;
            itemList.add(getItemById(result[1].split(",")[i]));
        }

        return itemList;
    }


}
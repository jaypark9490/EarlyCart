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
                        return new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7));},
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
                    rs -> { itemList.add(new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7)));},
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
                    rs -> { itemList.add(new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7)));},
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
                    rs -> { itemList.add(new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7)));},
                    limit);
            return itemList;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Item> getItemListByChatGPT1(String id) {

        ArrayList<Item> itemList = new ArrayList<>();
        StringBuilder data = new StringBuilder("id,name\\n");
        String itemName = getItemById(id).getName();
        try {
            db.query("select * from items;", rs -> { data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n" );});

            String prompt = "\\n'" + itemName + "'을 구입했을때 활용방안을 여러가지 설명해줘. 활용방안을 자세하게 200자 이내로 답변해줘. 답변할때 내가 말한 상품의 단위는 빼줘." +
                    "\\n앞에 상품과 함께 추가로 구매하면 좋을것 같은 상품도 추천해줘. 추천상품을 자세하게 200자 이내로 설명해주고, 답변 맨 뒤에 추천상품 id 목록을 띄어쓰기 없이 콤마로 구분해서 추가해줘. 적어도 5개 이상의 상품을 추천해줘." +
                    "답변에 활용방안과 추천상품 id를 '<split>' 로 구분할수 있게 대답해줘. 예를들면, 답변 마지막에 '<split>1,2,3,4,5' 이와 같이 추가해줘." +
                    "모든 답변은 존댓말로 해줘.";

            String[] result = chatGPTService.getResponse(data.toString() + prompt).split("<split>");

            itemList = new ArrayList<>();

            itemList.add(new Item(9999, result[0], null, 0, 0, null, null));

            for (int i = 0; i < result[1].split(",").length; i++) {
                if (result[1].split(",")[i].equals(id)) continue;
                itemList.add(getItemById(result[1].split(",")[i]));
            }

            return itemList;

        } catch (Exception e) {
            itemList = getItemListByRandom(10);

            String prompt = "\\n'" + itemName + "'을 구입했을때 활용방안을 여러가지 설명해줘. 활용방안을 자세하게 200자 이내로 답변해줘. 답변할때 내가 말한 상품의 단위는 빼줘.";

            String result = chatGPTService.getResponse(data.toString() + prompt);

            itemList.add(0, new Item(9999, result, null, 0, 0, null, null));
            return itemList;
        }
    }


    public ArrayList<Item> getItemListByChatGPT2(String cartItemList) {

        ArrayList<Item> itemList = new ArrayList<>();
        StringBuilder data = new StringBuilder("id,name\\n");
        try {
            db.query("select * from items;", rs -> { data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n" );});

            String prompt = data + "\\n위 데이터는 현재 마트에 있는 상품들이야.\\n현재 장바구니에는 상품 id\\n" + cartItemList + "\\n 가 담겨있어." +
                    "장바구니에 담겨있는 상품들과 함께 구매해도 좋은 상품을 추천해줘." +
                    "적어도 5개 이상의 상품을 추천해줘. 답변은 상품 id만 출력해줘. 예를들면, '1,2,3,4,5' 이와 같이 출력해줘.";

            String[] result = chatGPTService.getResponse(data.toString() + prompt).split(",");

            itemList = new ArrayList<>();

            System.out.println(cartItemList);
            for (int i = 0; i < result.length; i++) {
                itemList.add(getItemById(result[i]));
                System.out.println(getItemById(result[i]).getId());
            }

            return itemList;

        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Item> getItemListByChatGPT3(String content) {

        ArrayList<Item> itemList = new ArrayList<>();
        StringBuilder data = new StringBuilder("id,name\\n");
        try {
            db.query("select * from items;", rs -> { data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n" );});

            String prompt = data + "\\n위 데이터는 현재 마트에 있는 상품들이야. 마트에 있는 상품들을 기반으로 검색어와 관련된 상품을 추천해줘.\\n" +
                    "만약 검색어가 음식이면 요리 레시피에 필요한 재료들을 추천해줘." +
                    "검색어 '" + content + "'\\n" +
                    "답변은 상품 id만 출력해줘. 예를들면, '1,2,3,4,5' 이와 같이 출력해줘. 결과가 없으면 'null' 출력해줘.";

            String[] result = chatGPTService.getResponse(data.toString() + prompt).split(",");

            itemList = new ArrayList<>();

            if (result[0].equals("null")) {
                return itemList;
            }

            for (int i = 0; i < result.length; i++) {
                itemList.add(getItemById(result[i]));
            }

            return itemList;

        } catch (Exception e) {
            return null;
        }
    }

}
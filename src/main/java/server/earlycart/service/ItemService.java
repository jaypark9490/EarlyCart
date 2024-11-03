package server.earlycart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import server.earlycart.model.Item;
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

    public Item getItemByBarcode(String barcode) {
        try {
            Item item = db.queryForObject("select * from items where barcode = ?;",
                    (rs, row) -> {
                        return new Item(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7));},
                    barcode);
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

            String prompt = "\\n위 데이터는 마트에 있는 상품들이야.\\n" +
                    "'" + itemName + "'을 구입했을때 활용방안을 여러가지 설명해줘. 활용방안을 자세하게 200자 이내로 설명해줘. 답변할때 내가 말한 상품의 단위는 빼줘.\\n" +
                    "'" + itemName + "'와 비슷하거나 잘 어울리는 상품 목록을 추천해줘. 추천상품 목록을 자세하게 200자 이내로 설명해줘. 설명할때 상품 id는 빼줘.\\n" +
                    "설명한 모든 상품 id 목록을 콤마로 구분해서 맨 뒤에 추가해줘. 적어도 5개 이상의 상품을 추천해줘.\\n" +
                    "답변에 활용방안과 추천상품 id를 '<split>' 로 구분할수 있게 대답해줘. 예를들면, 답변 마지막에 '<split>1,2,3,4,5' 이와 같이 추가해줘.";

            String[] result = chatGPTService.getResponse(data.toString() + prompt).split("<split>");

            itemList.add(new Item(9999, result[0], null, 0, 0, null, null));

            for (int i = 0; i < result[1].split(",").length; i++) {
                if (result[1].split(",")[i].equals(id)) continue;
                itemList.add(getItemById(result[1].split(",")[i].trim()));
            }

            return itemList;

        } catch (Exception e) {
            return getItemListByRandom(12);
        }
    }


    public ArrayList<Item> getItemListByChatGPT2(String cartItemList) {

        ArrayList<Item> itemList = new ArrayList<>();
        StringBuilder data = new StringBuilder("id,name\\n");
        try {
            db.query("select * from items;", rs -> { data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n" );});

            String prompt = "\\n위 데이터는 마트에 있는 상품들이야. 내 장바구니에는 현재 상품 id : '" + cartItemList + "'가 들어있어.\\n" +
                    "장바구니에 있는 상품들을 기반으로 추천상품 목록을 출력해줘.\\n" +
                    "추천상품 id 목록을 콤마로 구분해서 맨 뒤에 추가해줘. 적어도 5개 이상의 상품을 추천해줘.\\n" +
                    "답변은 추천상품 id 목록만 출력해줘. 예를들면, '1,2,3,4,5' 이와 같이 추가해줘.";

            String[] result = chatGPTService.getResponse(data.toString() + prompt).split("<split>");

            for (int i = 0; i < result[1].split(",").length; i++) {
                itemList.add(getItemById(result[1].split(",")[i].trim()));
            }

            return itemList;

        } catch (Exception e) {
            return getItemListByRandom(12);
        }
    }

    public ArrayList<Item> getItemListByChatGPT3(String keywords) {

        ArrayList<Item> itemList = new ArrayList<>();
        StringBuilder data = new StringBuilder("id,name\\n");
        try {
            db.query("select * from items;", rs -> { data.append(rs.getInt(1) + "," + rs.getString(3) + "\\n" );});

            String prompt = "\\n위 데이터는 마트에 있는 상품들이야. 마트에 있는 상품들을 기반으로 검색해줘.\\n" +
                    "검색어 : '" + keywords + "'\\n" +
                    "검색어에 필요한 모든 상품 목록의 상품 id를 검색해줘. 검색결과에 대해 자세하게 200자 이내로 설명해줘. 설명할때 상품 id는 빼줘.\\n" +
                    "검색결과의 모든 상품 id 목록을 콤마로 구분해서 맨 뒤에 추가해줘. 적어도 5개 이상의 상품을 추천해줘.\\n" +
                    "답변에 설명과 상품 id를 '<split>' 로 구분할수 있게 대답해줘. 예를들면, 답변 마지막에 '<split>1,2,3,4,5' 이와 같이 추가해줘.";

            String[] result = chatGPTService.getResponse(data.toString() + prompt).split("<split>");

            itemList.add(new Item(9999, result[0], null, 0, 0, null, null));

            for (int i = 0; i < result[1].split(",").length; i++) {
                itemList.add(getItemById(result[1].split(",")[i].trim()));
            }

            return itemList;

        } catch (Exception e) {
            return getItemListByRandom(12);
        }
    }

    public ArrayList<Item> getItemListByChatGPT4(String id) {
        ArrayList<Item> itemList = new ArrayList<>();
        String itemName = getItemById(id).getName();
        try {
            String prompt = "'" + itemName + "'을 구입했을때 활용방안을 자세하게 200자 이내로 설명해줘. 답변할때 내가 말한 상품의 단위는 빼줘.\\n" +
                    "'" + itemName + "'와 비슷하거나 잘 어울리는 상품을 찾기위한 관련된 검색어를 출력해줘. 검색어는 콤마로 구분해서 맨 마지막에 추가해줘.\\n" +
                    "답변에 활용방안과 검색어를 '<split>' 로 구분할수 있게 대답해줘. 예를들면, 답변 마지막에 '<split>검색어1,검색어2,검색어3,검색어4,검색어5' 이와 같이 추가해줘.\\n" +
                    "최대한 많은 검색어를 출력해줘.\\n";

            String[] result = chatGPTService.getResponse(prompt).split("<split>");

            itemList.add(new Item(9999, result[0] + result[1], null, 0, 0, null, null));

            String[] keywords = result[1].split(",");
            for (int i = 0; i < keywords.length; i++) {
                itemList.addAll(getItemListByName(keywords[i]));
            }

            return itemList;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
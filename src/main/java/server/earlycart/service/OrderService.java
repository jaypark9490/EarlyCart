package server.earlycart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import server.earlycart.model.Item;
import server.earlycart.model.Order;
import server.earlycart.model.OrderItem;

import java.sql.Statement;
import java.util.ArrayList;

@Service
public class OrderService {
    @Autowired
    JdbcTemplate db;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    public String newOrder(String session, String cartId, String itemList) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            String userId = userService.getUserBySession(session).getId();

            db.update(connection -> {
                var ps = connection.prepareStatement("insert into orders values (null, ?, ?, null, null, null, 1, now());", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, userId);
                ps.setInt(2, Integer.parseInt(cartId));
                return ps;
            }, keyHolder);

            Long orderId = keyHolder.getKey().longValue();

            newOrderItemList(orderId, itemList);



            return "1";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "0";
        }
    }

    public String newOrderItemList(Long orderId, String itemList) {
        try {

            String[] itemIdList = itemList.split("<split>")[0].split(",");
            String[] itemQuantityList = itemList.split("<split>")[1].split(",");
            int orderPrice = 0;

            for (int i = 0; i < itemIdList.length; i++) {
                Item item = itemService.getItemById(itemIdList[i]);
                int itemId = Integer.parseInt(itemIdList[i]);
                String itemName = item.getName();
                int itemQuantity = Integer.parseInt(itemQuantityList[i]);
                int itemPrice = item.getPrice();
                int totalPrice = itemQuantity * itemPrice;
                orderPrice += totalPrice;
                db.update("insert into order_items values (null, ?, ?, ?, ?, ?);", orderId, itemId, itemName, itemQuantity, totalPrice);
                if (i == 0) {
                    updateOrderName(orderId, itemName + " 외 " + (itemIdList.length - 1) + "개");
                    updateOrderImage(orderId, item.getImage());
                }
            }

            updateOrderPrice(orderId, orderPrice);

            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    public String updateOrderName(Long orderId, String orderName) {
        try {
            db.update("update orders set name = ? where id = ?;", orderName, orderId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    public String updateOrderPrice(Long orderId, int orderPrice) {
        try {
            db.update("update orders set price = ? where id = ?;", orderPrice, orderId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    public String updateOrderImage(Long orderId, String orderImage) {
        try {
            db.update("update orders set image = ? where id = ?;", orderImage, orderId);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    public Order getOrderById(String orderId) {
        try {
            Order order = db.queryForObject("select * from orders where id = ?;",
                    (rs, row) -> {
                        return new Order(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8));},
                    orderId);
            return order;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Order> getOrderListBySession(String session) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {

            String userId = userService.getUserBySession(session).getId();

            db.query("select * from orders where user_id = ?;",
                    rs -> { orderList.add(new Order(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8)));},
                    userId);
            return orderList;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<OrderItem> getOrderItemListByOrderId(String orderId) {
        ArrayList<OrderItem> orderList = new ArrayList<>();
        try {

            db.query("select * from order_items where order_id = ?;",
                    rs -> { orderList.add(new OrderItem(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));},
                    orderId);
            return orderList;
        } catch (Exception e) {
            return null;
        }
    }

}

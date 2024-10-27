package server.earlycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.earlycart.model.Item;
import server.earlycart.service.ItemService;

import java.util.ArrayList;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("item/{id}")
    public Item itemById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @GetMapping("item/category/{category}")
    public ArrayList<Item> itemListByCategory(@PathVariable String category) {
        return itemService.getItemListByCategory(category);
    }

    @GetMapping("item/search/{name}")
    public ArrayList<Item> itemListByName(@PathVariable String name) {
        return itemService.getItemListByName(name);
    }

    @GetMapping("item/random/{limit}")
    public ArrayList<Item> itemListByRandom(@PathVariable int limit) {
        return itemService.getItemListByRandom(limit);
    }

    @GetMapping("item/chatgpt/{id}")
    public ArrayList<Item> chatgpt(@PathVariable String id) {
        return itemService.getItemListTest(id);
    }

    @GetMapping("item/chatgpt2/{id}")
    public String chatgpt2(@PathVariable String id) {
        return itemService.getItemListTest2(id);
    }

    @GetMapping("item/chatgpt")
    public ArrayList<Item> chatgpt() {
        return itemService.getItemListByRandom(15);
        //return itemService.getItemListByChatGPT();
    }
}

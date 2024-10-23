package server.earlycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.earlycart.model.Item;
import server.earlycart.service.ChatGPTService;
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
    public ArrayList<Item> itemListByRandom(@PathVariable String limit) {
        return itemService.getItemListByRandom(limit);
    }

    @GetMapping("item/gpt/{id}")
    public String gpt(@PathVariable String id) {
        return itemService.getItemListByChatGPT(id);
    }
}

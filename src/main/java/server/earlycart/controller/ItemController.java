package server.earlycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.earlycart.model.Item;
import server.earlycart.service.ItemService;

import java.util.ArrayList;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("item/{id}")
    public Item getItemById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @GetMapping("item/barcode/{barcode}")
    public Item getItemByBarcode(@PathVariable String barcode) {
        return itemService.getItemByBarcode(barcode);
    }

    @GetMapping("item/category/{category}")
    public ArrayList<Item> getItemListByCategory(@PathVariable String category) {
        return itemService.getItemListByCategory(category);
    }

    @GetMapping("item/search/{name}")
    public ArrayList<Item> getItemListByName(@PathVariable String name) {
        return itemService.getItemListByName(name);
    }

    @GetMapping("item/random/{limit}")
    public ArrayList<Item> getItemListByRandom(@PathVariable int limit) {
        return itemService.getItemListByRandom(limit);
    }

    @GetMapping("item/chatgpt/{id}")
    public ArrayList<Item> getItemListByChatGPT1(@PathVariable String id) {
        return itemService.getItemListByChatGPT1(id);
    }

    @GetMapping("item/chatgpt/cart/{itemList}")
    public ArrayList<Item> getItemListByChatGPT2(@PathVariable String itemList) {
        return itemService.getItemListByChatGPT2(itemList);
    }

    @GetMapping("item/chatgpt/search/{keywords}")
    public ArrayList<Item> getItemListByChatGPT3(@PathVariable String keywords) {
        return itemService.getItemListByChatGPT3(keywords);
    }

    @GetMapping("item/chatgpt2/{id}")
    public ArrayList<Item> getItemListByChatGPT4(@PathVariable String id) {
        return itemService.getItemListByChatGPT4(id);
    }


}

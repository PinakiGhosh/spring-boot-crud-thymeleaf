package com.example.inventory.services

import com.example.inventory.exceptions.ItemExistsException
import com.example.inventory.models.ItemModel
import com.example.inventory.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {

    @Autowired
    ItemRepository itemRepository

    ItemModel save(ItemModel itemModel){
        ItemModel existingModel = itemRepository.findByItemName(itemModel.itemName)
        if(existingModel){
            throw new ItemExistsException("Item with name ${itemModel.itemName} already exists")
        }
        itemRepository.save(itemModel)
    }

    ItemModel getItem(Long itemId){
        itemRepository.findById(itemId).orElseGet(null)
    }

    void deleteItem(Long itemId){
        itemRepository.deleteById(itemId)
    }

    List<ItemModel> fetchAll(){
        return itemRepository.findAll() as List
    }
}

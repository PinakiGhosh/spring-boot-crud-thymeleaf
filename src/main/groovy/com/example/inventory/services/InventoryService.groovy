package com.example.inventory.services

import com.example.inventory.exceptions.InsufficientStockExcpetion
import com.example.inventory.models.InventoryModel
import com.example.inventory.repository.InventoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository

    InventoryModel addToInventory(Long itemId,int count){
        InventoryModel inventoryModel = inventoryRepository.findById(itemId).orElseGet(null)
        if(inventoryModel){
            inventoryModel.count+=count
        }else{
            inventoryModel = new InventoryModel()
            inventoryModel.itemId = itemId
            inventoryModel.count = count
        }
        return inventoryRepository.save(inventoryModel)
    }

    InventoryModel deleteFromInventory(Long itemId,int count){
        InventoryModel inventoryModel = inventoryRepository.findById(itemId).orElseGet(null)
        if(inventoryModel && inventoryModel.count > count){
            inventoryModel.count-=count
            inventoryRepository.save(inventoryModel)
        }else{
            throw new InsufficientStockExcpetion("ItemId ${itemId} doesnot have enough stock")
        }
    }
}

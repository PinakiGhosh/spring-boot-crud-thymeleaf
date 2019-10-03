package com.example.inventory.repository

import com.example.inventory.models.ItemModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository extends CrudRepository<ItemModel,Long>{

    ItemModel findByItemName(String itemName)
}

package com.example.inventory.repository

import com.example.inventory.models.InventoryModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository extends CrudRepository<InventoryModel,Long> {
}

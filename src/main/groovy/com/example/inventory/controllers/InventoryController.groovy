package com.example.inventory.controllers

import com.example.inventory.exceptions.InsufficientStockExcpetion
import com.example.inventory.exceptions.ItemExistsException
import com.example.inventory.models.ItemModel
import com.example.inventory.services.InventoryService
import com.example.inventory.services.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping('/')
class InventoryController {

    @Autowired
    ItemService itemService
    @Autowired
    InventoryService inventoryService

    /**
     * Check if the application is up
     * @return
     */
    @GetMapping('/ping')
    @ResponseBody Map ping(){
        return [success:true,time:new Date(),name:'Inventory']
    }

    @GetMapping('/add')
    String addItemPage(){
        'view'
    }

    /**
     * Add a new item.
     * If an item with same name already exits then return success false with error message
     * @param itemModel
     * @param modelMap
     * @return
     */
    @PostMapping('/add')
    String addItem(@ModelAttribute ItemModel itemModel,ModelMap modelMap){
        String message
        ItemModel savedModel
        boolean success = false
        try {
            savedModel = itemService.save(itemModel)
            message = "${itemModel.itemName} saved with id ${savedModel.itemId}"
            modelMap.addAttribute('itemModel',savedModel)
            success = true
        }catch (ItemExistsException iee){
            message = iee.message
            modelMap.addAttribute('itemModel',itemService.getItem(itemModel.itemName))
        }
        modelMap.addAttribute('message',message)
        modelMap.addAttribute('success',success)
        'view'
    }

    /**
     * Delete an item based on Id
     * Throw 404 exception if not found
     * @param itemId
     * @return
     */
    @DeleteMapping('/deleteItem/{itemId}')
    String deleteItem(@PathVariable('itemId') Long itemId){
        if(itemService.getItem(itemId)){
            itemService.deleteItem(itemId)
            'viewAll'
        }else{
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * List all the items
     * @param modelMap
     * @return
     */
    @GetMapping('/list')
    String showAll(ModelMap modelMap){
        modelMap.addAttribute('itemList',itemService.fetchAll())
        'viewAll'
    }

    /**
     * View and item detail
     * @param itemId
     * @param modelMap
     * @return
     */
    @GetMapping('/view/{id}')
    String viewItem(@PathVariable('id') Long itemId,ModelMap modelMap){
        modelMap.addAttribute('itemModel',itemService.getItem(itemId))
        'view'
    }

    /**
     * Add stock for an item.
     * Throw 404 if item is not found
     * @param itemId
     * @param count
     * @return
     */
    @PutMapping('/addStock')
    @ResponseBody addStock(@RequestParam('itemId') Long itemId,@RequestParam('count')Integer count){
        boolean success = false
        if(itemService.getItem(itemId)){
            success = inventoryService.addToInventory(itemId,count) as Boolean
        }else{
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        return success
    }

    /**
     * Delete stock for an item.
     * Throw 404 if item is not present
     * Return error message if count to delete > current count
     * @param itemId
     * @param count
     * @return
     */
    @PutMapping('/deleteStock')
    @ResponseBody deleteStock(@RequestParam('itemId') Long itemId,@RequestParam('count')Integer count){
        String message
        boolean success = false
        if(itemService.getItem(itemId)){

            try {
                success = inventoryService.deleteFromInventory(itemId,count) as Boolean
                message = 'Stock deleted successfully'
            }catch (InsufficientStockExcpetion ise){
                message = ise.message
            }
            return [success: success,message : message]
        }else{
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

}

package com.example.inventory.models

import org.hibernate.annotations.CreationTimestamp

import javax.persistence.*

@Entity
@Table(name = "item")
class ItemModel {
    private Long itemId
    private String itemName
    private String description
    private Date addedOn
    private InventoryModel inventoryByItemId

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    Long getItemId() {
        return itemId
    }

    void setItemId(Long itemId) {
        this.itemId = itemId
    }

    @Basic
    @Column(name = "item_name", nullable = false, length = 256)
    String getItemName() {
        return itemName
    }

    void setItemName(String itemName) {
        this.itemName = itemName
    }

    @Basic
    @Column(name = "description", nullable = false, length = 512)
    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_on", nullable = false)
    Date getAddedOn() {
        return addedOn
    }

    void setAddedOn(Date addedOn) {
        this.addedOn = addedOn
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        ItemModel itemModel = (ItemModel) o

        if (itemId != null ? !itemId.equals(itemModel.itemId) : itemModel.itemId != null) return false
        if (itemName != null ? !itemName.equals(itemModel.itemName) : itemModel.itemName != null) return false
        if (description != null ? !description.equals(itemModel.description) : itemModel.description != null)
            return false
        if (addedOn != null ? !addedOn.equals(itemModel.addedOn) : itemModel.addedOn != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (addedOn != null ? addedOn.hashCode() : 0)
        return result
    }

    @OneToOne(mappedBy = "itemByItemId")
    InventoryModel getInventoryByItemId() {
        return inventoryByItemId
    }

    void setInventoryByItemId(InventoryModel inventoryByItemId) {
        this.inventoryByItemId = inventoryByItemId
    }
}

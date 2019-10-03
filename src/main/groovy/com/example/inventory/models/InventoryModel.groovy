package com.example.inventory.models

import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity
@Table(name = "inventory")
class InventoryModel {
    private Long itemId
    private Integer count
    private Date updatedOn
    private ItemModel itemByItemId

    @Id
    @Column(name = "item_id", nullable = false)
    Long getItemId() {
        return itemId
    }

    void setItemId(Long itemId) {
        this.itemId = itemId
    }

    @Basic
    @Column(name = "count", nullable = true)
    Integer getCount() {
        return count
    }

    void setCount(Integer count) {
        this.count = count
    }

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    Date getUpdatedOn() {
        return updatedOn
    }

    void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        InventoryModel that = (InventoryModel) o

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false
        if (count != null ? !count.equals(that.count) : that.count != null) return false
        if (updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0
        result = 31 * result + (count != null ? count.hashCode() : 0)
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0)
        return result
    }

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false,insertable = false, updatable = false)
    ItemModel getItemByItemId() {
        return itemByItemId
    }

    void setItemByItemId(ItemModel itemByItemId) {
        this.itemByItemId = itemByItemId
    }
}

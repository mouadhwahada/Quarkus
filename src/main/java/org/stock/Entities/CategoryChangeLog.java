package org.stock.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class CategoryChangeLog extends PanacheEntity {
    private Long categoryId;
    private String changeType; // e.g., "State Changed", "Quantity Updated"
    private String oldValue;
    private String newValue;
    private LocalDateTime changeDate;

    public CategoryChangeLog() {

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        categoryId = categoryId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public CategoryChangeLog(Long categoryId, String changeType, String oldValue, String newValue, LocalDateTime changeDate) {
        this.categoryId = categoryId;
        this.changeType = changeType;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeDate = changeDate;
    }
}

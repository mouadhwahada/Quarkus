package org.stock.Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class PieceChangeLog extends PanacheEntity {


    private Long pieceId;
    private String changeType; // e.g., "State Changed", "Quantity Updated"
    private String oldValue;
    private String newValue;
    private LocalDateTime changeDate;

    public PieceChangeLog(Long pieceId, String changeType, String oldValue, String newValue, LocalDateTime changeDate) {
        this.pieceId = pieceId;
        this.changeType = changeType;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeDate = changeDate;
    }

    public PieceChangeLog() {

    }

    public Long getPieceId() {
        return pieceId;
    }

    public void setPieceId(Long pieceId) {
        this.pieceId = pieceId;
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
}

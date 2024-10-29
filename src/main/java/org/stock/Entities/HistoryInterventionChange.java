package org.stock.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class HistoryInterventionChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    //@JoinColumn(name = "piece_id",referencedColumnName = "piece_id")
    private Piece piece;

    private String oldClientCin;
    private String newClientCin;

    private String oldTechnician;
    private String newTechnician;

    private String oldObservation;
    private String newObservation;

    private LocalDateTime changeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String getOldClientCin() {
        return oldClientCin;
    }

    public void setOldClientCin(String oldClientCin) {
        this.oldClientCin = oldClientCin;
    }

    public String getNewClientCin() {
        return newClientCin;
    }

    public void setNewClientCin(String newClientCin) {
        this.newClientCin = newClientCin;
    }

    public String getOldTechnician() {
        return oldTechnician;
    }

    public void setOldTechnician(String oldTechnician) {
        this.oldTechnician = oldTechnician;
    }

    public String getNewTechnician() {
        return newTechnician;
    }

    public void setNewTechnician(String newTechnician) {
        this.newTechnician = newTechnician;
    }

    public String getOldObservation() {
        return oldObservation;
    }

    public void setOldObservation(String oldObservation) {
        this.oldObservation = oldObservation;
    }

    public String getNewObservation() {
        return newObservation;
    }

    public void setNewObservation(String newObservation) {
        this.newObservation = newObservation;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    // Getters and Setters
}

package org.stock.Entities;

import org.stock.Entities.Etat;
import org.stock.Entities.Observation;

import java.time.LocalDateTime;

public class UpdateHistoryTreeRequest {
    private String emplacementSource;
    private LocalDateTime reperationDate;
    private Etat status;
    private Observation observation;
    private String clientCin;
    private Integer purchaseNumber;
    private String emplacementDestination;



    // Getters and Setters


    public String getEmplacementDestination() {
        return emplacementDestination;
    }

    public void setEmplacementDestination(String emplacementDestination) {
        this.emplacementDestination = emplacementDestination;
    }

    public String getEmplacementSource() {
        return emplacementSource;
    }

    public void setEmplacementSource(String emplacementSource) {
        this.emplacementSource = emplacementSource;
    }

    public LocalDateTime getReperationDate() {
        return reperationDate;
    }

    public void setReperationDate(LocalDateTime reperationDate) {
        this.reperationDate = reperationDate;
    }

    public Etat getStatus() {
        return status;
    }

    public void setStatus(Etat status) {
        this.status = status;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}

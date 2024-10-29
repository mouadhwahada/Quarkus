package org.stock.dto;

import org.stock.Entities.Etat;
import org.stock.Entities.Observation;

import java.time.LocalDateTime;

public class HistoryTreeUpdateRequest {
    private String emplacementSource;
    private LocalDateTime reperationDate;
    private String emplacementDestination;
    private Etat status;
    private Observation observation;

    // Getters et Setters

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

    public String getEmplacementDestination() {
        return emplacementDestination;
    }

    public void setEmplacementDestination(String emplacementDestination) {
        this.emplacementDestination = emplacementDestination;
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
}

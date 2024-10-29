package org.stock.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.stock.Entities.Etat;
import org.stock.Entities.Observation;
import org.stock.Entities.Piece;

import java.time.LocalDateTime;

public class HistoryTreeDto {
    private Long pieceId;


    private String identifiantAntecedent;

    private Etat status;

    private String EmplacementSource;
    private LocalDateTime Reperation_date;
    private String Technician;

    private Observation observation;

    private String emplacementDestination;

    public Long getPieceId() {
        return pieceId;
    }

    public void setPieceId(Long pieceId) {
        this.pieceId = pieceId;
    }

    public String getIdentifiantAntecedent() {
        return identifiantAntecedent;
    }

    public void setIdentifiantAntecedent(String identifiantAntecedent) {
        this.identifiantAntecedent = identifiantAntecedent;
    }

    public Etat getStatus() {
        return status;
    }

    public void setStatus(Etat status) {
        this.status = status;
    }

    public String getEmplacementSource() {
        return EmplacementSource;
    }

    public void setEmplacementSource(String emplacementSource) {
        EmplacementSource = emplacementSource;
    }

    public LocalDateTime getReperation_date() {
        return Reperation_date;
    }

    public void setReperation_date(LocalDateTime reperation_date) {
        Reperation_date = reperation_date;
    }

    public String getTechnician() {
        return Technician;
    }

    public void setTechnician(String Technician) {
        Technician = Technician;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public String getEmplacementDestination() {
        return emplacementDestination;
    }

    public void setEmplacementDestination(String emplacementDestination) {
        this.emplacementDestination = emplacementDestination;
    }
}

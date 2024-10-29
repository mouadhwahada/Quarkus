package org.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.smallrye.common.constraint.NotNull;
import org.stock.Entities.NatureIntervention;
import org.stock.Entities.Result;

import java.time.LocalDateTime;

public class InterventionHistorydto {

    private Long pieceId;

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    private Integer purchaseNumber;

    @NotNull
    private String clientCin; // Ajouté ici
    private String client;
    private LocalDateTime interventionDate;
    private NatureIntervention interventionNature;
    private String technician;
    private Result result;
    private String observation; // Changement ici pour correspondre à l'entité

    private String namePiece;

    public String getNamePiece() {
        return namePiece;
    }

    public void setNamePiece(String namePiece) {
        this.namePiece = namePiece;
    }
    // Getters and Setters

    public Long getPieceId() {
        return pieceId;
    }

    public void setPieceId(Long pieceId) {
        this.pieceId = pieceId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDateTime getInterventionDate() {
        return interventionDate;
    }

    public void setInterventionDate(LocalDateTime interventionDate) {
        this.interventionDate = interventionDate;
    }

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }
    public NatureIntervention getInterventionNature() {
        return interventionNature;
    }

    public void setInterventionNature(NatureIntervention interventionNature) {
        this.interventionNature = interventionNature;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getObservation() {
        return observation; // Changement ici
    }

    public void setObservation(String observation) {
        this.observation = observation; // Changement ici
    }

    public InterventionHistorydto(Long pieceId, Integer purchaseNumber, String clientCin, String client, LocalDateTime interventionDate, NatureIntervention interventionNature, String technician, Result result, String observation, String namePiece) {
        this.pieceId = pieceId;
        this.purchaseNumber = purchaseNumber;
        this.clientCin = clientCin;
        this.client = client;
        this.interventionDate = interventionDate;
        this.interventionNature = interventionNature;
        this.technician = technician;
        this.result = result;
        this.observation = observation;
        this.namePiece = namePiece;
    }
}

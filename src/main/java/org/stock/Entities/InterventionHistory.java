package org.stock.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "intervention_history")
public class InterventionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "piece_id")
    @JsonBackReference
    private Piece piece;

    private String client;
    private String clientCin;
    private LocalDateTime interventionDate;

    @Enumerated(EnumType.STRING)
    private NatureIntervention interventionNature;

    private String technician;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String observation;

    private Integer purchaseNumber;




    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "clientCin", referencedColumnName = "clientCin", insertable = false, updatable = false),
            @JoinColumn(name = "purchaseNumber", referencedColumnName = "purchaseNumber", insertable = false, updatable = false)
    })
    private PieceSale pieceSale; // New relation to PieceSale

    @ManyToOne
    @JoinColumn(name = "last_intervention_id")
    private InterventionHistory firstIntervention;

    public InterventionHistory getFirstIntervention() {
        return firstIntervention;
    }

    public void setFirstIntervention(InterventionHistory firstIntervention) {
        this.firstIntervention = firstIntervention;
    }

    private String pieceName;

    // Getters and Setters

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
        if (piece != null) {
            this.pieceName = piece.getNamePiece();
        }
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }

    public LocalDateTime getInterventionDate() {
        return interventionDate;
    }

    public void setInterventionDate(LocalDateTime interventionDate) {
        this.interventionDate = interventionDate;
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
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public PieceSale getPieceSale() {
        return pieceSale;
    }

    public void setPieceSale(PieceSale pieceSale) {
        this.pieceSale = pieceSale;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }
}

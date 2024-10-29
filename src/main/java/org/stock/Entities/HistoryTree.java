package org.stock.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tree_History")
public class HistoryTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "piece_id")
    private Piece piece;

    private String identifiantAntecedent;

    @Enumerated(EnumType.STRING)
    private Etat status;

    private String emplacementSource;
    private LocalDateTime reperationDate;
    private String technician;

    @Enumerated(EnumType.STRING)
    private Observation observation;

    private String emplacementDestination;

    private String clientCin;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "client_cin", referencedColumnName = "clientCin"),
            @JoinColumn(name = "purchase_number", referencedColumnName = "purchaseNumber")
    })
    private PieceSale pieceSale; // Relation to PieceSale

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

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
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

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }

    public PieceSale getPieceSale() {
        return pieceSale;
    }

    public void setPieceSale(PieceSale pieceSale) {
        this.pieceSale = pieceSale;
    }
}

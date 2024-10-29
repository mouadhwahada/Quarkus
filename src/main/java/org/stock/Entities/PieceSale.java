package org.stock.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Piece_Sale")
@IdClass(PieceSaleId.class) // Indique une clé composite
public class PieceSale {

    @Id
    private String clientCin;

    @Id
    private Integer purchaseNumber;

    @Enumerated(EnumType.STRING)
    private PieceState pieceState;

    public PieceState getPieceState() {
        return pieceState;
    }

    public void setPieceState(PieceState pieceState) {
        this.pieceState = pieceState;
    }

    @ManyToOne
    @JoinColumn(name = "piece_id")
    @JsonBackReference
    private Piece piece;


    @OneToMany(mappedBy = "pieceSale")
    private List<InterventionHistory> interventionHistories; // Relation avec InterventionHistory



    @Column(name = "piece_name")
    private String pieceName;

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    private LocalDateTime saleDate;
    private Integer quantitySold;
    private String antecedentId;

    private Integer purchaseCount;  // Nouveau champ pour le nombre total d'achats

    @OneToMany(mappedBy = "pieceSale")
    @JsonIgnore

    private List<HistoryTree> historyTrees;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void updatePieceName() {
        if (piece != null) {
            this.pieceName = piece.getNamePiece();
        }
    }


    // Getters and Setters


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

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getAntecedentId() {
        return antecedentId;
    }

    public void setAntecedentId(String antecedentId) {
        this.antecedentId = antecedentId;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public List<HistoryTree> getHistoryTrees() {
        return historyTrees;
    }

    public void setHistoryTrees(List<HistoryTree> historyTrees) {
        this.historyTrees = historyTrees;
    }
}

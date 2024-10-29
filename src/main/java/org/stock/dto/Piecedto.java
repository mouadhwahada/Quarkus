package org.stock.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.stock.Entities.PieceState;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Piecedto {




    private Long id;
    @NotNull
    private String namePiece;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    public PieceState pieceState;


    private Integer quantity;

    private LocalDateTime dateAdded;
    private Integer price;

    @NotNull
    private String supplier;

    private String antecedentId; // Ajouté pour stocker l'identifiant d'antécédent

    public Piecedto(String namePiece) {
        this.namePiece = namePiece;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private Long categoryId;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNamePiece() {
        return namePiece;
    }

    public void setNamePiece(String namePiece) {
        this.namePiece = namePiece;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PieceState getPieceState() {
        return pieceState;
    }

    public void setPieceState(PieceState pieceState) {
        this.pieceState = pieceState;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAntecedentId() {
        return antecedentId;
    }

    public void setAntecedentId(String antecedentId) {
        this.antecedentId = antecedentId;
    }

    public Piecedto(String namePiece, String description, PieceState pieceState, Integer quantity, LocalDateTime dateAdded, Integer price, String supplier, String antecedentId, Long categoryId, String categoryName) {
        this.namePiece = namePiece;
        this.description = description;
        this.pieceState = pieceState;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.price = price;
        this.supplier = supplier;
        this.antecedentId = antecedentId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
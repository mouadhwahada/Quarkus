package org.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.stock.Entities.Category;
import org.stock.Entities.PieceState;
import org.wildfly.common.annotation.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Piecedto {




    private Long id;
    @NotNull
    private String NamePiece;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    public PieceState pieceState;


    private Integer quantity;

    private LocalDateTime dateAdded;
    private Integer price;

    @NotNull
    private String supplier;


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
        return NamePiece;
    }

    public void setNamePiece(String namePiece) {
        NamePiece = namePiece;
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
}
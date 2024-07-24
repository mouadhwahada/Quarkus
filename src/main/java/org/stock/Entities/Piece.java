package org.stock.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.stock.dto.Piecedto;
import org.wildfly.common.annotation.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "piece")
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long category_id;
    private String NamePiece;
    private String description;
    @Enumerated(EnumType.STRING)
    public PieceState pieceState;
    private Integer quantity;
    private LocalDateTime dateAdded;
    private Integer price;
    private String supplier ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id" , insertable = false, updatable = false)
    private Category categ;

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Piece() {

    }

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

    public Category getCateg() {
        return categ;
    }

    public void setCateg(Category categ) {
        this.categ = categ;
    }

    public Piece(String namePiece, String description, PieceState pieceState, Integer quantity, LocalDateTime dateAdded, Integer price, String supplier, Category categ) {
        NamePiece = namePiece;
        this.description = description;
        this.pieceState = pieceState;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.price = price;
        this.supplier = supplier;
        this.categ = categ;
    }

    public Piecedto getPieceDto(){
        Piecedto piecedto = new Piecedto();
        piecedto.setId(id);
        piecedto.setNamePiece(NamePiece);
        piecedto.setPrice(price);
        piecedto.setDescription(description);
        piecedto.setPieceState(pieceState);
        piecedto.setQuantity(quantity);
        piecedto.setSupplier(supplier);
        piecedto.setCategoryName(categ.getName());
        piecedto.setCategoryId(categ.getId());
        return piecedto;

    }
}

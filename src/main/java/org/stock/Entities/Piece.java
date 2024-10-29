package org.stock.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.stock.dto.Piecedto;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Piece")
@JsonIgnoreProperties({"interventionHistories"})

public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namePiece;
    private String description;

    @Enumerated(EnumType.STRING)
    private PieceState pieceState;

    private Integer quantity;
    private LocalDateTime dateAdded;
    private Integer price;
    private String supplier;

    @OneToMany(mappedBy = "piece")
    @JsonManagedReference
    private Set<InterventionHistory> interventionHistories;

    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PieceSale> pieceSales;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category categ;

   // private String antecedentId;

    public Piece() {

    }

    // Getters and Setters

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

    public Category getCateg() {
        return categ;
    }

    public void setCateg(Category categ) {
        this.categ = categ;
    }

    public Set<InterventionHistory> getInterventionHistories() {
        return interventionHistories;
    }

    public void setInterventionHistories(Set<InterventionHistory> interventionHistories) {
        this.interventionHistories = interventionHistories;
    }

    public Set<PieceSale> getPieceSales() {
        return pieceSales;
    }

    public void setPieceSales(Set<PieceSale> pieceSales) {
        this.pieceSales = pieceSales;
    }

  /*  public String getAntecedentId() {
        return antecedentId;
    }

    public void setAntecedentId(String antecedentId) {
        this.antecedentId = antecedentId;
    }*/

    public Piece(String namePiece, String description, PieceState pieceState, Integer quantity, LocalDateTime dateAdded, Integer price, String supplier, Category categ, String antecedentId) {
        this.namePiece = namePiece;
        this.description = description;
        this.pieceState = pieceState;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.price = price;
        this.supplier = supplier;
        this.categ = categ;
     //   this.antecedentId = antecedentId;
    }

    public Piecedto getPieceDto() {
        Piecedto piecedto = new Piecedto(namePiece);
        piecedto.setId(this.id);
        piecedto.setNamePiece(this.namePiece);
        piecedto.setDescription(this.description);
        piecedto.setPieceState(this.pieceState);
        piecedto.setQuantity(this.quantity);
        piecedto.setDateAdded(this.dateAdded);
        piecedto.setPrice(this.price);
        piecedto.setSupplier(this.supplier);
   //     piecedto.setAntecedentId(this.antecedentId);

        if (this.categ != null) {
            piecedto.setCategoryId(this.getCateg().getId());
            piecedto.setCategoryName(this.getCateg().getName());
        }

        return piecedto;
    }
}

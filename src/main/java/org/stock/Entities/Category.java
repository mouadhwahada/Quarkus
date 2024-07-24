package org.stock.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.wildfly.common.annotation.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="category")
public class Category  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @NotNull
    private String name;

    @Lob
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "categ", fetch = FetchType.EAGER)
    private Set<Piece> pieces = new HashSet<>();


    public Long getId() {
        return category_id;
    }

    public void setId(Long id) {
        this.category_id = id;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(Set<Piece> pieces) {
        this.pieces = pieces;
    }
}


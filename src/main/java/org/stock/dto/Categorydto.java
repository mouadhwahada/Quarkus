package org.stock.dto;

import jakarta.persistence.OneToMany;
import org.stock.Entities.Category;
import org.stock.Entities.Piece;

import java.util.List;

public class Categorydto {

    private Long id;
    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}



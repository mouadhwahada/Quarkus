package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.stock.Entities.Piece;
import org.stock.dto.Piecedto;

import java.util.List;

@ApplicationScoped
public class PieceRepository implements PanacheRepository<Piece> {
    public List<Piece> findByNamePieceContaining(String NamePiece) {
        return find("NamePiece like ?1", "%" + NamePiece + "%").list();
    }


}
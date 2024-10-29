package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.stock.Entities.Piece;
import org.stock.Entities.PieceChangeLog;

import java.util.List;

@ApplicationScoped
public class PieceRepository implements PanacheRepository<Piece> {

    @PersistenceContext
    EntityManager em;



    public List<Piece> findByNamePieceContaining(String namePiece) {
        return find("namePiece like ?1", "%" + namePiece + "%").list();
    }

    public Piece findByName(String pieceName) {
        List<Piece> results = em.createQuery("SELECT p FROM Piece p WHERE p.namePiece = :namePiece", Piece.class)
                .setParameter("namePiece", pieceName)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }


    // New method to find a Piece by clientCin and purchaseNumber
    public Piece findByClientCinAndPurchaseNumber(String clientCin, Integer purchaseNumber) {
        List<Piece> results = em.createQuery("SELECT p FROM Piece p JOIN p.pieceSales ps WHERE ps.clientCin = :clientCin AND ps.purchaseNumber = :purchaseNumber", Piece.class)
                .setParameter("clientCin", clientCin)
                .setParameter("purchaseNumber", purchaseNumber)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Piece> findByPieceState(String pieceState) {
        return find("pieceState", pieceState).list();
    }
}

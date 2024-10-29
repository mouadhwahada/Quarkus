package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.stock.Entities.PieceSale;
import org.stock.Entities.PieceState;

import java.util.List;

@ApplicationScoped
public class PieceSaleRepository implements PanacheRepository<PieceSale> {

    @Inject
    EntityManager em;

    public List<PieceSale> findByPieceId(Long pieceId) {
        return list("piece_id", pieceId);
    }

    public List<PieceSale> findByClientCin(String clientCin) {
        return list("clientCin", clientCin);
    }

    public List<PieceSale> findByPurchaseNumber(Integer purchaseNumber) {
        return em.createQuery("SELECT ps FROM PieceSale ps WHERE ps.purchaseNumber = :purchaseNumber", PieceSale.class)
                .setParameter("purchaseNumber", purchaseNumber)
                .getResultList();
    }

    public List<PieceSale> findByClientCinAndPurchaseNumber(String clientCin, Integer purchaseNumber) {
        return em.createQuery("SELECT ps FROM PieceSale ps WHERE ps.clientCin = :clientCin AND ps.purchaseNumber = :purchaseNumber", PieceSale.class)
                .setParameter("clientCin", clientCin)
                .setParameter("purchaseNumber", purchaseNumber)
                .getResultList();
    }

    public PieceSale findPieceSaleByClientCinAndPurchaseNumber(String clientCin, Integer purchaseNumber) {
        return find("clientCin = ?1 and purchaseNumber = ?2", clientCin, purchaseNumber).firstResult();
    }

    public List<PieceSale> findAllSoldPieces() {
        return list("pieceState", PieceState.SOLD);
    }

    public List<PieceSale> findByClientCinList(String clientCin) {
        return find("clientCin", clientCin).list();
    }
}

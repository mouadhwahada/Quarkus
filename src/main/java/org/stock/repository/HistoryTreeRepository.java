package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stock.Entities.HistoryTree;
import org.stock.Entities.PieceSale;

import java.util.List;

@ApplicationScoped
public class HistoryTreeRepository implements PanacheRepository<HistoryTree> {

    public HistoryTree findByClientCinAndPurchaseNumber(String clientCin, Integer purchaseNumber) {
        return find("pieceSale.clientCin = ?1 and pieceSale.purchaseNumber = ?2", clientCin, purchaseNumber).firstResult();
    }

    @PersistenceContext
    private EntityManager em;

    public HistoryTree findLastHistoryTreeByPieceSale(PieceSale pieceSale) {
        List<HistoryTree> historyTrees = em.createQuery("SELECT ht FROM HistoryTree ht WHERE ht.pieceSale = :pieceSale ORDER BY ht.reperationDate DESC", HistoryTree.class)
                .setParameter("pieceSale", pieceSale)
                .setMaxResults(1)
                .getResultList();
        return historyTrees.isEmpty() ? null : historyTrees.get(0);
    }


    public EntityManager getEntityManager() {
        return em;
    }
}

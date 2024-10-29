package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.stock.Entities.InterventionHistory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InterventionHistoryRepository implements PanacheRepository<InterventionHistory> {

    public List<InterventionHistory> findByClientCinAndPieceName(String clientCin, String pieceName) {
        return list("clientCin = ?1 and pieceName = ?2", clientCin, pieceName);
    }

    public List<InterventionHistory> findByClientCin(String clientCin) {
        return list("clientCin", clientCin);
    }

    public List<InterventionHistory> findByPieceId(Long pieceId) {
        return list("piece.id", pieceId);
    }
    @Inject
    EntityManager em;

    public Optional<InterventionHistory> findLatestByPieceId(Long pieceId) {
        TypedQuery<InterventionHistory> query = em.createQuery(
                "SELECT i FROM InterventionHistory i WHERE i.piece.id = :pieceId ORDER BY i.interventionDate DESC",
                InterventionHistory.class
        );
        query.setParameter("pieceId", pieceId);
        query.setMaxResults(1);
        return query.getResultList().stream().findFirst();
    }
}

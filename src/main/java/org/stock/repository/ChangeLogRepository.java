package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.stock.Entities.PieceChangeLog;

import java.util.List;

@ApplicationScoped
public class ChangeLogRepository implements PanacheRepository<PieceChangeLog> {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<PieceChangeLog> findByPieceId(Long pieceId) {
        return em.createQuery("SELECT l FROM PieceChangeLog l WHERE l.pieceId = :pieceId", PieceChangeLog.class)
                .setParameter("pieceId", pieceId)
                .getResultList();
    }

}

package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.stock.Entities.HistoryInterventionChange;

import java.util.List;

@ApplicationScoped
public class HistoryInterventionChangeRepository implements PanacheRepository<HistoryInterventionChange> {


    public List<HistoryInterventionChange> findByClientCin(String clientCin) {
        return list("clientCin", clientCin); // Assurez-vous que le nom de l'attribut est correct
    }

    public List<HistoryInterventionChange> findByClientCinAndPurchaseNumberAndPieceName(String clientCin, Integer purchaseNumber, String pieceName) {
        // Remplacez les noms des attributs dans la requête par les noms réels de votre entité
        return list("clientCin = ?1 and purchaseNumber = ?2 and pieceName = ?3", clientCin, purchaseNumber, pieceName);
    }

    public List<HistoryInterventionChange> findByCriteria(String clientCin, Integer purchaseNumber, String pieceName) {
        return list("clientCin = ?1 and purchaseNumber = ?2 and pieceName = ?3", clientCin, purchaseNumber, pieceName);
    }




}

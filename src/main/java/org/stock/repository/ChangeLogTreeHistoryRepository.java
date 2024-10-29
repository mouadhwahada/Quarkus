package org.stock.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.stock.Entities.ChangeLogTreeHistory;

import java.util.List;

public class ChangeLogTreeHistoryRepository implements PanacheRepositoryBase<ChangeLogTreeHistory,Long> {

    public List<ChangeLogTreeHistory> findByClientCinAndPurchaseNumber(String clientCin, Integer purchaseNumber) {
        return list("clientCin = ?1 and purchaseNumber = ?2", clientCin, purchaseNumber);
    }
}

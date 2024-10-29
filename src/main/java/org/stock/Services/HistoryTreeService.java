package org.stock.Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.stock.Entities.Etat;
import org.stock.Entities.HistoryTree;
import org.stock.Entities.Observation;
import org.stock.Entities.PieceSale;
import org.stock.repository.HistoryTreeRepository;
import org.stock.repository.PieceSaleRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class HistoryTreeService {

    @Inject
    HistoryTreeRepository historyTreeRepository;

    @Inject
    PieceSaleRepository pieceSaleRepository;


    //bel antecedent id bch naaml l'update , il suffit que je prends l 'antecedndet id , il apporte les autres donnes w je fais le changement , najm nchoufha b l path
    @Transactional
    public HistoryTree updateHistoryTreeDetails(String clientCin, Integer purchaseNumber, String emplacementSource, LocalDateTime reperationDate, String emplacementDestination,
                                                Etat status, Observation observation) {
        //  Trouver PieceSale entity by clientCin and purchaseNumber
        PieceSale pieceSale = pieceSaleRepository.findPieceSaleByClientCinAndPurchaseNumber(clientCin, purchaseNumber);
        if (pieceSale == null) {
            throw new RuntimeException("PieceSale not found");
        }

        //LastHistorytree
        HistoryTree historyTree = historyTreeRepository.findLastHistoryTreeByPieceSale(pieceSale);
        if (historyTree == null) {
            throw new RuntimeException("No HistoryTree found for the given PieceSale");
        }

        //Modifier Details
        historyTree.setEmplacementDestination(emplacementDestination);
        historyTree.setEmplacementSource(emplacementSource);
        historyTree.setReperationDate(LocalDateTime.now());
        historyTree.setStatus(status);
        historyTree.setObservation(observation);
        historyTree.setPieceSale(pieceSale);

//Mergy w baad return l updated
 return historyTreeRepository.getEntityManager().merge(historyTree);
    }

    @Transactional
    public HistoryTree createHistory(HistoryTree historyTree) {
        historyTreeRepository.persist(historyTree);
        return historyTree;
    }

    @Transactional
    public List<HistoryTree> getAllHistoryTrees() {
        return historyTreeRepository.listAll();
    }

    public HistoryTree getHistoryTreeDetails(String clientCin, Integer purchaseNumber) {
        return null;
    }
}



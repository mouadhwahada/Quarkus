package org.stock.Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.stock.Entities.Piece;
import org.stock.Entities.PieceSale;
import org.stock.Entities.PieceState;
import org.stock.repository.PieceRepository;
import org.stock.repository.PieceSaleRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PieceSaleService {

    @Inject
    PieceRepository pieceRepository;

    @Inject
    PieceSaleRepository pieceSaleRepository;

    @Transactional // Assurez-vous que la méthode est transactionnelle
    public void sellPiece(String pieceName, Integer quantitySold, String clientCin) throws IllegalAccessException {
        Piece piece = pieceRepository.findByName(pieceName); // ou utiliser l'ID
        if (piece == null) {
            throw new IllegalAccessException("Piece not found");
        }
        if (piece.getQuantity() < quantitySold) {
            throw new IllegalArgumentException("Not enough stock available for Piece: " + pieceName);
        }

        // Vérifiez si une vente existe déjà pour ce client et cette pièce
        Integer purchaseNumber = getNextPurchaseNumber(clientCin);
        PieceSale existingSale = pieceSaleRepository.findPieceSaleByClientCinAndPurchaseNumber(clientCin, purchaseNumber);

        // Créer l'entrée de vente
        PieceSale sale = existingSale != null ? existingSale : new PieceSale();
        sale.setPiece(piece);
        sale.setClientCin(clientCin);
        sale.setSaleDate(LocalDateTime.now());
        sale.setQuantitySold(quantitySold);
        sale.setPieceState(PieceState.SOLD);
        sale.setPurchaseNumber(purchaseNumber);

        // Persister l'entrée de vente
        pieceSaleRepository.persist(sale);

        // MAJ quantité en stock
        piece.setQuantity(piece.getQuantity() - quantitySold);
        pieceRepository.persist(piece); // Assurez-vous que les modifications sont persistées

        // MAJ nombre d'achats du client
        updatePurchaseCount(clientCin);
    }

    private Integer getNextPurchaseNumber(String clientCin) {
        List<PieceSale> sales = pieceSaleRepository.findByClientCin(clientCin);
        return sales.size() + 1; // Le prochain numéro d'achat
    }

    @Transactional
    public void updatePurchaseCount(String clientCin) {
        List<PieceSale> sales = pieceSaleRepository.findByClientCin(clientCin);

        // Calculer le nombre total d'achats
        int purchaseCount = sales.size();

        // Mettre à jour le nombre d'achats pour chaque enregistrement
        for (PieceSale sale : sales) {
            sale.setPurchaseCount(purchaseCount);
            pieceSaleRepository.persist(sale);
        }
    }

    public List<PieceSale> findSalesByClientCin(String clientCin) {
        return pieceSaleRepository.findByClientCin(clientCin);
    }

    public List<PieceSale> getAllSoldPieces() {
        return pieceSaleRepository.findAllSoldPieces();
    }
}

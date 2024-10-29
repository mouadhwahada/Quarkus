package org.stock.Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.stock.Entities.*;
import org.stock.dto.Piecedto;
import org.stock.repository.CategoryRepository;
import org.stock.repository.ChangeLogRepository;
import org.stock.repository.PieceRepository;
import org.stock.repository.PieceSaleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PieceService {

    @Inject
    PieceRepository pieceRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    PieceSaleRepository pieceSaleRepository;

    @Inject
    ChangeLogRepository changeLogRepository;
    @Transactional
    public List<Piece> listPieces() {
        return pieceRepository.listAll();
    }

    @Transactional
    public Piece savePiece(Piecedto piecedto, Long categoryId) throws IllegalAccessException {
        Category category = categoryRepository.findById(categoryId);

        if (category == null) {
            throw new IllegalAccessException("Category not found");
        }

        Piece piece = new Piece();
        piece.setCateg(category);
        piece.setNamePiece(piecedto.getNamePiece());
        piece.setDescription(piecedto.getDescription());
        piece.setPieceState(piecedto.getPieceState());
        piece.setSupplier(piecedto.getSupplier());
        piece.setPrice(piecedto.getPrice());
        piece.setDateAdded(LocalDateTime.now());
        piece.setQuantity(piecedto.getQuantity());

        pieceRepository.persist(piece);

        return piece;
    }

    @Transactional
    public void removePiece(Long id) {
        Optional<Piece> pieceOp = pieceRepository.findByIdOptional(id);

        if (pieceOp.isEmpty()) {
            throw new NullPointerException("Piece not found !!!");
        }
        Piece piece = pieceOp.get();
        pieceRepository.delete(piece);
    }

 /*   @Transactional
    public void updatePiece(Long id, Piecedto piecedto) throws IllegalAccessException {
        Optional<Piece> pieceOp = pieceRepository.findByIdOptional(id);
        if (pieceOp.isEmpty()) {
            throw new NullPointerException("Piece not found");
        }
        Piece piece = pieceOp.get();

        // Track state changes
        if (piecedto.getPieceState() != null && !piecedto.getPieceState().equals(piece.getPieceState())) {
            logChange(piece.getId(), "State Changed", piece.getPieceState().toString(), piecedto.getPieceState().toString());
            piece.setPieceState(piecedto.getPieceState());
        }

        if (piecedto.getQuantity() != null && piecedto.getQuantity() >= 0 && piecedto.getQuantity() != piece.getQuantity()) {
            logChange(piece.getId(), "Quantity Updated", Integer.toString(piece.getQuantity()), Integer.toString(piecedto.getQuantity()));
            piece.setQuantity(piecedto.getQuantity());
        }

        pieceRepository.persist(piece); // Ensure that the updated piece is persisted
    }*/

    public List<PieceChangeLog> getLogsByPieceId(Long pieceId) {
        return changeLogRepository.findByPieceId(pieceId);
    }

    // Méthode pour récupérer les logs par nom de pièce
    public List<PieceChangeLog> getLogsByPieceName(String pieceName) {
        Piece piece = pieceRepository.findByName(pieceName);
        if (piece == null) {
            throw new IllegalArgumentException("Piece not found");
        }
        return changeLogRepository.findByPieceId(piece.getId());
    }




    @Transactional
    public void updatePiece(String pieceName, Piecedto piecedto) throws IllegalAccessException {
        Piece piece = pieceRepository.findByName(pieceName);  // Recherche par nom de pièce

        if (piece == null) {
            throw new NullPointerException("Piece not found");
        }

        // Suivi des changements d'état
        if (piecedto.getPieceState() != null && !piecedto.getPieceState().equals(piece.getPieceState())) {
            logChange(piece.getId(), "State Updated", piece.getPieceState().toString(), piecedto.getPieceState().toString());
            piece.setPieceState(piecedto.getPieceState());
        }


        if (piecedto.getNamePiece() != null && !piecedto.getNamePiece().equals(piece.getNamePiece())) {
            logChange(piece.getId(), "Piece Name Updated", piece.getNamePiece().toString(), piecedto.getNamePiece().toString());
            piece.setNamePiece(piecedto.getNamePiece());
        }

        if (piecedto.getDescription() != null && !piecedto.getDescription().equals(piece.getDescription())) {
            logChange(piece.getId(), "Description Updated", piece.getDescription().toString(), piecedto.getDescription().toString());
            piece.setDescription(piecedto.getDescription());
        }
        if (piecedto.getQuantity() != null && piecedto.getQuantity() >= 0 && piecedto.getQuantity() != piece.getQuantity()) {
            logChange(piece.getId(), "Quantity Updated", Integer.toString(piece.getQuantity()), Integer.toString(piecedto.getQuantity()));
            piece.setQuantity(piecedto.getQuantity());
        }

        if (piecedto.getPrice() != null && piecedto.getPrice() >= 0 && !piecedto.getPrice().equals(piece.getPrice())) {
            logChange(piece.getId(), "Price Updated", Integer.toString(piece.getPrice()), Integer.toString(piecedto.getPrice()));
            piece.setPrice(piecedto.getPrice());
        }

        if (piecedto.getSupplier() != null && !piecedto.getSupplier().equals(piece.getSupplier())) {
            logChange(piece.getId(), "Last_id Updated", piece.getSupplier().toString(), piecedto.getSupplier().toString());
            piece.setSupplier(piecedto.getSupplier());
        }


        pieceRepository.persist(piece);  // Assurez-vous que la pièce mise à jour est persistée
    }


    @Transactional
    public void logChange(Long pieceId, String changeType, String oldValue, String newValue) {
        PieceChangeLog log = new PieceChangeLog();
        log.setPieceId(pieceId);
        log.setChangeType(changeType);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setChangeDate(LocalDateTime.now());

        log.persist();
    }

    @Transactional
    public Piece postPiece(Piecedto piecedto) throws IllegalAccessException {
        Long categoryId = piecedto.getCategoryId(); // Assurez-vous que le categoryId est extrait du DTO
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }

        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new IllegalAccessException("Category not found");
        }

        Piece piece = new Piece();
        piece.setNamePiece(piecedto.getNamePiece());
        piece.setDescription(piecedto.getDescription());
        piece.setPrice(piecedto.getPrice());
        piece.setQuantity(piecedto.getQuantity());
        piece.setPieceState(PieceState.IN_STOCK);
        piece.setDateAdded(LocalDateTime.now());
        piece.setSupplier(piecedto.getSupplier());
       // piece.setCateg(piecedto.getCategoryId());
        piece.setCateg(category);

        pieceRepository.persist(piece);
        return piece;
    }


 /*   @Transactional
    public List<Piece> getAllPieces(){
        return pieceRepository.listAll();
    }*/
 @Transactional
 public List<Piecedto> getAllPieces() {
     return pieceRepository.listAll().stream()
             .map(Piece::getPieceDto)
             .collect(Collectors.toList());
 }

    @Transactional
    public List<PieceChangeLog> getChangeLog(Long pieceId) {
        return PieceChangeLog.list("pieceId", pieceId);
    }

    @Transactional
    public List<Piecedto> searchPieceByNamePiece(String namePiece) {
        return pieceRepository.findByNamePieceContaining(namePiece).stream()
                .map(Piece::getPieceDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void sellPiece(Long pieceId, String clientCin) throws IllegalAccessException {
        Optional<Piece> pieceOp = pieceRepository.findByIdOptional(pieceId);

        if (pieceOp.isEmpty()) {
            throw new IllegalAccessException("Piece not found with ID: " + pieceId);
        }

        Piece piece = pieceOp.get();

        // Mettre à jour l'état de la pièce à "vendu"
        piece.setPieceState(PieceState.SOLD); // Assurez-vous que "SOLD" est un état valide dans votre enum
        pieceRepository.persist(piece);

        // Enregistrer la vente
        PieceSale sale = new PieceSale();
        sale.setPiece(piece);
        sale.setClientCin(clientCin);
        sale.setSaleDate(LocalDateTime.now());
        pieceSaleRepository.persist(sale);
    }

    public List<Piece> getSoldPieces() {
        return pieceRepository.findByPieceState("SOLD");
    }


}

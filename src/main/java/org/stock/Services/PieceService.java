package org.stock.Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.stock.Entities.Category;
import org.stock.Entities.Piece;
import org.stock.Entities.PieceChangeLog;
import org.stock.dto.Piecedto;
import org.stock.repository.CategoryRepository;
import org.stock.repository.PieceRepository;

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

    @Transactional
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

        if (piecedto.getQuantity() >= 0 && piecedto.getQuantity() != piece.getQuantity()) {
            logChange(piece.getId(), "Quantity Updated", Integer.toString(piece.getQuantity()), Integer.toString(piecedto.getQuantity()));
            piece.setQuantity(piecedto.getQuantity());
        }

        pieceRepository.persist(piece); // Ensure that the updated piece is persisted
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
        piece.setPieceState(piecedto.getPieceState());
        piece.setDateAdded(LocalDateTime.now());
        piece.setSupplier(piecedto.getSupplier());
        piece.setCategory_id(piecedto.getCategoryId());
        piece.setCateg(category);

        pieceRepository.persist(piece);
        return piece;
    }


    @Transactional
    public List<Piece> getAllPieces(){
        return pieceRepository.listAll();
    }

    @Transactional
    public List<PieceChangeLog> getChangeLog(Long pieceId) {
        return PieceChangeLog.list("pieceId", pieceId);
    }

    @Transactional
    public List<Piecedto> searchPieceByNamePiece(String NamePiece) {
        return pieceRepository.findByNamePieceContaining(NamePiece).stream()
                .map(Piece::getPieceDto)
                .collect(Collectors.toList());
    }
}

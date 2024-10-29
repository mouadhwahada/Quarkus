package org.stock.Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.stock.Entities.*;
import org.stock.dto.ChangeLogRequestDTO;
import org.stock.dto.InterventionHistorydto;
import org.stock.repository.HistoryInterventionChangeRepository;
import org.stock.repository.InterventionHistoryRepository;
import org.stock.repository.PieceRepository;
import org.stock.repository.PieceSaleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class InterventionHistoryService {

    @Inject
    InterventionHistoryRepository interventionHistoryRepository;

    @Inject
    HistoryInterventionChangeRepository changeLogRepository;
    @Inject
    PieceRepository pieceRepository;

    @Inject
    PieceSaleRepository pieceSaleRepository;

    @Inject
    HistoryTreeService historyTreeService;

    @Inject
    PieceSaleService pieceSaleService;


    @Transactional
    public List<InterventionHistory> getAllInterventions() {
        return interventionHistoryRepository.listAll();
    }

    @Transactional
    public InterventionHistory createOrUpdateInterventionFromPurchaseNumberAndClientCin(InterventionHistorydto interventionDTO, Integer purchaseNumber) {



        // Rechercher la pièce basée sur le nom de la pièce
        Piece piece = pieceRepository.findByName(interventionDTO.getNamePiece());
        if (piece == null) {
            throw new NotFoundException("No piece found with name: " + interventionDTO.getNamePiece());
        }

        // Rechercher les ventes de pièce basées sur le numéro d'achat et le CIN du client
        List<PieceSale> pieceSales = pieceSaleRepository.findByClientCinAndPurchaseNumber(interventionDTO.getClientCin(), purchaseNumber);

        if (pieceSales.isEmpty()) {
            throw new NotFoundException("No piece sale found with client CIN: " + interventionDTO.getClientCin() + " and purchase number: " + purchaseNumber);
        }

        PieceSale pieceSale = pieceSales.get(0);

        // Vérifiez si la pièce associée à la vente correspond à celle trouvée par le nom
        if (!pieceSale.getPiece().equals(piece)) {
            throw new NotFoundException("Piece from sale does not match the provided piece name.");
        }

        // Rechercher un historique d'intervention existant pour cette pièce et ce client
        List<InterventionHistory> existingInterventions = interventionHistoryRepository.find("piece.id = ?1 and clientCin = ?2", piece.getId(), interventionDTO.getClientCin()).list();

        InterventionHistory lastIntervention = null;
        InterventionHistory newIntervention = new InterventionHistory();

        if (!existingInterventions.isEmpty()) {
            // Si des interventions existent déjà, nous récupérons la dernière intervention
            lastIntervention = existingInterventions.get(existingInterventions.size() - 1);

            // Copier les champs de la dernière intervention dans la nouvelle intervention
            newIntervention.setFirstIntervention(lastIntervention); // Associez la nouvelle intervention à la dernière

            // Log changes to the last intervention
            logChanges(
                    lastIntervention.getClientCin(),
                    lastIntervention.getTechnician(),
                    lastIntervention.getObservation(),
                    newIntervention
            );

            // Assigner les valeurs inchangées
            newIntervention.setPieceName(lastIntervention.getPieceName());
            newIntervention.setClientCin(lastIntervention.getClientCin());

        } else {
            // Si aucun historique d'intervention n'existe, cette nouvelle intervention sera la première
            newIntervention.setPieceName(piece.getNamePiece());
            newIntervention.setClientCin(interventionDTO.getClientCin());
        }

        // Remplir les détails de la nouvelle intervention
        newIntervention.setPiece(piece);
        newIntervention.setClient(interventionDTO.getClient());
        newIntervention.setInterventionDate(LocalDateTime.now());
        newIntervention.setInterventionNature(interventionDTO.getInterventionNature());
        newIntervention.setTechnician(interventionDTO.getTechnician());
        newIntervention.setResult(interventionDTO.getResult());
        newIntervention.setObservation(newIntervention.getObservation());
        newIntervention.setObservation(interventionDTO.getObservation());
        newIntervention.setPurchaseNumber(purchaseNumber); // Set the purchase number
        newIntervention.setPieceSale(pieceSale); // Set the PieceSale reference

        if (lastIntervention == null) {
            // Si c'est la première intervention, générer et affecter un nouvel antecedentId
            String antecedentId = generateAntecedentId();
            pieceSale.setAntecedentId(antecedentId);
            pieceSaleRepository.persist(pieceSale); // Persister après modification
        }

        interventionHistoryRepository.persist(newIntervention);

        // Mettre à jour le nombre d'achats du client
        pieceSaleService.updatePurchaseCount(interventionDTO.getClientCin());

        // Mettre à jour l'historique de l'arbre
        HistoryTree historyTree = new HistoryTree();
        historyTree.setPiece(piece);
        historyTree.setIdentifiantAntecedent(pieceSale.getAntecedentId());
        historyTree.setStatus(Etat.in_progress);
        historyTree.setEmplacementSource("Stock Admin");
        historyTree.setReperationDate(LocalDateTime.now());
        historyTree.setTechnician(interventionDTO.getTechnician());
        historyTree.setEmplacementDestination("Default Destination");
        historyTree.setClientCin(interventionDTO.getClientCin());
        historyTree.setPieceSale(pieceSale);

        historyTreeService.createHistory(historyTree);

        return newIntervention;
    }


    public List<InterventionHistory> getInterventionsByClientCinAndPieceName(String clientCin, String pieceName) {
        return interventionHistoryRepository.findByClientCinAndPieceName(clientCin, pieceName);
    }






    private String generateAntecedentId() {
        return UUID.randomUUID().toString();
    }

    public boolean hasIntervention(Long pieceId) {
        List<InterventionHistory> interventions = interventionHistoryRepository.findByPieceId(pieceId);
        return !interventions.isEmpty();
    }

    public List<InterventionHistory> getAllInterventionsForPiece(Long pieceId) {
        return interventionHistoryRepository.findByPieceId(pieceId);
    }

    public List<InterventionHistory> getAllInterventionsForClient(String clientCin) {
        return interventionHistoryRepository.findByClientCin(clientCin);
    }


    @Transactional
    public void logChanges(String oldClientCin, String oldTechnician, String oldObservation, InterventionHistory updatedIntervention) {
        HistoryInterventionChange changeLog = new HistoryInterventionChange();
        changeLog.setPiece(updatedIntervention.getPiece());
        changeLog.setOldClientCin(oldClientCin);
        changeLog.setOldTechnician(oldTechnician);
        changeLog.setNewTechnician(updatedIntervention.getTechnician());
        changeLog.setOldObservation(oldObservation);
        changeLog.setNewObservation(updatedIntervention.getObservation());
        changeLog.setChangeDate(LocalDateTime.now());

        // Persister le ChangeLog
        try {
            changeLogRepository.persist(changeLog);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while logging changes", e);
        }
    }

    public List<HistoryInterventionChange> getChangeLogForClient(String clientCin) {
        return changeLogRepository.findByClientCin(clientCin);
    }

    public List<HistoryInterventionChange> getChangeLogsByCriteria(String clientCin, Integer purchaseNumber, String pieceName) {
        return changeLogRepository.findByClientCinAndPurchaseNumberAndPieceName(clientCin, purchaseNumber, pieceName);
    }


    public List<HistoryInterventionChange> getChangeLogsByCriteria(ChangeLogRequestDTO criteria) {
        return changeLogRepository.findByCriteria(criteria.getClientCin(), criteria.getPurchaseNumber(), criteria.getPieceName());
    }



    //cette methode apporte la modificaton de la premiere intervention seulement
    /*
    public InterventionHistory createOrUpdateInterventionFromPurchaseNumberAndClientCin(InterventionHistorydto interventionDTO, Integer purchaseNumber) {
        // Rechercher la pièce basée sur le nom de la pièce
        Piece piece = pieceRepository.findByName(interventionDTO.getNamePiece());
        if (piece == null) {
            throw new NotFoundException("No piece found with name: " + interventionDTO.getNamePiece());
        }

        // Rechercher les ventes de pièce basées sur le numéro d'achat et le CIN du client
        List<PieceSale> pieceSales = pieceSaleRepository.findByClientCinAndPurchaseNumber(interventionDTO.getClientCin(), purchaseNumber);

        if (pieceSales.isEmpty()) {
            throw new NotFoundException("No piece sale found with client CIN: " + interventionDTO.getClientCin() + " and purchase number: " + purchaseNumber);
        }

        PieceSale pieceSale = pieceSales.get(0);

        // Vérifiez si la pièce associée à la vente correspond à celle trouvée par le nom
        if (!pieceSale.getPiece().equals(piece)) {
            throw new NotFoundException("Piece from sale does not match the provided piece name.");
        }

        // Rechercher un historique d'intervention existant pour cette pièce et ce client
        List<InterventionHistory> existingInterventions = interventionHistoryRepository.find("piece.id = ?1 and clientCin = ?2", piece.getId(), interventionDTO.getClientCin()).list();

        InterventionHistory firstIntervention;
        InterventionHistory newIntervention = new InterventionHistory();

        if (!existingInterventions.isEmpty()) {
            // Si un historique d'intervention existe déjà, nous le récupérons comme première intervention
            firstIntervention = existingInterventions.get(0);

            // Log changes to the first intervention
            logChanges(firstIntervention.getClientCin(), firstIntervention.getTechnician(), firstIntervention.getObservation(), firstIntervention);

            // Copier les champs de la première intervention dans la nouvelle intervention
            newIntervention.setFirstIntervention(firstIntervention); // Associez la nouvelle intervention à la première

        } else {
            // Si aucun historique d'intervention n'existe, cette nouvelle intervention sera la première
            firstIntervention = newIntervention;
        }

        // Remplir les détails de la nouvelle intervention
        newIntervention.setPiece(piece);
        newIntervention.setClient(interventionDTO.getClient());
        newIntervention.setClientCin(interventionDTO.getClientCin());
        newIntervention.setInterventionDate(LocalDateTime.now());
        newIntervention.setInterventionNature(interventionDTO.getInterventionNature());
        newIntervention.setTechnician(interventionDTO.getTechnician());
        newIntervention.setResult(interventionDTO.getResult());
        newIntervention.setObservation(interventionDTO.getObservation());
        newIntervention.setPurchaseNumber(purchaseNumber); // Set the purchase number
        newIntervention.setPieceSale(pieceSale); // Set the PieceSale reference

        if (firstIntervention == newIntervention) {
            // Si c'est la première intervention, générer et affecter un nouvel antecedentId
            String antecedentId = generateAntecedentId();
            pieceSale.setAntecedentId(antecedentId);
            pieceSaleRepository.persist(pieceSale); // Persister après modification
        }

        interventionHistoryRepository.persist(newIntervention);

        // Mettre à jour le nombre d'achats du client
        pieceSaleService.updatePurchaseCount(interventionDTO.getClientCin());

        // Mettre à jour l'historique de l'arbre
        HistoryTree historyTree = new HistoryTree();
        historyTree.setPiece(piece);
        historyTree.setIdentifiantAntecedent(pieceSale.getAntecedentId());
        historyTree.setStatus(Etat.in_progress);
        historyTree.setEmplacementSource("Stock Admin");
        historyTree.setReperationDate(LocalDateTime.now());
        historyTree.setTechnician(interventionDTO.getTechnician());
        historyTree.setEmplacementDestination("Default Destination");
        historyTree.setClientCin(interventionDTO.getClientCin());
        historyTree.setPieceSale(pieceSale);

        historyTreeService.createHistory(historyTree);

        return newIntervention;
    }*/

   /* public InterventionHistory createOrUpdateInterventionFromPurchaseNumberAndClientCin(InterventionHistorydto interventionDTO, Integer purchaseNumber) {
        // Rechercher la pièce basée sur le nom de la pièce
        Piece piece = pieceRepository.findByName(interventionDTO.getNamePiece());
        if (piece == null) {
            throw new NotFoundException("No piece found with name: " + interventionDTO.getNamePiece());
        }

        // Rechercher les ventes de pièce basées sur le numéro d'achat et le CIN du client
        List<PieceSale> pieceSales = pieceSaleRepository.findByClientCinAndPurchaseNumber(interventionDTO.getClientCin(), purchaseNumber);

        if (pieceSales.isEmpty()) {
            throw new NotFoundException("No piece sale found with client CIN: " + interventionDTO.getClientCin() + " and purchase number: " + purchaseNumber);
        }

        PieceSale pieceSale = pieceSales.get(0);

        // Vérifiez si la pièce associée à la vente correspond à celle trouvée par le nom
        if (!pieceSale.getPiece().equals(piece)) {
            throw new NotFoundException("Piece from sale does not match the provided piece name.");
        }

        // Rechercher un historique d'intervention existant
        List<InterventionHistory> existingInterventions = interventionHistoryRepository.find("piece.id = ?1 and clientCin = ?2", piece.getId(), interventionDTO.getClientCin()).list();

        InterventionHistory intervention;

        if (!existingInterventions.isEmpty()) {
            // Si un historique d'intervention existe déjà, nous le mettons à jour
            intervention = existingInterventions.get(0);
            logChanges(intervention.getClientCin(), intervention.getTechnician(), intervention.getObservation(), intervention);
            intervention.setInterventionDate(LocalDateTime.now());
            intervention.setInterventionNature(interventionDTO.getInterventionNature());
            intervention.setTechnician(interventionDTO.getTechnician());
            intervention.setResult(interventionDTO.getResult());
            intervention.setObservation(interventionDTO.getObservation());
        } else {
            // Si aucun historique d'intervention n'existe, nous en créons un nouveau
            intervention = new InterventionHistory();
            intervention.setPiece(piece);
            intervention.setClient(interventionDTO.getClient());
            intervention.setClientCin(interventionDTO.getClientCin());
            intervention.setInterventionDate(LocalDateTime.now());
            intervention.setInterventionNature(interventionDTO.getInterventionNature());
            intervention.setTechnician(interventionDTO.getTechnician());
            intervention.setResult(interventionDTO.getResult());
            intervention.setObservation(interventionDTO.getObservation());
            intervention.setPurchaseNumber(purchaseNumber); // Set the purchase number
            intervention.setPieceSale(pieceSale); // Set the PieceSale reference

            // Toujours générer et affecter un nouvel antecedentId pour chaque purchaseNumber
            String antecedentId = generateAntecedentId();
            pieceSale.setAntecedentId(antecedentId);
            pieceSaleRepository.persist(pieceSale); // Persister après modification

            interventionHistoryRepository.persist(intervention);
        }

        // Mettre à jour le nombre d'achats du client
        pieceSaleService.updatePurchaseCount(interventionDTO.getClientCin());

        // Mettre à jour l'historique de l'arbre
        HistoryTree historyTree = new HistoryTree();
        historyTree.setPiece(piece);
        historyTree.setIdentifiantAntecedent(pieceSale.getAntecedentId());
        historyTree.setStatus(Etat.in_progress);
        historyTree.setEmplacementSource("Stock Admin");
        historyTree.setReperationDate(LocalDateTime.now());
        historyTree.setTechnician(interventionDTO.getTechnician());
        historyTree.setEmplacementDestination("Default Destination");
        historyTree.setClientCin(interventionDTO.getClientCin());
        historyTree.setPieceSale(pieceSale);

        historyTreeService.createHistory(historyTree);

        return intervention;
    }
*/

    /*
    @Transactional
    public InterventionHistory createInterventionFromPurchaseNumberAndClientCin(InterventionHistorydto interventionDTO, Integer purchaseNumber) {
        // Rechercher la pièce basée sur le nom de la pièce
        Piece piece = pieceRepository.findByName(interventionDTO.getNamePiece());
        if (piece == null) {
            throw new NotFoundException("No piece found with name: " + interventionDTO.getNamePiece());
        }

        // Rechercher les ventes de pièce basées sur le numéro d'achat et le CIN du client
        List<PieceSale> pieceSales = pieceSaleRepository.findByClientCinAndPurchaseNumber(interventionDTO.getClientCin(), purchaseNumber);

        if (pieceSales.isEmpty()) {
            throw new NotFoundException("No piece sale found with client CIN: " + interventionDTO.getClientCin() + " and purchase number: " + purchaseNumber);
        }

        // Supposons qu'il y ait une seule vente pour ce numéro d'achat et CIN
        PieceSale pieceSale = pieceSales.get(0);

        // Vérifiez si la pièce associée à la vente correspond à celle trouvée par le nom
        if (!pieceSale.getPiece().equals(piece)) {
            throw new NotFoundException("Piece from sale does not match the provided piece name.");
        }

        // Toujours générer et affecter un nouvel antecedentId pour chaque purchaseNumber
        String antecedentId = generateAntecedentId();
        pieceSale.setAntecedentId(antecedentId);
        pieceSaleRepository.persist(pieceSale); // Persister après modification

        InterventionHistory intervention = new InterventionHistory();
        intervention.setPiece(piece);
        intervention.setClientCin(interventionDTO.getClientCin());
        intervention.setInterventionDate(LocalDateTime.now());
        intervention.setInterventionNature(interventionDTO.getInterventionNature());
        intervention.setTechnician(interventionDTO.getTechnician());
        intervention.setResult(interventionDTO.getResult());
        intervention.setObservation(interventionDTO.getObservation());
        intervention.setPurchaseNumber(purchaseNumber); // Set the purchase number
        intervention.setPieceSale(pieceSale); // Set the PieceSale reference

        try {
            interventionHistoryRepository.persist(intervention);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while persisting intervention", e);
        }

        // Mettre à jour le nombre d'achats du client
        pieceSaleService.updatePurchaseCount(interventionDTO.getClientCin());

        HistoryTree historyTree = new HistoryTree();
        historyTree.setPiece(piece);
        historyTree.setIdentifiantAntecedent(antecedentId);
        historyTree.setStatus(Etat.in_progress);
        historyTree.setEmplacementSource("Stock Admin");
        historyTree.setReperationDate(LocalDateTime.now());
        historyTree.setTechnician(interventionDTO.getTechnician());
        historyTree.setEmplacementDestination("Default Destination");
        historyTree.setClientCin(interventionDTO.getClientCin()); // Set the client CIN
        historyTree.setPieceSale(pieceSale); // Set the PieceSale reference

        historyTreeService.createHistory(historyTree);

        return intervention;
    }
    */



    /*
    @Transactional
    public InterventionHistory updateInterventionHistory(Long pieceId, Integer purchaseNumber, String clientCin, String technician, String observation) {
        // Trouver l'intervention historique par pieceId et purchaseNumber
        List<InterventionHistory> interventions = interventionHistoryRepository.find("piece.id = ?1 and purchaseNumber = ?2", pieceId, purchaseNumber).list();

        if (interventions.isEmpty()) {
            throw new NotFoundException("Intervention history not found with Piece ID: " + pieceId + " and Purchase Number: " + purchaseNumber);
        }

        // Supposons qu'il y ait une seule intervention pour ce pieceId et purchaseNumber
        InterventionHistory intervention = interventions.get(0);

        // Enregistrer les anciens champs avant de les modifier pour garder une trace
        String oldClientCin = intervention.getClientCin();
        String oldTechnician = intervention.getTechnician();
        String oldObservation = intervention.getObservation();

        // Mettre à jour les champs si de nouvelles valeurs sont fournies
        if (clientCin != null && !clientCin.isEmpty()) {
            intervention.setClientCin(clientCin);
        }
        if (technician != null && !technician.isEmpty()) {
            intervention.setTechnician(technician);
        }
        if (observation != null && !observation.isEmpty()) {
            intervention.setObservation(observation);
        }

        // Persister les changements
        try {
            interventionHistoryRepository.persist(intervention);
            logChanges(oldClientCin, oldTechnician, oldObservation, intervention);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating intervention", e);
        }

        return intervention;
    }*/

}

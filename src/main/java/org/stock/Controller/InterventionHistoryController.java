package org.stock.Controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.stock.Entities.HistoryInterventionChange;
import org.stock.Entities.InterventionHistory;
import org.stock.Services.InterventionHistoryService;
import org.stock.dto.ChangeLogRequestDTO;
import org.stock.dto.InterventionHistoryUpdateDTO;
import org.stock.dto.InterventionHistorydto;
import org.stock.dto.InterventionRequest;

import java.util.List;
import java.util.logging.Logger;

@Path("/interventions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InterventionHistoryController {

    private static final Logger LOG = Logger.getLogger(String.valueOf(InterventionHistoryController.class));



    @Inject
    InterventionHistoryService interventionHistoryService;


    @GET
    @Path("/by-client-cin/{clientCin}/and-piece-name/{pieceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInterventionsByClientCinAndPieceName(
            @PathParam("clientCin") String clientCin,
            @PathParam("pieceName") String pieceName) {

        List<InterventionHistory> interventions = interventionHistoryService.getInterventionsByClientCinAndPieceName(clientCin, pieceName);

        if (interventions.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No interventions found for the provided client CIN and piece name").build();
        }

        return Response.ok(interventions).build();
    }


    // @GET
    @Path("/client")
    public Response getAllInterventionsForClient(@QueryParam("clientCin") String clientCin) {
        try {
            List<InterventionHistory> interventions = interventionHistoryService.getAllInterventionsForClient(clientCin);
            return Response.ok(interventions).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    // @POST
    @Path("/change-log")
    public Response getChangeLogByCriteria(ChangeLogRequestDTO criteria) {
        try {
            List<HistoryInterventionChange> changeLogs = interventionHistoryService.getChangeLogsByCriteria(criteria);
            if (changeLogs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No change logs found for the provided criteria").build();
            }
            return Response.ok(changeLogs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<InterventionHistory> getAllInterventions() {
        return interventionHistoryService.getAllInterventions();
    }
    @POST
    @Path("/create-from-purchase-number")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrUpdateIntervention(InterventionRequest request) {
        try {
            // Récupérer les informations du DTO
            InterventionHistorydto interventionDTO = request.getInterventionDTO();
            Integer purchaseNumber = request.getPurchaseNumber();

            // Appeler le service avec les informations extraites
            InterventionHistory intervention = interventionHistoryService.createOrUpdateInterventionFromPurchaseNumberAndClientCin(interventionDTO, purchaseNumber);
            return Response.ok(intervention).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }}


   /*  @POST
    @Path("/create-from-purchase-number")
     public Response createOrUpdateIntervention(InterventionHistorydto interventionDTO, @QueryParam("purchaseNumber") Integer purchaseNumber) {
         try {
             InterventionHistory intervention = interventionHistoryService.createOrUpdateInterventionFromPurchaseNumberAndClientCin(interventionDTO, purchaseNumber);
             return Response.ok(intervention).build();
         } catch (NotFoundException e) {
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
         } catch (Exception e) {

             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
         }
     }*/



   /* @GET
    @Path("/find-antecedent")
    public Response getAntecedent(
            @QueryParam("clientCin") String clientCin,
            @QueryParam("pieceName") String pieceName,
            @QueryParam("purchaseNumber") Integer purchaseNumber) {

        try {
            // Récupérez les interventions à partir du service
            List<InterventionHistory> interventions = interventionHistoryService.getInterventionsByClientCinAndPieceName(clientCin, pieceName);
            if (interventions.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No interventions found").build();
            }

            // Supposons que vous vouliez obtenir le premier enregistrement pour l'exemple
            InterventionHistory latestIntervention = interventions.get(0);
            String antecedentId = latestIntervention.getAntecedentId(); // Assurez-vous que cette méthode existe
            return Response.ok(antecedentId).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }}
*/



     //controller de la methode qui get seulement first interventionhistory
     /*
     public Response createOrUpdateIntervention(InterventionHistorydto interventionDTO) {
         // Extraire le numéro d'achat du DTO
         Integer purchaseNumber = interventionDTO.getPurchaseNumber();

         // Appeler la méthode de service pour créer ou mettre à jour l'intervention
         InterventionHistory interventionHistory = interventionHistoryService.createOrUpdateInterventionFromPurchaseNumberAndClientCin(interventionDTO, purchaseNumber);

         // Retourner la réponse avec l'intervention créée ou mise à jour
         return Response.ok(interventionHistory).build();
     }*/
     /*
     public Response createOrUpdateIntervention(@QueryParam("purchaseNumber") Integer purchaseNumber, InterventionHistorydto interventionDTO) {
         try {
             // Vérifier que le DTO contient les informations nécessaires
             if (interventionDTO.getClientCin() == null || interventionDTO.getNamePiece() == null || purchaseNumber == null) {
                 return Response.status(Response.Status.BAD_REQUEST)
                         .entity("Client CIN, Piece Name, and Purchase Number must be provided.")
                         .build();
             }

             // Appeler le service pour créer ou mettre à jour l'historique d'intervention
             InterventionHistory intervention = interventionHistoryService.createOrUpdateInterventionFromPurchaseNumberAndClientCin(interventionDTO, purchaseNumber);

             // Retourner une réponse réussie avec l'intervention créée ou mise à jour
             return Response.status(Response.Status.CREATED).entity(intervention).build();
         } catch (NotFoundException e) {
             // Gérer les erreurs NotFound
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
         } catch (Exception e) {
             // Gérer les erreurs inattendues
             e.printStackTrace();
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while processing the request.").build();
         }
     }
*/



     //avant le changement que chque piece peut faire plusieurs interventions
    /* public Response createInterventionFromPurchaseNumber(InterventionHistorydto interventionDTO) {
         try {
             // Ensure that the DTO contains the necessary information
             if (interventionDTO.getClientCin() == null || interventionDTO.getNamePiece() == null || interventionDTO.getPurchaseNumber() == null) {
                 return Response.status(Response.Status.BAD_REQUEST)
                         .entity("Client CIN, Piece Name, and Purchase Number must be provided.")
                         .build();
             }

             // Call the service method to create the intervention
             InterventionHistory intervention = interventionHistoryService.createInterventionFromPurchaseNumberAndClientCin(interventionDTO, interventionDTO.getPurchaseNumber());

             // Return a successful response with the created intervention
             return Response.status(Response.Status.CREATED).entity(intervention).build();
         } catch (NotFoundException e) {
             // Handle not found errors
             return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
         } catch (Exception e) {
             // Handle unexpected errors
             e.printStackTrace();
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while processing the request.").build();
         }

    }*/


 /*   @PUT
    @Path("/update")
    public Response updateInterventionHistory(InterventionHistoryUpdateDTO updateDTO) {
        try {
            // Pass all the parameters from the DTO to the service method
            InterventionHistory updatedIntervention = interventionHistoryService.updateInterventionHistory(
                    updateDTO.getPieceId(),
                    updateDTO.getPurchaseNumber(),
                    updateDTO.getClientCin(),
                    updateDTO.getTechnician(),
                    updateDTO.getObservation()
            );
            return Response.ok(updatedIntervention).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/change-log")
    public Response getChangeLogForClient(@QueryParam("clientCin") String clientCin) {
        try {
            List<HistoryInterventionChange> changeLogs = interventionHistoryService.getChangeLogForClient(clientCin);
            return Response.ok(changeLogs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }*/



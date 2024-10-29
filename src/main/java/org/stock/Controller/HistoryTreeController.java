package org.stock.Controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.stock.Entities.*;
import org.stock.Services.HistoryTreeService;
import org.stock.dto.HistoryTreeUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

@Path("/history-tree")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HistoryTreeController {

    @Inject
    HistoryTreeService historyTreeService;

    @GET
    @Path("/{clientCin}/{purchaseNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistoryTree(
            @PathParam("clientCin") String clientCin,
            @PathParam("purchaseNumber") Integer purchaseNumber) {
        try {
            HistoryTree historyTree = historyTreeService.getHistoryTreeDetails(clientCin, purchaseNumber);
            return Response.ok(historyTree).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getByDetails")
    public Response getHistoryTreeDetails(@QueryParam("clientCin") String clientCin,
                                          @QueryParam("purchaseNumber") Integer purchaseNumber) {
        try {
            HistoryTree historyTree = historyTreeService.getHistoryTreeDetails(clientCin, purchaseNumber);
            if (historyTree != null) {
                return Response.ok(historyTree).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("HistoryTree not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    @PUT
    @Path("/update/{clientCin}/{purchaseNumber}")
    @Transactional
    public Response updateHistoryTree(
            @PathParam("clientCin") String clientCin,
            @PathParam("purchaseNumber") Integer purchaseNumber,
            HistoryTreeUpdateRequest request) {

        try {
            HistoryTree updatedHistoryTree = historyTreeService.updateHistoryTreeDetails(
                    clientCin,
                    purchaseNumber,
                    request.getEmplacementSource(),
                    request.getReperationDate(),
                    request.getEmplacementDestination(),
                    request.getStatus(),
                    request.getObservation()
            );
            return Response.ok(updatedHistoryTree).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("/update")
    public Response updateHistoryTree(UpdateHistoryTreeRequest request) {

        try {
            HistoryTree updatedHistoryTree = historyTreeService.updateHistoryTreeDetails(
                    request.getClientCin(),
                    request.getPurchaseNumber(),
                    request.getEmplacementSource(),
                    request.getReperationDate(),
                    request.getEmplacementDestination(),
                    request.getStatus(),
                    request.getObservation()
            );
            return Response.ok(updatedHistoryTree).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HistoryTree> getAllInterventions() {
        return historyTreeService.getAllHistoryTrees();
    }
}

    // @PUT
  /*  @Path("/{clientCin}/{purchaseNumber}")
    public Response updateHistoryTree(
            @PathParam("clientCin") String clientCin,
            @PathParam("purchaseNumber") Integer purchaseNumber,
            String emplacementSource,
            LocalDateTime reperationDate,
            String emplacementDestination,
            Etat status,
            Observation observation
    ) {
        try {
            // Update the HistoryTree details
            HistoryTree updatedHistoryTree = historyTreeService.updateHistoryTreeDetails(
                    clientCin, purchaseNumber, emplacementSource, reperationDate, emplacementDestination, status, observation
            );

            return Response.ok(updatedHistoryTree).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }*/






  /*  @GET
    @Path("/by-piece/{clientCin}/{purchaseNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChangeLogsByPieceId(
            @PathParam("clientCin") String clientCin,
            @PathParam("purchaseNumber") Integer purchaseNumber) {
        try {
            List<ChangeLogTreeHistory> changeLogs = historyTreeService.getChangeLogsByPieceId(clientCin, purchaseNumber);
            if (changeLogs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No change logs found").build();
            }
            return Response.ok(changeLogs).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }*/




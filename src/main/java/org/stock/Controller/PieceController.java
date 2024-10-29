package org.stock.Controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.stock.Entities.Piece;
import org.stock.Entities.PieceChangeLog;
import org.stock.Services.PieceService;
import org.stock.dto.Piecedto;
import org.stock.repository.PieceRepository;

import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/piece")
public class PieceController {

    @Inject
    PieceService pieceService;

    @Inject
    PieceRepository pieceRepository;






    @POST
    @Path("/post")
    public Response postPiece(Piecedto piecedto) {
        try {
            pieceService.postPiece(piecedto);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Category not found: " + e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid input: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{pieceId}")
    public Response updatePiece(@PathParam("pieceId") Long pieceId, Piecedto piecedto) {
        try {
            pieceService.updatePiece(String.valueOf(pieceId), piecedto); // Mise à jour basée sur l'ID de la pièce
            return Response.ok().build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Piece not found: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{pieceName}")
    public Response updatePiece(@PathParam("pieceName") String pieceName, Piecedto piecedto) {
        try {
            pieceService.updatePiece(pieceName, piecedto);  // Utilisation de pieceName au lieu de id
            return Response.ok().build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Piece or Category not found: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage()).build();
        }
    }


    @GET
    @Path("/by-piece-id/{pieceId}")
    public Response getLogsByPieceId(@PathParam("pieceId") Long pieceId) {
        List<PieceChangeLog> logs = pieceService.getLogsByPieceId(pieceId);
        if (logs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No logs found for piece ID: " + pieceId).build();
        }
        return Response.ok(logs).build();
    }

    // Endpoint pour obtenir les logs par nom de pièce
    @GET
    @Path("/by-piece-name/{pieceName}")
    public Response getLogsByPieceName(@PathParam("pieceName") String pieceName) {
        try {
            List<PieceChangeLog> logs = pieceService.getLogsByPieceName(pieceName);
            if (logs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No logs found for piece name: " + pieceName).build();
            }
            return Response.ok(logs).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/all")
    public Response getAllPieces() {
        List<Piecedto> pieces = pieceService.getAllPieces();
        return Response.ok(pieces).build();
    }
    @GET
    @Path("/search/{namePiece}")
    public Response searchPieceByNamePiece(@PathParam("namePiece") String namePiece) {
        List<Piecedto> pieces = pieceService.searchPieceByNamePiece(namePiece);
        return Response.ok(pieces).build();
    }


    @Path("/SOLD")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSoldPieces() {
        try {
            List<Piece> soldPieces = pieceService.getSoldPieces();
            return Response.ok(soldPieces).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    // @GET
    @Path("{id}")
    public Response getPiece(@PathParam("id") Long id) {
        Piece piece = pieceRepository.findById(id) ;
        if (piece == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(piece).build();
    }




   /* @GET
    @Path("/{id}/log")
    public Response getChangeLog(@PathParam("id") Long pieceId) {
        List<PieceChangeLog> changeLogs = pieceService.getChangeLog(pieceId);
        if (changeLogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No change logs found for piece ID: " + pieceId).build();
        }
        return Response.ok(changeLogs).build();
    }*/


/*
    @POST
    @Path("/sell")
    @Transactional
    public Response sellPiece(@QueryParam("pieceId") Long pieceId, @QueryParam("clientCin") String clientCin) {
        try {
            pieceService.sellPiece(pieceId, clientCin);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }*/

    /*@POST
    @Path("/log")
    public Response logChange(@QueryParam("pieceId") Long pieceId,
                              @QueryParam("changeType") String changeType,
                              @QueryParam("oldValue") String oldValue,
                              @QueryParam("newValue") String newValue) {
        try {
            pieceService.logChange(pieceId, changeType, oldValue, newValue);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }*/

   /* @POST
    @Path("/add")
    public Response savePiece(Piecedto piecedto, @QueryParam("categoryId") Long categoryId) {
        try {
            Piece piece = pieceService.savePiece(piecedto, categoryId);
            return Response.status(Response.Status.CREATED).entity(piece).build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }*/

 /*   @PUT
   @Path("/{id}")
    public Response updatePiece(@PathParam("id") Long id, Piecedto piecedto) {
        try {
            pieceService.updatePiece(id, piecedto);
            return Response.ok().build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }*/

  /*  @DELETE
    @Path("/{id}")
    public Response removePiece(@PathParam("id") Long id) {
        try {
            pieceService.removePiece(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }*/


  /*  @PUT
    @Path("/{id}")
    public Response updatePiece(@PathParam("id") Long id, Piecedto piecedto) {
        try {
            pieceService.updatePiece(id, piecedto);
            return Response.ok().build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Piece or Category not found: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage()).build();
        }
    }*/

}

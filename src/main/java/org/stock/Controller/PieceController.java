package org.stock.Controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.stock.Entities.Piece;
import org.stock.Entities.PieceChangeLog;
import org.stock.Services.PieceService;
import org.stock.dto.Piecedto;
import org.stock.repository.PieceRepository;

import java.io.IOException;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/piece")
public class PieceController {

    @Inject
    PieceService pieceService;

    @Inject
    PieceRepository pieceRepository;

   /* @GET
    public Response listPieces() {
        List<Piece> pieces = pieceService.listPieces();
        return Response.ok(pieces).build();
    }*/

    @GET
    @Path("{id}")
    public Response getPiece(@PathParam("id") Long id) {
        Piece piece = pieceRepository.findById(id) ;
        if (piece == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(piece).build();
    }

   // @POST
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
    }

    /*
    @POST
    @Path("/add")
    public Response savePiece(Piecedto piecedto, @QueryParam("categoryId") Long categoryId) {
        try {
            Piece piece = pieceService.savePiece(piecedto, categoryId);
            return Response.ok(piece).status(Response.Status.CREATED).build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }*/

    @PUT
    @Path("{id}")
    public Response updatePiece(@PathParam("id") Long id, Piecedto piecedto) {
        try {
            pieceService.updatePiece(id, piecedto);
            return Response.ok().build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

   // @DELETE
    @Path("{id}")
    public Response removePiece(@PathParam("id") Long id) {
        try {
            pieceService.removePiece(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST

    //@Path("/post/{categoryId}")
    @Path("/post")
    public Response postPiece(Piecedto piecedto) {
        try {
            Piece postedPiece = pieceService.postPiece(piecedto);
            return Response.status(Response.Status.CREATED).entity(postedPiece).build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/pieces")
    public Response getAllPieces() {
        List<Piece> piecedtoList = pieceService.getAllPieces();
        return Response.ok(piecedtoList).build();
    }

    @GET
    @Path("{id}/log")
    public Response getChangeLog(@PathParam("id") Long pieceId) {
        List<PieceChangeLog> logs = pieceService.getChangeLog(pieceId);
        if (logs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No change logs found for this piece.").build();
        }
        return Response.ok(logs).build();
    }
    @GET
    @Path("/search/{NamePiece}")
    public Response searchPieceByNamePiece(@PathParam("NamePiece") String NamePiece) {
        List<Piecedto> pieces = pieceService.searchPieceByNamePiece(NamePiece);
        return Response.ok(pieces).build();
    }
}

   /* @POST
    @Path("/{categoryId}")
    public Response postPiece(@PathParam("categoryId") Long categoryId, Piecedto piecedto) {
        try {
            Piece postedPiece = pieceService.postPiece(categoryId, piecedto);
            return Response.status(Response.Status.CREATED).entity(postedPiece).build();
        } catch (IllegalAccessException | IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}*/

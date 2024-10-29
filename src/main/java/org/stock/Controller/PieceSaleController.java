package org.stock.Controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.stock.Entities.PieceSale;
import org.stock.Services.PieceSaleService;
import org.stock.dto.SaleRequestDto;

import java.util.List;

@Path("/piecesale")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PieceSaleController {

    @Inject
    PieceSaleService pieceSaleService;

    // Endpoint pour vendre une pièce
    @POST
    @Path("/sell")
    @Transactional
    public Response sellPiece(SaleRequestDto saleRequestDto) {
        try {
            pieceSaleService.sellPiece(
                    saleRequestDto.getPieceName(),
                    saleRequestDto.getQuantitySold(),
                    saleRequestDto.getClientCin()
            );
            return Response.status(Response.Status.CREATED).entity("Piece sold successfully").build();
        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred: " + e.getMessage()).build();
        }
    }

    // Endpoint pour récupérer toutes les ventes par clientCin
    @GET
    @Path("/client/{clientCin}")
    public Response getSalesByClientCin(@PathParam("clientCin") String clientCin) {
        List<PieceSale> sales = pieceSaleService.findSalesByClientCin(clientCin);
        if (sales.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No sales found for the given client CIN").build();
        }
        return Response.ok(sales).build();
    }

    // Endpoint pour récupérer toutes les ventes de pièces
    @GET
    @Path("/all")
    public Response getAllSoldPieces() {
        List<PieceSale> sales = pieceSaleService.getAllSoldPieces();
        return Response.ok(sales).build();
    }
}

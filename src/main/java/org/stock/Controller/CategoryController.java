package org.stock.Controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.stock.Entities.Category;
import org.stock.Entities.CategoryChangeLog;
import org.stock.Services.CategoryService;
import org.stock.dto.Categorydto;
import org.stock.repository.CategoryRepository;

import java.util.List;

@Path("category")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @Inject
    CategoryRepository categoryRepository;

    @GET
    public Response listCategories(){
        List<Category> categories = categoryService.listCategories();
        return  Response.ok(categories).build();
    }

    @GET
    @Path("{id}")
    public Response getCategory(@PathParam("id") Long id){
        Category category=categoryRepository.findById(id);
        return Response.ok(category).build();
    }

    @POST
    @Path("/add")
    public Response saveCategory(Categorydto categorydto){
        Category category = categoryService.saveCategory(categorydto);
        return Response.ok(category).status(201).build();
    }

    //@DELETE
    @Path("{id}")
    public Response removeCategory(@PathParam("id") Long id){
        categoryService.removeCategory(id);
        return Response.status(204).build();
    }

    @PUT
    @Path("{id}")
    public Response updateCategory(@PathParam("id") Long id, Categorydto categorydto){
        try {
            categoryService.updateCategory(id, categorydto);
            return Response.ok().build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
   // @POST
    @Path("/log")
    public Response logChange(@QueryParam("categoryId") Long categoryId,
                              @QueryParam("changeType") String changeType,
                              @QueryParam("oldValue") String oldValue,
                              @QueryParam("newValue") String newValue) {
        try {
            categoryService.logChange(categoryId,changeType, oldValue, newValue);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{category_id}/log")
    public Response getChangeLog(@PathParam("category_id") Long categoryId) {
        List<CategoryChangeLog> logs = categoryService.getChangeLog( categoryId);
        if (logs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No change logs found for this category.").build();
        }
        return Response.ok(logs).build();
    }
}

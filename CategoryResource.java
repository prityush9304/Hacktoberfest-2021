package com.oracle.fodlite.svc.catalogService;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.oracle.fodlite.svc.catalogService.database.Database;
import com.oracle.fodlite.svc.catalogService.model.Category;
import com.oracle.fodlite.svc.catalogService.model.Product;
import io.helidon.webserver.ServerRequest;
import org.eclipse.microprofile.metrics.*;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Path("/category")
@Traced
@RequestScoped
public class CategoryResource {

    @Inject
    Metrics metrics;

    @Inject
    Database database;

    @Inject
    MetricRegistry registry;

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
    private List<Category> allCategories = new ArrayList<>(this.database.getCategories().values());
    private List<Product> allProducts= new ArrayList<>(this.database.getProducts().values());

    @Context
    private ServerRequest serverRequest;

    @GET
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(){
        System.out.println(database.getCategories().values());
        try {

            JsonArrayBuilder catJsonBldr = Json.createArrayBuilder();
            for( Category c : allCategories) {
                catJsonBldr.add(Json.createObjectBuilder().add("name", c.getName()).add("id", c.getId()));
            }
            JsonObjectBuilder jsonCategories = Json.createObjectBuilder().add("items", catJsonBldr);
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).entity(jsonCategories.build()).build();
        }catch (Exception e){
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        finally {
        }
    }

    @GET
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Path("{id}/product")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsWithinCategory(@PathParam("id") long categoryId){

        try {
            JsonArrayBuilder prodJsonBldr = Json.createArrayBuilder();
            for( Product p : allProducts) {
                if(p.getCategoryId()!=categoryId) continue;
                prodJsonBldr.add(
                        Json.createObjectBuilder()
                                .add("productId",p.getId())
                                .add("name", p.getName())
                                .add("imageUrl", p.getImageUrl())
                                .add("categoryId", p.getCategoryId())
                                .add("description", p.getDescription())
                                .add("featured", p.getFeatured())
                                .add("available" , p.getAvailable())
                                .add("numOfItems" , p.getNumOfItems())
                                .add("price" , p.getPrice())

                );
            }
            JsonObjectBuilder jsonCategories = Json.createObjectBuilder().add("items", prodJsonBldr);
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).entity(jsonCategories.build()).build();
        }catch (Exception e){
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        finally {
        }

    }

    @GET
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Path("{categoryId}/product/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsWithinCategoryById(@PathParam("categoryId") long categoryId,@PathParam("productId") long productId ){

        try {

            Product result=null ;
            try {
                for (Product p : allProducts) {
                    if (p.getCategoryId() == categoryId && p.getId() == productId) {
                        result = p;
                        break;
                    }
                }
                if(result==null) throw new NoResultException();
            }catch(NoResultException e){
                return Response.status(Response.Status.OK).entity("{}").build();
            }

            JsonObjectBuilder jsonProduct = Json.createObjectBuilder()
                    .add("productId",result.getId())
                    .add("name", result.getName())
                    .add("imageUrl", result.getImageUrl())
                    .add("categoryId", result.getCategoryId())
                    .add("description", result.getDescription())
                    .add("featured", result.getFeatured())
                    .add("available" , result.getAvailable())
                    .add("numOfItems" , result.getNumOfItems())
                    .add("price" , result.getPrice());
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).entity(jsonProduct.build().toString()).build();

        }catch (Exception e){
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        finally
        {

        }

    }
    private void counterInc(String name, String desc, Tag[] tags) {
        Metadata m = Metadata.builder()
                .withName(name)
                .withDescription(desc)
                .withType(MetricType.COUNTER)
                .build();
        Counter counter = registry.counter(m, tags);
        counter.inc();
    }

}

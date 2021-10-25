package com.oracle.fodlite.svc.basketService;

import com.oracle.fodlite.svc.basketService.model.BasketEntry;
import com.oracle.fodlite.svc.basketService.database.Database;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.webserver.ServerRequest;
import org.eclipse.microprofile.metrics.*;
//import org.eclipse.microprofile.metrics.Metric;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/basket")
@Traced
@ApplicationScoped
public class BasketResource{

    @Inject
    Metrics metrics;
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private Logger LOGGER = LoggerFactory.getLogger(BasketResource.class);
    @Context
    private ServerRequest serverRequest;

//    @Inject
//    @Metric(name = "basket_http_request_total",tags = {"method=get"}, absolute = true)
//    private Counter httpResponse;

    /**
     * Return a contents of basket.
     *
     * @return {@link Response}
     */
    @GET
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBasket() {
        LOGGER.info("GET /basket called");
        // Returns contents of basket
        try {
            LOGGER.info("GET /basket called :  before getResource");
            List<BasketEntry> result= Database.getItems();
            metrics.incrementDBHits();
            ObjectMapper mapper = new ObjectMapper();
            LOGGER.info("basket returned {}",mapper.writeValueAsString(result));
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON parsing exception");
            metrics.incrementStatusCodeCounter(500);
            return Response.serverError().entity("error get basket"+e.getLocalizedMessage()).build();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            metrics.incrementStatusCodeCounter(500);
            return Response.serverError().entity("error get basket"+e.getLocalizedMessage()).build();
        }finally{
        }
    }


    /**
     * inserts an item into the current basket
     *
     * @param basketEntry JSON containing the new item to add to basket
     * @return {@link Response}
     */
    @Path("/item")
    @POST
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToBasket(BasketEntry basketEntry) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LOGGER.info("Add to basket called with {} "+mapper.writeValueAsString(basketEntry));
        try {
            if (basketEntry.getProductId() == 0 || basketEntry.getQuantity() == null) {
                // throw error
                LOGGER.error("addToBasket unable to get product entry");   // NO SCS compliant but for debugging
                return Response.status(Response.Status.BAD_REQUEST).entity("basket entry must include quantity and productId").build();
            }
            metrics.incrementDBHits();
            List<BasketEntry> items=Database.getItems();
            for(BasketEntry be:items){
                if(be.getProductId()==basketEntry.getProductId()){
                    be.setQuantity(basketEntry.getQuantity());
                    return Response.ok().build();
                }
            }
            metrics.incrementDBHits();
            Database.getItems().add(basketEntry);
            LOGGER.info("Add to basket succeeded with {} "+mapper.writeValueAsString(basketEntry));
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).build();
        }
        catch (Exception e) {
            e.printStackTrace();// NO SCS compliant but for debugging
            LOGGER.error(e.getLocalizedMessage());   // NO SCS compliant but for debugging
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error add to basket, msg "+e.getLocalizedMessage().toString()).build();
        }
        finally {
        }
    }

    @DELETE
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    public Response deleteBasket() {
        LOGGER.info("Delete entire basket Called - ");
        try
        {
            metrics.incrementDBHits();
            Database.getItems().removeAll(Database.getItems());
            LOGGER.info("Delete basket succeeded");
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();// NO SCS compliant but for debugging
            LOGGER.error(e.getLocalizedMessage());   // NO SCS compliant but for debugging
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting basket"+e.getLocalizedMessage().toString()).build();
        }
        finally {
        }
    }

    @Path("/item/{id}")
    @DELETE

    public Response deleteFromBasket(@PathParam("id") long id) {
        LOGGER.info("Delete item in basket Called - {} ",id);
        try  {
            List<BasketEntry> items=Database.getItems();
            for(int i=0;i<items.size();i++){
                if(items.get(i).getProductId()==id){
                    BasketEntry be=items.get(i);
                    items.remove(i);
                    LOGGER.info("Delete item in basket succeed - {} ",id);
                    return Response.ok(be)
                            .build();
                }
            }
            LOGGER.info("Delete item in basket failed - {} ",id);
            return Response.status(Response.Status.NOT_FOUND).entity("Product ID not found in basket").build();
        }catch (Exception e)
        {
            e.printStackTrace();// NO SCS compliant but for debugging
            LOGGER.error(e.getLocalizedMessage());   // NO SCS compliant but for debugging
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting basket"+e.getLocalizedMessage().toString()).build();
        }
        finally {
        }
    }
}

/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oracle.fodlite.svc;

import java.io.IOException;

import java.net.MalformedURLException;

import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ThreadLocalRandom;

import com.oracle.fodlite.database.Database;
import com.oracle.fodlite.model.Order;
import com.oracle.fodlite.model.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.helidon.security.SecurityContext;
import io.helidon.webserver.ServerRequest;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.metrics.*;
import org.eclipse.microprofile.metrics.annotation.*;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 *
 * The message is returned as a JSON object.
 */

@Path("/order")
@Traced
@RequestScoped
public class OrderService {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    Random rand=new Random();

    @Context
    private ServerRequest serverRequest;


    //

//    @Inject
//    @Metric(name = "fodlite_response2xx_total",tags = {"service=ordersvc"}, absolute = true)
//    private Counter httpResponse2XX;
//
//    @Inject
//    @Metric(name = "fodlite_response5xx_total",tags = {"service=ordersvc"}, absolute = true)
//    private Counter httpResponse5XX;
//
//    @Inject
//    @Metric(name = "fodlite_http_requests_total",tags = {"service=ordersvc"}, absolute = true)
//    private Counter httpResponse;

    @Inject
    MetricRegistry registry;

    @Inject
    Metrics metrics;

    @Inject
    Config config;

//    private void httpResponse(Tag[] tags) {
//        Metadata m = Metadata.builder()
//                .withName("http_requests_total")
//                .withType(MetricType.COUNTER)
//                .build();
//        Counter counter = registry.counter(m,tags);
//        counter.inc();
//    }

//    private void histogramUpdate(String name, String desc, Tag[] tags, long value) {
//        Metadata m = Metadata.builder()
//                .withName(name)
//                .withDescription(desc)
//                .withType(MetricType.HISTOGRAM)
//                .build();
//        Histogram histogram = registry.histogram(m, tags);
//        histogram.update(value);
//    }

    @Inject
    private Database database;

    private boolean debug = false;

    @Path("/create")
    @POST
    @Timed(name = "fod_http_response_timer",tags = {"method=post"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder() {
//        histogramUpdate("fodlite_create","order service create histogram",null,rand.nextInt(1000));
//        sleepRandom();
        try {
            Order cart = retrieveCustomerBasket();
            if(cart==null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            emptyCustomerBasket();
            metrics.incrementStatusCodeCounter(200);
            return Response.status(Response.Status.OK).entity("successful:true").build();
        } catch (Exception ex) {
            metrics.incrementStatusCodeCounter(500);
            JsonObject msg = JSON.createObjectBuilder()
                .add("status", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .add("error", ex.getMessage())
                .build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        finally {
        }
    }

    /**
     *
     * @return {@link Response}
     */

    @Path("/{orderid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    public Response getOrder(@Context SecurityContext context, @PathParam ("orderid") long id) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("The input parameter %d\n", id);
            }
            LOGGER.info("Database was accessed");
            Order order = Database.getOrders().get(id);
            metrics.incrementDBHits();
            if (order != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("the Order returned: %s\n", order.toString());
                }
                metrics.incrementStatusCodeCounter(200);
                return Response.status(Response.Status.OK).entity(order).build();
            } else {
                LOGGER.info("Specified product not found!");
                metrics.incrementStatusCodeCounter(404);
                return Response.status(404).build();
            }
        } catch (Exception e) {
            LOGGER.error("Internal Server Error\n"+e);
            JsonObject msg = JSON.createObjectBuilder()
                .add("error", e.getMessage())
                .build();
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        finally {
        }
    }


    @Path("/orders")
    @GET
    @Timed(name = "fod_http_response_timer",tags = {"method=get"}, absolute = true,unit = MetricUnits.NANOSECONDS, reusable = true)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {

        try {
            LOGGER.info("Database was accessed");
            List<Order> orders =new ArrayList<>(Database.getOrders().values());
            metrics.incrementDBHits();
            if (orders != null && orders.size() > 0) {
                LOGGER.info("Number of Orders: " + orders.size());

                metrics.incrementStatusCodeCounter(200);
                return Response.status(Response.Status.OK).entity(orders).build();
            } else {
                LOGGER.info("Number of Orders: 0");
                JsonObject json = Json.createObjectBuilder()
                    .add("orders","[]")
                    .build();
                metrics.incrementStatusCodeCounter(200);
                return Response.status(Response.Status.OK).entity(json).build();
            }

        } catch (Exception e) {
            JsonObject msg = JSON.createObjectBuilder()
                .add("error", e.getMessage())
                .build();
            metrics.incrementStatusCodeCounter(500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        finally {
        }
    }

    private Order retrieveCustomerBasket() throws MalformedURLException, IOException,javax.ws.rs.NotFoundException,Exception {
        //Making custom span
//        Optional<SpanContext> sp_context=serverRequest.spanContext();
//        Span span=null;
//        if(sp_context.isPresent()) {
//            span = GlobalTracer.get()
//                    .buildSpan("oracle_orderSvc_retrieveBasket").asChildOf(sp_context.get()).start();
//            //logger.info("Using existing span for creating a child span");
//        } else {
//            span = GlobalTracer.get()
//                    .buildSpan("oracle_orderSvc_retrieveBasket").start();
//            //logger.info("No existing span - creating a new span.");
//        }
        String basketPath=config.getValue("basket-path",String.class);
        Client client = ClientBuilder.newClient();
        ObjectMapper objMapper = new ObjectMapper();
        JsonArray response;
        LOGGER.info("Getting items from the basket");
        try {
            if (!debug) {
                response = client.target(basketPath+"/basket")
                                .request(MediaType.APPLICATION_JSON)
                                .get(JsonArray.class);
            } else {
                response = Json.createArrayBuilder().add("{\"items\":[{\"productId\": 1,\"quantity\":5, \"productName\": \"shirt\"}]}")
                .build();
            }
            if(response.size()>0) {
                LOGGER.info("Recieved the items from the basket");
                LOGGER.info("Creating new order");
            }
            else LOGGER.warn("Basket is empty");
            Order order=new Order();
            for(JsonValue jo:response){
                String a=jo.toString();
                OrderItem orderItem=objMapper.readValue(a,OrderItem.class);
                order.addItems(orderItem);
            }
//            if(order.getItems()==null) return null;
            if(response.size()>0) LOGGER.info("Added {} items in the order",order.getItems().size());
            order.setOrderId(Database.getOrders().size()+1);
            LOGGER.info("Database was accessed");
            metrics.incrementDBHits();
//            System.out.println(order.getOrderId());
            // Check cart not empty
            if (order.getItems().size()==0)
            {
                return null;
            }

            Database.getOrders().put(order.getOrderId(),order);
            LOGGER.info("Database was accessed ");
            metrics.incrementDBHits();
            LOGGER.info("Order placed successfully");
            return order;
        }

        catch(javax.ws.rs.NotFoundException ex)
        {
            LOGGER.error("got NotFoundException when calling basket service");
            throw ex;

        }
        catch (Exception e)
        {
            MDC.put("Key", "Value");
            LOGGER.error("Internal Server Error\n",e);
            throw e;
        }
        finally {
//            span.finish();
        }

    }



    private boolean emptyCustomerBasket() throws MalformedURLException, IOException, javax.ws.rs.NotFoundException,Exception {
        Client client = ClientBuilder.newClient();
        ObjectMapper objMapper = new ObjectMapper();
        Response response;
        try {
            if (!debug) {
                response =
                        client.target("http://localhost:9002/basket")
                                .request(MediaType.APPLICATION_JSON)
                                .delete();
                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    LOGGER.error("Error deleting basket , got "+response.getStatus()+" error code");
                    throw new Exception("Error deleting basket , got "+response.getStatus()+" error code");
                }
            } else {
                LOGGER.info("basket successfully emptied");
                return true;
            }
            return true;
        }

        catch (Exception e)
        {
            LOGGER.error("Error deleting basket"+e.getLocalizedMessage());
            throw e;
        }
        finally {
        }
    }
    private void sleepRandom() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(500, 600));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        }
    }
}

package io.rhythm.examples.resources;

import io.rhythm.examples.bean.MessageFilterBean;
import io.rhythm.examples.model.*;
import io.rhythm.examples.model.Message;
import io.rhythm.examples.service.MessageService;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.util.GlobalTracer;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

@Path("/messages")
@Traced
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    @Inject
    private MessageService messageService;

    @Context
    UriInfo uriInfo;

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageResource.class.getName());
    private String prd_name="prd_name=erp";
    private String prd_svc="prd_app=expense";
    private String prd_app="prd_svc=create";

    @Context
    private io.helidon.webserver.ServerRequest serverRequest;

    @Counted(name = "messenger_message_listCount_total", absolute = true)
    @Metered(name = "messenger_messageListMeter", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Timed(name = "messenger_messageListimer", absolute = true, unit = MetricUnits.MILLISECONDS)
    @GET
    public Response getMessage(@BeanParam MessageFilterBean filter){
        Span span=serverRequest.tracer().activeSpan();
        //Logging in spans
        span.log(
                Map.of("hi","This is a log","key","Map can also be made")
        );
        filter.setStart(filter.getStart()-1);
        if(filter.getStart() >=0 && filter.getSize()>0) return Response.ok(messageService.demandMessages(filter.getStart(), filter.getSize()))
                .build();
        return Response.ok(messageService.getAllMessages())
                .build();
    }

    @GET
    @Path("/{mes}")
    @Counted(name = "messenger_messageCount", absolute = true)
    @Metered(name = "messenger_messageMeter", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Timed(name = "messenger_messageTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
    public Response getMessages(@PathParam("mes") long messageID) {

        Message message=messageService.getMessage(messageID);
        if(message!=null){
            LOGGER.info("Successful message retrieval");
            return Response.ok(message).build();
        }
        else {
            MDC.put("key","This is a custom key!");
            LOGGER.error("Message cannot be found!");
            return Response.status(404).build();
        }
    }

    @POST
    @Counted(name = "messenger_messageSaveCount", absolute = true)
    @Metered(name = "messenger_messageSaveMeter", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Timed(name = "messenger_messageSaveTimer", absolute = true, unit = MetricUnits.MILLISECONDS)

    public Response addMessage(Message message){
        messageService.addMessage(message);
        LOGGER.info("Successful call to save.");
        return Response.created(
                uriInfo.getBaseUriBuilder()
                        .path("/messages/{id}")
                        .build(message.getId())
        )
                .entity(message)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{messageId}")
    public Message putMessage(@PathParam("messageId") long messageId,Message message){
        message.setId(messageId);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Counted(name = "messenger_messageDeleteCount", absolute = true)
    @Metered(name = "messenger_messageDeleteMeter", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Timed(name = "messenger_messageDeleteTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Path("/{messageId}")
    public Response deleteMessage(@PathParam("messageId") long messageId) {
        Message message = messageService.removeMessage(messageId);
        if (message != null) {
            LOGGER.info("Successful call to delete.");
            return Response.ok(message)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .build();
    }

    @Path("/{mess}/comments")
    public CommentResource comments(){
        Span span=null;
        Optional<SpanContext> sp_context = serverRequest.spanContext();
        if(sp_context.isPresent()) {
            span = GlobalTracer.get()
                    .buildSpan("MessageResource").asChildOf(sp_context.get()).start();
            //logger.info("Using existing span for creating a child span");
        } else {
            span = GlobalTracer.get()
                    .buildSpan("spectra_spobsv_hello-spectra_delayedmessage").start();
            //logger.info("No existing span - creating a new span.");
        }
        try {
            Thread.sleep(1 + new Random().nextInt(10));
        } catch (InterruptedException e){
        }
        span.finish();
        return new CommentResource();
    }
}

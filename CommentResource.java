package io.rhythm.examples.resources;

import io.rhythm.examples.model.Comment;
import io.rhythm.examples.service.CommentService;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
//@Traced
@ApplicationScoped
public class CommentResource {
    private CommentService commentService=new CommentService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("messageId") long messageId,@Context UriInfo uriInfo) {
        //TODO Make custom spans!!
        if(commentService.getAllComments(messageId)!=null){
            return Response.created(uriInfo.getAbsolutePath())
                    .cookie(new NewCookie("name","get me"))
                    .entity(commentService.getAllComments(messageId))
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment postComment(@PathParam("messageId") long messageId, Comment comment){
        commentService.addComment(messageId,comment);
        return comment;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{commentId}")
    public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") int commentId){
        return commentService.getComment(messageId,commentId);
    }
}

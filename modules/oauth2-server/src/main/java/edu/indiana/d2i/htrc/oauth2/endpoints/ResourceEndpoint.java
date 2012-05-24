package edu.indiana.d2i.htrc.oauth2.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/res")
public class ResourceEndpoint {
    
    @GET
    @Produces("text/plain")
    public String testResource(){
        return "OAuth test resource";
    }
}

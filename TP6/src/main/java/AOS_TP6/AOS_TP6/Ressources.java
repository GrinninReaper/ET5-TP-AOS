package AOS_TP6.AOS_TP6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.annotation.*;

@Path("/bonjour")
public class Ressources {
	
	public Ressources() {
		
	}
	
	@POST
	@Produces("text/plain")
	@Path("/change")
	public String putHello(
			@FormParam("nom") String name){
		return "Name changed to "+ name;
		
	}
	
	@GET
	@Produces("text/plain")
	public String getHello(@QueryParam("nom") String name) {
		String rslt = "Hello "+ name;
		return rslt;
	}
	
	@GET
	@Produces({MediaType.TEXT_HTML})
	public String sayHello(@QueryParam("nom") String name)
	{
	   return "<html><body><h3>"+name + "</h3></body></html>";
	}
}

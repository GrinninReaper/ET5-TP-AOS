package resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;

import javax.net.ssl.SSLContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import serveur.APIList;
import serveur.Hello;

@Path("/data")
public class TotoGET {
	
	
	public static HashMap<Integer, Hello> map;
	static {
	    map = new HashMap<>();
	    map.put(0, new Hello("Reaper", System.currentTimeMillis()));
	    map.put(1, new Hello("Grinning", System.currentTimeMillis()));
	    map.put(2, new Hello("Kill", System.currentTimeMillis()));
	    map.put(3, new Hello("Them", System.currentTimeMillis()));
	    map.put(4, new Hello("All", System.currentTimeMillis()));
	}
	
	public String jaxbObjectToJSON(Hello hello)
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Hello.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           
            
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(hello, sw);
             
            //Verify XML Content
            return sw.toString();
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@GET
	@Produces("application/json")
	public String getHello(@QueryParam("nom") String name) {
		Hello hello = new Hello(name, System.currentTimeMillis());
		String rslt = jaxbObjectToJSON(hello);
		return rslt;
	}
	
	@Path("/ID")
	@GET 
	@Produces("application/json")
	public String getHelloID(@QueryParam("id") String id) {
		if(map.containsKey(Integer.valueOf(id))) {
			Hello hello = map.get(Integer.valueOf(id));
			String rslt = jaxbObjectToJSON(hello);
			return rslt;
		}
		return "Object with id " + id + " not found";
	}
	
	@Path("/ADD")
	@PUT
	@Produces("application/json")
	public String putHelloID(@PathParam("nom") String name) {
		Hello newHello = new Hello(name,System.currentTimeMillis());
		Integer newId = Collections.max(map.keySet()) + 1;
		map.put(newId, newHello);
		return jaxbObjectToJSON(newHello);
	}
	
	@Path("/CHANGE")
	@PUT
	@Produces("application/json")
	public String changeHelloID(@PathParam("nom") String name, @PathParam("id") String id) {
		if(map.containsKey(Integer.valueOf(id))) {
			String rslt = jaxbObjectToJSON(map.get(Integer.valueOf(id)));
			map.put(Integer.valueOf(id), new Hello(name, System.currentTimeMillis()));
			rslt += jaxbObjectToJSON(map.get(Integer.valueOf(id)));
			return rslt;
		}
		return "Object with id " + id + " not found";
	}
	
	@Path("/DEL")
	@DELETE
	@Produces("text/plain")
	public String delHelloID(@QueryParam("id") String id) {
		if(map.containsKey(Integer.valueOf(id))) {
			map.remove(Integer.valueOf(id));
			return "Deleted entry with id " + id;
		}
		return "No object with " + id + " not found";
	}
	
	@Path("/SWFilms")
	@GET
	@Produces("text/plain")
	public String getNumberOfFilms() throws IOException {
		String swapiRsltFile = "films.json";
		/*
		 * Extracting the data from swapi.co
		 */
		ClientConfig ccg = new ClientConfig();
		ccg.register(APIList.class);
		
		SslConfigurator sslConfig = SslConfigurator.newInstance()
		        .trustStoreFile("jssecacerts")
		        .trustStorePassword("changeit");

		SSLContext sslContext = sslConfig.createSSLContext();
				
		Client clientFilm = ClientBuilder.newBuilder().sslContext(sslContext).build();
		Response responseFilm = clientFilm.target("https://swapi.co/api/films").request().accept("application/json").get();
		InputStream filmStream = (InputStream) responseFilm.getEntity();
		/*
		 * Storing the data in file 
		 */
	    File targetFile = new File(swapiRsltFile);
	    targetFile.createNewFile();
	    OutputStream outStream = new FileOutputStream(targetFile);
	    byte[] buffer = new byte[8 * 1024];
	    int bytesRead;
	    while ((bytesRead = filmStream.read(buffer)) != -1) {
	    	outStream.write(buffer, 0, bytesRead);
	    }
		/*
		 * Extracting the wanted data from the saved file
		 */
		InputStream inJson = APIList.class.getResourceAsStream("/films.json");
		APIList list = new ObjectMapper().readValue(inJson, APIList.class);
		/*
		 * Display of the data
		 */
		return "There are " + list.count + " films.";
	}
	
	@Path("/SWPeople")
	@GET
	@Produces("text/plain")
	public String getNumberOfPeople() throws IOException {
		String swapiRsltFile = "people.json";
		/*
		 * Extracting the data from swapi.co
		 */
		ClientConfig ccg = new ClientConfig();
		ccg.register(APIList.class);
		SslConfigurator sslConfig = SslConfigurator.newInstance()
		        .trustStoreFile("jssecacerts")
		        .trustStorePassword("changeit");

		SSLContext sslContext = sslConfig.createSSLContext();
				
		Client clientPeople = ClientBuilder.newBuilder().sslContext(sslContext).build();
		Response responsePeople = clientPeople.target("https://swapi.co/api/people").request().accept("application/json").get();
		InputStream peopleStream = (InputStream) responsePeople.getEntity();
		/*
		 * Storing the data in file 
		 */
	    File targetFile = new File(swapiRsltFile);
	    targetFile.createNewFile();
	    OutputStream outStream = new FileOutputStream(targetFile);
	    byte[] buffer = new byte[8 * 1024];
	    int bytesRead;
	    while ((bytesRead = peopleStream.read(buffer)) != -1) {
	    	outStream.write(buffer, 0, bytesRead);
	    }
		/*
		 * Extracting the wanted data from the saved file
		 */
		InputStream inJson = APIList.class.getResourceAsStream("/people.json");
		APIList list = new ObjectMapper().readValue(inJson, APIList.class);
		/*
		 * Display of the data
		 */
		return "There are " + list.count + " people.";
	}
	
	/*
	@POST 	@Path("post")
	@Produces("application/json")
	public String postHello(@QueryParam("nom") String name) {
		Hello hello = new Hello(name, System.currentTimeMillis());
		return hello.jaxbObjectToXML();
	}
	*/
	
}

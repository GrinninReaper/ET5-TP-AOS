package serveur;

import java.util.Scanner;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import resources.TotoGET;

public class App {
	 public static void main(String[] args) {
	     JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	     sf.setResourceClasses(TotoGET.class);
	     sf.setProvider(new JacksonJaxbJsonProvider());
	     sf.setResourceProvider(
	    	 		TotoGET.class, 
	    	 		new SingletonResourceProvider(new TotoGET())
	    		 );
	    sf.setAddress("http://localhost:8000/");
	    sf.create();

	 	System.out.println("Saisir car+return pour stopper le serveur");	
	 	new Scanner(System.in).next();
	 
	 	System.out.println("Fin");
	 }
}


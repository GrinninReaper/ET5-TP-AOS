package AOS_TP6.AOS_TP6;


import java.util.Scanner;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

/**
 * Hello world!
 *
 */
public class App {
	 public static void main(String[] args) {
	     JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	     sf.setResourceClasses(Ressources.class);
	     sf.setResourceProvider(
	    	 		Ressources.class, 
	    	 		new SingletonResourceProvider(new Ressources())
	    		 );
	    sf.setAddress("http://localhost:9000/");
	    sf.create();

	 	System.out.println("Saisir car+return pour stopper le serveur");	
	 	new Scanner(System.in).next();
	 
	 	System.out.println("Fin");
	 }
}

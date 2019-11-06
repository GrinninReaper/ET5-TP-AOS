package serveur;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Hello {
	public String name;
	public long date;
	
	public Hello(String name, long date) {
		this.name = name;
		this.date = date;
	}
	
	public Hello() {
		this.name = "failure";
		this.date = 0000000000000000000;
	}

	public static void main(String args[]) {
		Hello hello = new Hello("Toto", System.currentTimeMillis());
		//String rslt = hello.jaxbObjectToJSON();
		//System.out.println(rslt);
	}

}

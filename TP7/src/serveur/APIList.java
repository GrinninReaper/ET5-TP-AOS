package serveur;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class APIList {

	public long count;
	public String next;
	public String previous;
	public Object results;
	
	public APIList() {
		this.count = 0;
		this.next = null;
		this.previous = null;
		this.results = null;
	}
}

package my.example.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class User {
 
private int id;
private String firstName;
private String lastName;

public void setId(int id){
	this.id = id;
}
public int getId(){
	return id;
}

public void setFirstName(String fName){
	this.firstName = fName;
}
public String getFirstName(){
	return firstName;
}

public void setLastName(String lName){
	this.lastName = lName;
}
public String getLastName(){
	return lastName;
}

}
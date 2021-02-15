package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ContactManager {
	
	private ArrayList<Person> contacts;
	
	public ContactManager() {
		contacts= new ArrayList<Person>();
	}
	
	public void addContact(String na, String em) {
		Person person=new Person(na, em);
		contacts.add(person);
		int numb=getContacts().size();
    	person.setNumb(numb);
	}

	public ArrayList<Person> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Person> contacts) {
		this.contacts = contacts;
	}
	
	public void importContacts(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
	    String line = br.readLine();
	    while(line!=null){
	      String[] parts = line.split(";");
	      addContact(parts[0],parts[1]);
	      line = br.readLine();
	    }
	    br.close();
	}
	
	public void exportContacts(String fileName) throws FileNotFoundException{
	    PrintWriter pw = new PrintWriter(fileName);

	    for(int i=0;i<contacts.size();i++){
	      Person myContact = contacts.get(i);
	      pw.println(myContact.getName()+";"+myContact.getEmail());
	    }

	    pw.close();
	  }
}

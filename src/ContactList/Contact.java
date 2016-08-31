/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactList;

/**
 *
 * @author Orion
 */
public class Contact implements java.io.Serializable {
    String name;
    String phoneNumber;
    String emailAddress;
    public Contact(String name, String phoneNumber, String emailAddress){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
    
    public Contact(String name){
        this(name, null, null);
    }
    public String getName() {return this.name;}
    public String getPhoneNumber() {return this.phoneNumber;}
    public String getEmailAddress() {return this.emailAddress;}
    public void setName(String newName) {this.name = newName;} 
    public void setPhoneNumber(String newPhoneNumber){this.phoneNumber = newPhoneNumber;}
    public void setEmailAddress(String newEmailAddress){this.emailAddress = newEmailAddress;}
    @Override
    public String toString(){
        return String.format("[Name:%s] (Phone:%s) <Email:%s>" + name + phoneNumber + emailAddress);
    }
    @Override
    public boolean equals(Object other){
       boolean result = false;
       if(other != null){
           if(this.getClass() == other.getClass()){
               Contact that = (Contact)other;
               if(this.name.equalsIgnoreCase(that.name))
                   result = true;
           }
       }
        return result;
    }
    
        
}
    
        
    
   
        
    

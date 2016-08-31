/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Orion
 */
public class ContactList extends Application {
    //Create new file for data
    public static final File file = new File("ContactList.ser");
    
    //Create Array list for Contact Objects
    ArrayList<Contact> ContactList = new ArrayList<>();
    public int index ;
    private TextField txtName;
    private TextField txtPhone;
    private TextField txtEmail;
    private TextField txtStatus;
    
   
    public ContactList() throws IOException{
       if(file.exists()){
           try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                   ){
                   this.ContactList = (ArrayList<Contact>)ois.readObject();
                   this.index = 0;
           }catch(IOException ex){
               System.err.println(ex.getMessage());
               System.exit(0);
           }
           catch(ClassNotFoundException ex){
               System.err.println(ex.getMessage());
               System.exit(1);
           }
       }// if exists
       else{
           this.ContactList = new ArrayList<>();
       }
    }// constructor
       
    private void firstStart(){
          if( ! ContactList.isEmpty()){
            txtName.setText(ContactList.get(0).getName());
            txtPhone.setText(ContactList.get(0).getPhoneNumber());
            txtEmail.setText(ContactList.get(0).getEmailAddress());
          }
    }
    
    private void Add() throws IOException {
        if(txtName.getText().equals("") && txtPhone.getText().equals("") && txtEmail.getText().equals("")){
            status("Fields are empty.");
        }else{
            //Get fields
            String name = txtName.getText();
                txtName.setText(String.format("%s", name));
            String phone = txtPhone.getText();
                txtPhone.setText(String.format("%s", phone));
            String email = txtEmail.getText();
                txtEmail.setText(String.format("%s", email));
            Contact contact = new Contact(name, phone, email);
            //Add fields to Array
            ContactList.add(contact);
            txtStatus.clear();
            
        }
    }
    
    private void Remove(){
        if(!ContactList.isEmpty()){
            ContactList.remove(index);
            txtName.clear();
            txtPhone.clear();
            txtEmail.clear();
            index--;
        }
    }
    
    private void Previous() throws IOException, ClassNotFoundException, ArrayIndexOutOfBoundsException {
        try{
          if(index > 0)
              index--;
        txtName.setText(ContactList.get(index).getName());
        txtPhone.setText(ContactList.get(index).getPhoneNumber());
        txtEmail.setText(ContactList.get(index).getEmailAddress());
        if(index == 0)
            txtStatus.setText("You are at the begining of the list.");
        else
            txtStatus.clear();
        }
        catch(ArrayIndexOutOfBoundsException ex) {
            status("Array is empty.");
        }
        catch(IndexOutOfBoundsException ex){
            status("File has not been created.");
        }
    }
         
    private void Next()throws IOException, ClassNotFoundException, ArrayIndexOutOfBoundsException {
        try {
            if(index < (ContactList.size()) - 1)
                index++;
            txtName.setText(ContactList.get(index).getName());
            txtPhone.setText(ContactList.get(index).getPhoneNumber());
            txtEmail.setText(ContactList.get(index).getEmailAddress());
            if(index == (ContactList.size()) - 1)
                txtStatus.setText("You are at the end of the list.");
            else
                txtStatus.clear();
        }
        catch(ArrayIndexOutOfBoundsException ex) {
            status("Array is empty.");
        }
        catch(IndexOutOfBoundsException ex){
            status("File has not been created.");
        }
    }
   
    private void Clear(){
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtStatus.clear();
    }
    
    private void Edit() throws ArrayIndexOutOfBoundsException {
        try {
            ContactList.remove(index);
                String name = txtName.getText();
            txtName.setText(String.format("%s", name));
                String phone = txtPhone.getText();
            txtPhone.setText(String.format("%s", phone));
                String email = txtEmail.getText();
            txtEmail.setText(String.format("%s", email));
            Contact contact = new Contact(name, phone, email);
            //Add fields to Array
            ContactList.add(index, contact);
        }
        catch(ArrayIndexOutOfBoundsException ex){
            status("Fields are empty.");
        }
        catch(IndexOutOfBoundsException ex){
            status("File has not been created.");
        }
    }
    
    private void Search(){
        for(int i = 0; i < ContactList.size(); i++)
            if(ContactList.get(i).getName().equalsIgnoreCase(txtName.getText())){
                txtPhone.setText(ContactList.get(i).getPhoneNumber());
                txtEmail.setText(ContactList.get(i).getEmailAddress());
                txtName.setText(ContactList.get(i).getName());
                status("Found Person.");
            } else {
                status("Not Found");
            }
    }
    
    private void saveAndQuit() throws FileNotFoundException, IOException {
            status("Saving");
            try(FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                    ){ 
                oos.writeObject(this.ContactList);
            }catch(IOException ex){
                System.err.println(ex.getMessage());
                System.exit(2);
            }
            System.exit(3);
        }
                    
    private void status(String msg){
            this.txtStatus.setText(msg);
        }
    
    @Override
    public void start(Stage primaryStage) {
        Label lblName = new Label("Name:");
        Label lblPhone = new Label("Phone:");
        Label lblEmail = new Label("E-Mail:");
        Label lblStatus = new Label("Status:");
        txtName = new TextField();
            txtName.setAlignment(Pos.CENTER_LEFT);
        txtPhone = new TextField();
            txtPhone.setAlignment(Pos.CENTER_LEFT);
        txtEmail = new TextField();
            txtEmail.setAlignment(Pos.CENTER_LEFT);
        txtStatus = new TextField();
             txtStatus.setAlignment(Pos.CENTER_LEFT);
             txtStatus.setEditable(false);
        
        Button btnPrevious = new Button("<< Previous");
            btnPrevious.setOnAction(event -> {
            try {
                Previous();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
        Button btnSearch = new Button("Search?");
            btnSearch.setOnAction(event -> Search());
            
        Button btnNext = new Button("Next >>");
            btnNext.setOnAction(event -> {
            try {
                Next();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
        Button btnAdd = new Button("Add +");
            btnAdd.setOnAction(event -> {
            try {
                Add();
            } catch (IOException ex) {
                Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
        Button btnEdit = new Button("Edit *");
            btnEdit.setOnAction(event -> Edit());
            
        Button btnRemove = new Button("Remove -");
            btnRemove.setOnAction(event -> this.Remove());
            
        Button btnClear = new Button("Clear 0");
            btnClear.setOnAction(event -> Clear());
            
        Button btnQuit = new Button("Quit X");
            btnQuit.setOnAction(event -> {
            try {
                this.saveAndQuit();
            } catch (IOException ex) {
                Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
        GridPane textPane = new GridPane();
            textPane.setPadding(new Insets(10));
            textPane.setHgap(5);
            textPane.setVgap(5);
            ColumnConstraints cco = new ColumnConstraints(100);
                cco.setHalignment(HPos.RIGHT);
            textPane.getColumnConstraints().add(cco);
            textPane.addRow(0, lblName, txtName);
            textPane.addRow(1, lblPhone, txtPhone);
            textPane.addRow(2, lblEmail, txtEmail);
            textPane.addRow(3, lblStatus, txtStatus);
            
            FlowPane buttonPane = new FlowPane();
                buttonPane.setPadding(new Insets(11));
                buttonPane.setHgap(5);
                buttonPane.setVgap(10);
                buttonPane.setAlignment(Pos.BOTTOM_CENTER);
                buttonPane.getChildren().addAll(btnPrevious, btnSearch, btnNext);
                buttonPane.getChildren().addAll(btnAdd, btnEdit, btnRemove);
                buttonPane.getChildren().addAll(btnClear, btnQuit);
               
            BorderPane root = new BorderPane();
                root.setCenter(textPane);
                root.setBottom(buttonPane);
         
        Scene scene = new Scene(root, 250, 250);
        firstStart();
        primaryStage.setTitle("Contact List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {
        launch(args);
         }
}
      
        
      
      
    

 

    





















































/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd510.jgotorbl.test;

import edu.iit.sat.itmd510.jgotorbl.fp.dao.DAOException;
import edu.iit.sat.itmd510.jgotorbl.fp.model.Book;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaime
 */
public class BookCreateFormController implements Initializable {
    
    private Book book;
    private Stage dialogStage;
     
    @FXML
    private TextField nameField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField idField;
    @FXML
    private TextField priceField;

    /*
    * @return the stage
    */
    public Stage getDialogStage() {
        return dialogStage;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    /*
    * @return the book
    */
    public Book getBook() {
        return book;
    }
    
    /*
    * sets the book
    */
    public void setBook(Book book) {
        this.book = book;
    }
    
    /*
    * sets the book fields with the values posted in the attributes and closes the stage.
    */
    @FXML
    private void handleSaveButton(ActionEvent event) throws DAOException, ParseException {
        // some validation should be performed here       
        book.setId(Integer.parseInt(idField.getText()));
        book.setName(nameField.getText());
        book.setAuthor(authorField.getText());
        book.setPrice(Double.parseDouble(priceField.getText()));
         
        // if successful, we should indicate that here
        // we can retrieve the validated and user-entered object from getEmployee()
        
        
        dialogStage.close();
    }
    
    /*
    * closes the stage
    */
    @FXML
    private void handleCancelButton(ActionEvent event) {
        dialogStage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

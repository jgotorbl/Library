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
public class BookUpdateFormController implements Initializable {
    
    Book book;
    private Stage dialogStage;
    
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField priceField;
    
    /*
    * Returns the dialog stage
    */
    public Stage getDialogStage() {
        return dialogStage;
    }
    
    /*
    * modifies the dialog stage
    */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /*
    * modifies the textFields of the FXML stage
    */
    public void setTextFields(){
        idField.setText(Integer.toString(book.getId()));
        nameField.setText(book.getName());
        authorField.setText(book.getAuthor());
        priceField.setText(Double.toString(book.getPrice()));
    };
    
    /*
    * returns the book passed
    */
    public Book getBook() {
        return book;
    }
    
    /*
    * set the book passed
    */
    public void setBook(Book book) {
        this.book = book;
    }
    
    /*
    * Called when save is clicked
    * Updates the books attributes with the values in the text fields and closes the stage
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
    * close the stage
    */
    @FXML
    private void handleCancelButton(ActionEvent event) {
        dialogStage.close();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}

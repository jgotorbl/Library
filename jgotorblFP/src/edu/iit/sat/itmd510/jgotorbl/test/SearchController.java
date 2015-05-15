/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd510.jgotorbl.test;

import edu.iit.sat.itmd510.jgotorbl.fp.dao.BookDAO;
import edu.iit.sat.itmd510.jgotorbl.fp.dao.BookDAOFactory;
import edu.iit.sat.itmd510.jgotorbl.fp.dao.DAOException;
import edu.iit.sat.itmd510.jgotorbl.fp.model.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaime
 */
public class SearchController implements Initializable {
    
   
    Book book;
    private int listsize;
    private Stage dialogStage;
    
    @FXML
    private TextField idField;
    
    @FXML
    private Label idLabel;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label authorLabel;
    
    @FXML
    private Label priceLabel;
    
    
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
    * update the labels in the new stage with the values of the book with the id form the text field
    * if the id does not match any of the books, an alert pops up onto the screen.
    */
    @FXML
    public void handleSearchButton(ActionEvent event) throws DAOException, IOException{
        try(BookDAO dao = BookDAOFactory.createBookDAO()){
            book = dao.findById(Integer.parseInt(idField.getText()));
            if(book != null){
                idLabel.setText(Integer.toString(book.getId()));
                nameLabel.setText(book.getName());
                authorLabel.setText(book.getAuthor());
                priceLabel.setText(Double.toString(book.getPrice()));
            }else{
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Problem");
                alert.setHeaderText("No books found with that id");
                alert.setContentText("Please enter another id.");
                alert.showAndWait();
            } 
                
        }catch(Exception e){System.out.println(""+e.getMessage());}   
    }
    
    /*
    * closes the stage
    */
    @FXML
    public void handleExitButton(ActionEvent event){
        dialogStage.close();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
    
}

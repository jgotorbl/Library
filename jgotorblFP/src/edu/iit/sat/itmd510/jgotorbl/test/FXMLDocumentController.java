/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd510.jgotorbl.test;

import edu.iit.sat.itmd510.jgotorbl.fp.model.Book;
import edu.iit.sat.itmd510.jgotorbl.fp.dao.DAOException;
import edu.iit.sat.itmd510.jgotorbl.fp.dao.BookDAO;
import edu.iit.sat.itmd510.jgotorbl.fp.dao.BookDAOFactory;
import java.io.IOException;
import java.util.Optional;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jaime
 */
public class FXMLDocumentController implements Initializable {
    
    private ObservableList<Book> books;
    
     private Stage primaryStage;

    @FXML
    private TableView<Book> bksTable;
    
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label priceLabel;
    
    public ObservableList<Book> getEmployees() {
        return books;
    }
    
    /*
    * Set the items of the observable list of books
    */
    public void setBooks(ObservableList<Book> books) {
        this.books = books;
        //if (books != null) {
        //    System.out.println(books.toString());
        //}
        bksTable.setItems(books);
    }
    
    /*
    * getter of the primary stage
    */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    /*
    * setter of the primary stage
    */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    } 
    
    /*
    * Handles the Create Button.
    * Creates a new stage with a bookCreateForm form with a new controller. 
    * Modifies the books observable list. 
    * Modifies the MySQL database by accessing to the BookDAOFactory and calling the create method.
    */
    @FXML
    private void handleNewButtonAction(ActionEvent event) throws IOException, DAOException {
        System.out.println("You clicked new button");
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCreateForm.fxml"));
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Book");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        // Set the new employee into the controller.
        Book bk = new Book();
        BookCreateFormController controller = loader.getController();
        controller.setBook(bk);
        controller.setDialogStage(dialogStage);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        try (BookDAO dao = BookDAOFactory.createBookDAO()) {
            dao.add(controller.getBook());
            books.add(controller.getBook());
        }catch(Exception e){System.out.println(e);}
    }
    
    /*
    * Handles the Update Button.
    * Creates a new stage with a bookUpdateForm form with a new controller. 
    * Modifies the books observable list.
    * Modifies the MySQL database by accessing to the BookDAOFactory and calling the update method.
    * An alert pops up onto the screen if no book from the tablee has been selected when clicking the button
   */
    @FXML
    private void handleEditButtonAction(ActionEvent event) throws IOException{
        System.out.println("You clicked edit button");
        int selectedIndex = bksTable.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if(selectedIndex >=0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookUpdateForm.fxml"));
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Book");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            // Set the new employee into the controller.
            Book bk = new Book();
            bk = bksTable.getSelectionModel().getSelectedItem();
            BookUpdateFormController controller = loader.getController();
            controller.setBook(bk);
            controller.setTextFields();
            controller.setDialogStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            try (BookDAO dao = BookDAOFactory.createBookDAO()) {
                dao.update(controller.getBook());
                books.set(selectedIndex,controller.getBook());
            }catch(Exception e){System.out.println(e);}
        }else{
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("Problem");
            alert.setHeaderText("No Book Selected");
            alert.setContentText("Please select a book from the table.");
            alert.showAndWait();
        }
    }
    

    /*
    * Handles the Delete Button.
    * Modifies the table by removing the selected index form the table. 
    * Modifies the MySQL database by accessing to the BookDAOFactory and calling the detete method.
.   */
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) throws DAOException {
        System.out.println("You clicked delete button");
        int selectedIndex = bksTable.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Are you sure you want to delete " + bksTable.getItems().get(selectedIndex).getId() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try(BookDAO dao = BookDAOFactory.createBookDAO()){
                    System.out.println("Deleting " + bksTable.getItems().get(selectedIndex).getId());
                    dao.delete(bksTable.getItems().get(selectedIndex).getId());
                    bksTable.getItems().remove(selectedIndex);
                }catch(Exception e){System.out.println(e.getMessage());}
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("Problem");
            alert.setHeaderText("No Book Selected");
            alert.setContentText("Please select a book from the table.");
            alert.showAndWait();
        }
    }
    
    /*
    * Handles the Delete Button.
    * Creates a new stage with a searchBook form and with a new controller.  
.   */
    @FXML
    private void handleReadButtonAction(ActionEvent event) throws DAOException, IOException {
        System.out.println("You clicked the 'Search' button");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchBook.fxml"));
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Search Book");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        
        SearchController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }
    /*
    * Change the labels everytime the observable list is modified.
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bksTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {

            @Override
            public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
                if(newValue != null){
                    idLabel.setText(Integer.toString(newValue.getId()));
                    nameLabel.setText(newValue.getName());
                    authorLabel.setText(newValue.getAuthor());
                    priceLabel.setText(Double.toString(newValue.getPrice()));
                }else{
                    idLabel.setText(Integer.toString(oldValue.getId()));
                    nameLabel.setText(oldValue.getName());
                    authorLabel.setText(oldValue.getAuthor());
                    priceLabel.setText(Double.toString(oldValue.getPrice()));
                }
                
                

            }
        });
    }

}

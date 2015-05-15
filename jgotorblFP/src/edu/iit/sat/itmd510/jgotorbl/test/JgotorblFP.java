/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd510.jgotorbl.test;

import edu.iit.sat.itmd510.jgotorbl.fp.dao.BookDAO;
import edu.iit.sat.itmd510.jgotorbl.fp.dao.BookDAOFactory;
import edu.iit.sat.itmd510.jgotorbl.fp.model.Book;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 *
 * @author Jaime
 */
public class JgotorblFP extends Application {
    
    BooleanProperty ready = new SimpleBooleanProperty(false);
    InitializationTask task = new InitializationTask();
    
    /*
    * Carries out the Preloader progress
    * Initilizes a for loop that sets the value of the preloader progress. 
    * It is used inside a Thread that is initialized at the beginning of the program
    * @return an observable arrylist of books
    */
    private class InitializationTask extends Task<ObservableList<Book>> {
        @Override
        protected ObservableList<Book> call() throws Exception {
            try (BookDAO dao = BookDAOFactory.createBookDAO()) {
                // init stuff that takes a long time
                int max = 10;
                for (int i = 1; i <= max; i++) {
                    
                    Thread.sleep(200);
                    
                    // Send progress to preloader
                    notifyPreloader(new Preloader.ProgressNotification(((double) i) / max));
                    
                }
                // After init is ready, the app is ready to be shown
                // Do this before hiding the preloader stage to prevent the
                // app from exiting prematurely
                ready.setValue(Boolean.TRUE);
                notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
                return FXCollections.observableArrayList(dao.getAllBooks());
            }
        }
    }
    
    
    /*
    * Implements the task of initializing the program.
    * it has two listeners that initialize the main FXML page, the main controller
    * and show the stage 
    */
    @Override
    public void start(Stage stage) throws Exception {
        new Thread(task).start();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("displayAllBooks.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // when the initialization task is done, set the obs list in the controller
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ObservableList<Book> books = task.getValue();
                FXMLDocumentController controller = loader.getController();
                controller.setBooks(books);
                controller.setPrimaryStage(stage);
            }
        });
        
        //stage.show();
        
        // when the ready flag is set, show the stage
        ready.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (Boolean.TRUE.equals(newValue)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // show the stage
                            stage.show();
                        }
                    });
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

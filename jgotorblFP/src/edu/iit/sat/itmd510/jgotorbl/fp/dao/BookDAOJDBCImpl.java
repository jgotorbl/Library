/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd510.jgotorbl.fp.dao;

import edu.iit.sat.itmd510.jgotorbl.fp.model.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 *
 * @author jaime
 */
public class BookDAOJDBCImpl implements BookDAO{

    private Connection c = null;
    
    /*
    * Constructor. Initiztes database connection
    */
    public BookDAOJDBCImpl(){
        String url = "jdbc:mysql://localhost:3306/itmd510";
        String user = "itmd510";
        String password = "itmd510";     
        try{
            c = DriverManager.getConnection(url, user, password);
        }catch(SQLException e)
        {
            System.out.println(""+e.getMessage());
            System.exit(-1);
        }
    }
    
   /*
    * Adds a book to book table
    * @throw SQLException if there is an error while adding a book 
    */
    @Override
    public void add(Book b) throws DAOException {
        try(Statement st = c.createStatement()){
            String query ="INSERT INTO Book VALUES(" + b.getId() + ", '" +
                            b.getName()+"', '"+ b.getAuthor()+ "', " + b.getPrice() + ")";
            st.executeUpdate(query);
              
            
        }catch(SQLException e){
            throw new DAOException("Exception in adding book: "+e.getMessage());
        }   
    }

    /*
    * Updates a book from the book table
    * @throw DAOException if a book with that id not exists
    * @throw SQLException if there is an error while updating a book
    */
    @Override
    public void update(Book b) throws DAOException {
        try(Statement st = c.createStatement()){
            String query = "UPDATE Book SET NAME='"+ b.getName()+"', "+ 
                           "AUTHOR='"+ b.getAuthor()+
                           "', "+ "PRICE="+ b.getPrice()+"WHERE ID="+b.getId();
            st.executeUpdate(query);
            if (st.executeUpdate(query) != 1) {
                throw new DAOException("Error deleting employee");
            }
        }
        catch(SQLException e){
            throw new DAOException("Error in update: "+ e.getMessage());
        }
    }
    /*
    * Deletes an employee from the employees table
    * @throw DAOException  if a book with that id not exists
    */
    @Override
    public void delete(int id) throws DAOException {
        Book b = findById(id);
        if(b == null) throw new DAOException("Book "+b.getId()+" does not exixst to be deleted");
        try(Statement st = c.createStatement()){
            String query = "DELETE FROM Book WHERE ID="+id;
            st.executeUpdate(query);
            
        }catch(Exception e){
            throw new DAOException("Error in deleting book:" + e.getMessage());
        }
    }
    
    /*
    * Finds a book in the books arrray
    * @throw DAOException  if a book with that id not exists
    * @throw DAOException if there is an error while finding a book
    */
    @Override
    public Book findById(int id) throws DAOException {
        try(Statement st = c.createStatement()){
            String query = "SELECT * FROM Book WHERE ID="+id;
            ResultSet result = st.executeQuery(query);
            
            if(!result.next()){
              return null;  
            }
            return (new Book(result.getInt("ID"), 
                    result.getString("NAME"), result.getString("AUTHOR"), 
                    result.getDouble("PRICE")));
        }catch(SQLException e){
            throw new DAOException("No books with that ID found");
        }
        
    }
    /*
    * Returns an array of all the books in the table.
    * @return an array of all the books
    * @throw a DAO exception if there is an error while adding a book
    */
    @Override
    public Book[] getAllBooks() throws DAOException {
        try(Statement st = c.createStatement()){
            String query = "SELECT * FROM BOOK";
            ResultSet result = st.executeQuery(query);
            ArrayList <Book> books = new ArrayList<>();
            while(result.next()){
                books.add(new Book(result.getInt("ID"),result.getString("NAME"),
                        result.getString("AUTHOR"),result.getDouble("PRICE")));
            }
            
            return books.toArray(new Book[0]);
        }
        catch(SQLException e){
            throw new DAOException("Error adding book");
        }
    }

    @Override
    public void close() throws Exception {
        try{
            c.close();
        }
        catch(SQLException e){
            throw new DAOException("Error closing connection: "+ e.getMessage()); 
       }
    }
    
}

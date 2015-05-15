/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd510.jgotorbl.fp.model;

/**
 *
 * @author Jaime
 */
public class Book {
    private int id;
    private String name;
    private double price;
    private String author;
    /*
    * constructor
    * @param id of the book
    * @param name of the book
    * @param author of the book
    * @param price of the book
    */
    public Book(int id, String name, String author, double price){
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
    }
    
    /*
    * constructor
    */
    public Book(){
    }
    
    /*
    * @return id
    */
    public int getId(){
        return id;
    }
    /*
    * @return name
    */
    public String getName(){
        return name;
    }
    
    /*
    * @return author
    */
    public String getAuthor(){
        return author;
    }
    
    /*
    * @return price
    */
    public double getPrice(){
        return price;
    }
    
    /*
    * @param id of the book
    */
    public void setId(int id){
        this.id = id;
    }
    
    /*
    * @param nname of the book
    */
    public void setName(String name){
        this.name = name;
    }
    
    /*
    * @param author of the book
    */
    public void setAuthor(String author){
        this.author = author;
    }
    
    /*
    * @param price of the book
    */
    public void setPrice(double price){
        this.price = price;
    }
    
    /*
    * @return String with the book details
    */
    @Override
    public String toString(){
        return "Book ID:   " + getId() + "\n" +
            "Book Name: " + getName() + "\n" +
            "Book Author: " + getAuthor() + "\n" +    
            "Book Price: " + getPrice()+ "\n";
    }
}

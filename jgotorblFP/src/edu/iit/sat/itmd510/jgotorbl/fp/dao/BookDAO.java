package edu.iit.sat.itmd510.jgotorbl.fp.dao;

import edu.iit.sat.itmd510.jgotorbl.fp.model.Book;
import java.util.ArrayList;

public interface BookDAO extends AutoCloseable {

    public void add(Book book) throws DAOException;

    public void update(Book book) throws DAOException;

    public void delete(int id) throws DAOException;

    public Book findById(int id) throws DAOException;

    
    public Book[]  getAllBooks() throws DAOException;
    
}
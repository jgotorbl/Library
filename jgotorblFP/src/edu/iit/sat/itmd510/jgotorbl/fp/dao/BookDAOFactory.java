package edu.iit.sat.itmd510.jgotorbl.fp.dao;

public class BookDAOFactory {
    
    /*
    * @return an EmployeeDAOJDBCImpl object
    */
    public static BookDAO createBookDAO() {
        return new BookDAOJDBCImpl();
    }
}
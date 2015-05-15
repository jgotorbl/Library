package edu.iit.sat.itmd510.jgotorbl.fp.dao;

public class DAOException extends Exception {
    /*
    * creates an object of the exception class
    */
    public DAOException() {
        super();
    }
    
   /*
    * creates an object of the exception class
    * @param String message messsage of the excepion 
    */    
    public DAOException(String message) {
        super(message);
    }

    /*
    * @param Throwable cause cause of the exception
    * creates an object of the exception class
    */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /*
    * creates an object of the exception class
    * @param Throwable cause cause of the exception
    * @param String message messsage of the excepion
    */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
package com.iptv_comm.db.dao.com.iptv_com.db.exceptions;

/**
 * A general class for DB related exceptions.
 * More specific exceptions should be extended from this class.
 */
public class DBException extends Exception{
    public DBException() {
        super();
    }
    public DBException(String message) {
        super(message);
    }
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
    public DBException(Throwable cause) {
        super(cause);
    }

}

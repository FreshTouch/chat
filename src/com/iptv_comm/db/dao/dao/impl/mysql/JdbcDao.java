package com.iptv_comm.db.dao.dao.impl.mysql;

import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.Properties;

/**
 * Created by user on 7/5/16.
 */
public class JdbcDao {
//    protected Connection con=null;

    protected Connection getConnection() throws DBException {

            Properties props = new Properties();
            FileInputStream fis = null;
            Connection con=null;
            try {
                fis = new FileInputStream("//home/misha/db.properties");
                props.load(fis);

                // load the Driver Class
                Class.forName(props.getProperty("DB_DRIVER_CLASS"));

                // create the connection now
                con = DriverManager.getConnection(props.getProperty("DB_URL"),
                        props.getProperty("DB_USERNAME"),
                        props.getProperty("DB_PASSWORD"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new DBException(e);
            } catch (SQLException e) {
                throw new DBException(e);
            } catch (ClassNotFoundException e) {
                throw new DBException(e);
            }

        return con;
    }

    protected void closeConnection(Connection con) throws DBException{
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
    }

    protected void closeStatement(Statement stmt) throws DBException{
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
    }

    public static void main(String[] args){
        JdbcDao jd=new JdbcDao();
        Connection con=null;
        try {
            con=jd.getConnection();
            System.out.println(con);
        } catch (DBException e) {
            e.printStackTrace();
        } finally{
            try {
                jd.closeConnection(con);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

    }
}

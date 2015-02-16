/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.infsci2710;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kolobj
 */
public class WebJdbc {

    private String userName = "root";
    private String password = "root";

    private String driver = "com.mysql.jdbc.Driver";
    private String connectionString = "jdbc:mysql://localhost:8889/university_2710?zeroDateTimeBehavior=convertToNull";

    public Connection getConnection() {
        try {
            Class.forName(driver);
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.userName);
            connectionProps.put("password", this.password);
            Connection conn = DriverManager.getConnection(connectionString, connectionProps);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(WebJdbc.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List getDepartmentSalaries() throws SQLException {
        List result;
        Connection conn = getConnection();
        String sql = "select dept_name, avg(salary) as avg_salary from instructor group by dept_name";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rset = stmt.executeQuery();
        result = resultSetToArrayList(rset);
        
        if (null != stmt)
            stmt.close();

        if (null != conn)
            conn.close();

        return result;
    }

    public List read(String orderBy, String order, int limit, int offset) throws SQLException {
        List result;
        Connection conn = getConnection();
        String sql = String.format("select * from instructor order by %s %s limit %d offset %d",
                orderBy,
                order,
                limit,
                offset);
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        
        ResultSet rset = stmt.executeQuery();
        result = resultSetToArrayList(rset);
        
        if (null != stmt)
            stmt.close();

        if (null != conn)
            conn.close();

        return result;
    }

    public boolean insert(String id, String name, String dept_name, double salary) throws SQLException {
        int rowsAffected = 0;
        Connection conn = getConnection();
        String sql = "insert into instructor values(?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.setString(2, name);
        stmt.setString(3, dept_name);
        stmt.setDouble(4, salary);

        rowsAffected = stmt.executeUpdate();

        if ( null != stmt )
            stmt.close();

        if ( null != conn )
            conn.close();

        return rowsAffected == 1;
    }
    
    public boolean delete(String id) throws SQLException {
        int rowsAffected = 0;
        Connection conn = getConnection();
        String sql = "delete from instructor where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);

        rowsAffected = stmt.executeUpdate();

        if ( null != stmt )
            stmt.close();

        if ( null != conn )
            conn.close();

        return rowsAffected == 1;
    }
    
    public boolean update(String id, String name, String dept_name, double salary) throws SQLException {
        int rowsAffected = 0;
        Connection conn = getConnection();
        String sql = "update instructor set name=?, dept_name=?, salary=? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, dept_name);
        stmt.setDouble(3, salary);
        stmt.setString(4, id);

        rowsAffected = stmt.executeUpdate();

        if ( null != stmt )
            stmt.close();

        if ( null != conn )
            conn.close();

        return rowsAffected == 1;
    }

    protected List resultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()) {
            HashMap row = new HashMap(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

}

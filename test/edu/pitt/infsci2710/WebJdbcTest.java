/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.infsci2710;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kolobj
 */
public class WebJdbcTest {
    
    public WebJdbcTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class WebJdbc.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetConnection() throws SQLException {
        System.out.println("getConnection");
        WebJdbc instance = new WebJdbc();
        Connection expResult = null;
        Connection result = instance.getConnection();
        assertThat(result, is(not(expResult)));
    }
    
    /**
     * Test of read method, of class WebJdbc.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDepartmentSalaries() throws Exception {
        System.out.println("testGetDepartmentSalaries");
        
        WebJdbc instance = new WebJdbc();
        List notExpResult = null;
        int notExpSize = 0;
        List result = instance.getDepartmentSalaries();
        
        assertThat(result, is(not(notExpResult)));
        assertThat(result.size(), is(not(notExpSize)));
    }

    /**
     * Test of read method, of class WebJdbc.
     * @throws java.lang.Exception
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        String orderBy = "ID";
        String order = "DESC";
        int limit = 3;
        int offset = 0;        
        WebJdbc instance = new WebJdbc();
        List expResult = null;
        List result = instance.read(orderBy, order, limit, offset);
        
        assertThat(result, is(not(expResult)));
        assertThat(result.size(), is(limit));
    }
    
    /**
     * Test of insert method, of class WebJdbc.
     * @throws java.lang.Exception
     */
    @Test
    public void testInsert() throws Exception {
        System.out.println("insert");
        String id = "77987";
        String name = "Kim";
        String dept_name = "Physics";
        double salary = 98000;
        WebJdbc instance = new WebJdbc();
        boolean expResult = true;
        boolean result = instance.insert(id, name, dept_name, salary);
        assertThat(result, is(expResult));
    }
    
    /**
     * Test of insert method, of class WebJdbc.
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        String id = "77987";
        WebJdbc instance = new WebJdbc();
        boolean expResult = true;
        boolean result = instance.delete(id);
        assertThat(result, is(expResult));

    }


    /**
     * Test of update method, of class WebJdbc.
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        String id = "77987";
        String name = "Kim";
        String dept_name = "Physics";
        double salary = 98000;
        WebJdbc instance = new WebJdbc();
        boolean expResult = true;
        boolean result = instance.update(id, name, dept_name, salary);
        assertThat(result, is(expResult));
    }

    /**
     * Test of resultSetToArrayList method, of class WebJdbc.
     * @throws java.lang.Exception
     */
    @Test
    public void testResultSetToArrayList() throws Exception {
        System.out.println("resultSetToArrayList");
        ResultSet rs = null;
        WebJdbc instance = new WebJdbc();
        List expResult = null;
        List result = instance.resultSetToArrayList(rs);
        assertThat(result, is(not(expResult)));        
    }

    
    
}

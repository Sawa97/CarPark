/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.test;

import com.marcinsawa.carpark.manager.data.CarPark;
import com.marcinsawa.carpark.manager.data.InstanceofAllData;
import com.marcinsawa.carpark.manager.data.UserC;
import static com.marcinsawa.test.CarParkTest.carPark;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class InstanceofAllDataTest {
    static CarPark p;
    static UserC user;
    
    public InstanceofAllDataTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        p = new CarPark("TestAdress","CarPark",1000);
        user = new UserC("Marcin","Sawa",505,"haslo12",1000);
        
    }
    
    @After
    public void tearDown() {
    }

   
    @Test
    public void addCarParkTest(){
       InstanceofAllData.getListOfCarParks().put(p.getCarParkID(), null);
       assertNotNull(InstanceofAllData.getListOfCarParks());
       InstanceofAllData.getListOfCarParks().put(p.getCarParkID(), p);
       assertNotNull(InstanceofAllData.getListOfCarParks());
       InstanceofAllData.getListOfCarParks().put(p.getCarParkID(), p);
       assertEquals(1,InstanceofAllData.getListOfCarParks().size());

      

       
    }
    
    @Test
    public void removeCarParkTest(){
       InstanceofAllData.getListOfCarParks().remove(p.getCarParkID());
       assertEquals(0,InstanceofAllData.getListOfCarParks().size());
        p = new CarPark("TestAdresewes","CarPark",100032);
        try{
        InstanceofAllData.getListOfCarParks().remove(p.getCarParkID());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
    
}

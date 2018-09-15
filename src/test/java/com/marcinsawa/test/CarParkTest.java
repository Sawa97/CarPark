/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.test;

import com.marcinsawa.carpark.manager.data.CarPark;
import com.marcinsawa.carpark.manager.data.Place;
import com.marcinsawa.carpark.manager.data.UserC;
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
public class CarParkTest {
    
    static CarPark carPark;
    static UserC user;
    static Place place;
    public CarParkTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        carPark = new CarPark("TestAdress","CarPark",1000);
        user = new UserC("Marcin","Sawa",505,"haslo12",1000);
        place = new Place(true,6,2302);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addUserTest(){
        assertTrue(carPark.addUser(user));
        assertFalse(carPark.addUser(user));
        user = new UserC("Marcin","Sawa",505,"haslo12",1001);
        assertFalse(carPark.addUser(user));
    }
    @Test
    public void removeUserTest(){
        assertFalse(carPark.removeUser(user));
        carPark.addUser(user);
        assertTrue(carPark.removeUser(user));
        user = new UserC("Marcin","Sawa",505,"haslo12",1001);
        carPark.addUser(user);
        assertFalse(carPark.removeUser(user));
        
    }
    @Test
    public void addPlacesTest(){
        carPark.addPlaces(place);
        assertEquals(1,carPark.getTotalPlaces());
        assertEquals(0,carPark.getFreePlaces());
        assertEquals(1,carPark.getTakenPlaces());
        carPark.addPlaces(place);
        assertEquals(1,carPark.getTakenPlaces());
        
       
    }
    @Test
    public void changeStatusTest(){
        carPark.addPlaces(place);
        assertFalse(carPark.changeStatus(11));
        assertTrue(carPark.changeStatus(6));
        assertEquals(1,carPark.getFreePlaces());
        
    }
}

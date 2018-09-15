/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.carpark.manager.data;

import java.util.HashMap;

/**
 *
 * @author admin
 */
public class InstanceofAllData {
    
    public static InstanceofAllData instance = new InstanceofAllData();
    private static HashMap<Integer,CarPark> listOfCarParks = new HashMap<>();
    private static HashMap<Integer,UserC> listOfUsers = new HashMap<>();
    private static UserC actualUser;
    private static CarPark actualCarPark;
    
    

    public static InstanceofAllData getInstance() {
        return instance;
    }

    public static UserC getActualUser() {
        return actualUser;
    }

    public static CarPark getActualCarPark() {
        return actualCarPark;
    }

    public static void setActualCarPark(CarPark actualCarPark) {
        InstanceofAllData.actualCarPark = actualCarPark;
    }
    

    public static void setActualUser(UserC actualUser) {
        InstanceofAllData.actualUser = actualUser;
    }
    
    

    public static HashMap<Integer, CarPark> getListOfCarParks() {
        return listOfCarParks;
    }

    public static HashMap<Integer, UserC> getListOfUsers() {
        return listOfUsers;
    }

    public static void setInstance(InstanceofAllData instance) {
        InstanceofAllData.instance = instance;
    }
    
    public void addCarPark(CarPark p){
        if(p!=null){
        this.listOfCarParks.put(p.getCarParkID(), p);
        }
    }
    
    public void removeCarPark(CarPark p){
        this.listOfCarParks.remove(p.getCarParkID());
    }
    

    public static void setListOfCarParks(HashMap<Integer, CarPark> listOfCarParks) {
        InstanceofAllData.listOfCarParks = listOfCarParks;
    }

    public static void setListOfUsers(HashMap<Integer, UserC> listOfUsers) {
        InstanceofAllData.listOfUsers = listOfUsers;
    }
    
    
    
}

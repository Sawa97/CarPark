/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.carpark.manager.data;


import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author admin
 */
@Entity
public class CarPark implements Serializable{
    private  String address;
    private  String name;
    @Id
    private  Integer carParkID;
    private int totalPlaces;
    private int takenPlaces;
    private int freePlaces;
    @OneToMany(cascade= {CascadeType.ALL})
    @NotFound(action=NotFoundAction.IGNORE)
    private List<Place> places;
    @OneToMany(cascade= {CascadeType.ALL})
    @NotFound(action=NotFoundAction.IGNORE)
    private  List<UserC> listOfUsers;

    
    public CarPark(String address, String name, Integer carParkID) {
        this.address = address;
        this.name = name;
        this.carParkID = carParkID;
        listOfUsers = new ArrayList<>();
        places = new ArrayList<>();
    }

    public CarPark() {
    }
    
    

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    
    public void setTakenPlaces(int takenPlaces) {
        this.takenPlaces = takenPlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void setListOfUsers(ArrayList<UserC> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }
    
    

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Integer getCarParkID() {
        return carParkID;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public int getTakenPlaces() {
        return takenPlaces;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<UserC> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<UserC> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    


    public int getFreePlaces() {
        return freePlaces;
    }

    
    
    public boolean addUser(UserC newUser){
        if(listOfUsers.contains(newUser)){
            return false;
        }
        if(this.equals(newUser.getCarPark())){
        this.listOfUsers.add(newUser);
        return true;
        }
        return false;
        
    }
    
    public boolean removeUser(UserC oldUser){
        if(!listOfUsers.contains(oldUser)){
            return false;
        }
        if(this.equals(oldUser.getCarPark())){
        this.listOfUsers.remove(oldUser);
        return true;
        }
        return false;
    }
    
    public void addPlaces(Place place){
        if(!places.contains(place))
        {
        this.totalPlaces++;
        this.places.add(place);
        if(place.isStatus()){
            this.takenPlaces++;
        }
        else
        {
            this.freePlaces++;
        }
        }
       
    }
    
    public void changeStatus(int number,Session session){
        for(Place place : places){
            if(place.getNumber()==number){
                if(place.isStatus()==true)
                {
                    place.setStatus(false);
                    this.takenPlaces--;
                    this.freePlaces++;
                }
                else
                {
                    place.setStatus(true);
                    this.freePlaces--;
                    this.takenPlaces++;
            }
                
            session.beginTransaction();
            Query query = session.createQuery("update Place set status= '"+place.isStatus()+"'"+ "where id IN("+number+")");
            query.executeUpdate();
            query = session.createQuery("update CarPark set takenPlaces='"+this.getTakenPlaces()+"'"+"where id IN("+this.getCarParkID()+")");
            query.executeUpdate();
            query = session.createQuery("update CarPark set FreePlaces='"+this.getFreePlaces()+"'"+"where id IN("+this.getCarParkID()+")");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();

        }
            
    }
    
    
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj instanceof CarPark){
            CarPark object = (CarPark)obj;
            if(object==this)
                return true;
        }
        return false;
        
    }
    
   

    
    
    
    
}

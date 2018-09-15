/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.carpark.manager.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author admin
 */
@Entity
public class Place implements Serializable{
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="carpark_id")
    private CarPark carPark;
    private boolean status;
    @Id
    @Column(nullable=false)
    private int number;

    public Place(boolean status, int number, CarPark carPark) {
        this.status = status;
        this.number = number;
        this.carPark= carPark;
    }

    public Place() {
    }
    

    public boolean isStatus() {
        return status;
    }

    public int getNumber() {
        return number;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CarPark getCarPark() {
        return carPark;
    }
    
    
    
    
   
    
}

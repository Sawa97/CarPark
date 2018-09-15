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
public class UserC implements Serializable {
    private String Name;
    private String Surname;
    @Id
    @Column(nullable=false)
    private  Integer ID;
    private  String password;
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="carpark_id")
    private CarPark carPark;

    public UserC(String Name, String Surname, Integer ID, String password, CarPark carPark) {
        this.Name = Name;
        this.Surname = Surname;
        this.ID = ID;
        this.password = password;
        this.carPark = carPark;
    }

    public UserC() {
    }
    
    


    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public Integer getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }

    public CarPark getCarPark() {
        return carPark;
    }

  
    
    
    
    

    
    
    
}

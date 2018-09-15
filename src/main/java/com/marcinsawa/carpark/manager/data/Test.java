/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.carpark.manager.data;

import com.marcinsawa.carpark.manager.data.CarPark;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author admin
 */
public class Test {

    public Test() {
        
        boolean temp;
        CarPark parking = new CarPark("Powśtanców 21","Zwykły Parking",2302);
         UserC user = new UserC("Marcin","Sawa",505,"haslo12",parking);
        for(int i=0; i<500; i++){
            
            if(i%3==0){
                temp=true;
                
            }
            else
            {
                temp=false;
            }
            Place place = new Place(temp,i,parking);
            parking.addPlaces(place);
        }
        parking.addUser(user);
        
        
         SessionFactory sessionFactory;
        sessionFactory = new Configuration().configure().buildSessionFactory();
       try(Session session = sessionFactory.openSession()){
       session.beginTransaction();
       session.save(parking);
       session.getTransaction().commit();
       session.close();
       sessionFactory.close();
        }catch(Exception e){}
        
        
        
      
    }
    
    
    
}

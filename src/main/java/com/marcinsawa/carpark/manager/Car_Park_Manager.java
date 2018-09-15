

package com.marcinsawa.carpark.manager;


import com.marcinsawa.carpark.manager.data.Test;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class Car_Park_Manager {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        Test test = new Test();
        
        loginPanel loginFrame = new loginPanel();
        loginFrame.setTitle("Car-Park Manager");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
        
        
    }
    
  
    
}

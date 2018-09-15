/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcinsawa.carpark.manager;


import com.marcinsawa.carpark.manager.data.CarPark;
import com.marcinsawa.carpark.manager.data.InstanceofAllData;
import com.marcinsawa.carpark.manager.data.Test;
import com.marcinsawa.carpark.manager.data.UserC;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Math.sqrt;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author admin
 */
public class MainPanel extends javax.swing.JFrame {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        initComponents();
        initializeCarPark(InstanceofAllData.getActualCarPark(),InstanceofAllData.getActualUser());     
        Test t = new Test();//to zmienic
    }
    
    MouseListener ml = new MouseAdapter(){
        @Override
        public void mouseEntered(MouseEvent e){
            e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
           // System.out.println(e.getComponent());
        }
        
        @Override
        public void mouseClicked(MouseEvent e){
            int number=0;
           
                
                if(e.getComponent() instanceof JPanel){
                    JPanel panel = (JPanel)e.getComponent();
                    Component[] components = panel.getComponents();
                    for(Component comp: components){
                        if(comp instanceof JLabel){
                            
                            JLabel label = (JLabel)comp;
                            number = Integer.parseInt(label.getText()); 
                        }
                        
                    }
            }
                Session session = factory.openSession();
                
            InstanceofAllData.getActualCarPark().changeStatus(number, session);
            
            
            
            
            if(e.getComponent().getBackground()==Color.red)
            {
                e.getComponent().setBackground(Color.green);
            
                
            }else
            {
            e.getComponent().setBackground(Color.red);
           
            } 
            refreshing(InstanceofAllData.getActualCarPark(),InstanceofAllData.getActualUser());
        }
    };
   
    private void initializeCarPark(CarPark p,UserC u){
        createParkingButtons(p);
        leftPanelHandler(p);
        topPanelHandler(u);
        createChart(p);
        
    }
    
    private void refreshing(CarPark p, UserC u){
        leftPanelHandler(p);
        createChart(p);
    }
    
    
    
    private void leftPanelHandler(CarPark p){
        carParkNameLabel.setText(p.getName());
        addressLabel.setText(p.getAddress());
        totalPlacesLabel.setText(Integer.toString(p.getTotalPlaces()));
        takenPlacesLabel.setText(Integer.toString(p.getTakenPlaces()));
        freePlacesLabel.setText(Integer.toString(p.getFreePlaces()));
        
        
        
    }
    private void topPanelHandler(UserC u){
        nameLabel.setText(u.getName()+" "+u.getSurname());
    }
    
    
    
    private void createChart(CarPark p){
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("free places", new Integer(p.getFreePlaces()));
      dataset.setValue("taken places", new Integer(p.getTakenPlaces()));

      
      
      
        Color myColor = new Color(77,72,78);

        
        PiePlot plot = new PiePlot(dataset);
        StandardPieSectionLabelGenerator labels = new StandardPieSectionLabelGenerator("{0}={2}");
        plot.setLabelGenerator(labels);
        plot.setInteriorGap(0);
        plot.setBackgroundPaint(myColor);
        plot.setBaseSectionOutlinePaint(myColor);
        plot.setShadowXOffset(0);
        plot.setShadowYOffset(0);
        plot.setOutlineVisible(false);
        
        plot.setSectionPaint("free places",new Color(51,204,0));
        plot.setSectionPaint("taken places", new Color(255,51,51));
        
        
        JFreeChart chart = new JFreeChart("",plot);
        chart.setPadding(new RectangleInsets(0,0,0,0));
        
        chart.setBackgroundPaint(myColor);
        chart.setBorderVisible(false);
        chart.clearSubtitles();
        
        ChartPanel cp = new ChartPanel(chart);
               leftPanel.add(cp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 200, 150));

        
        
        
    }
    
    
    
    private void createParkingButtons(CarPark p){
        int basicWidth = mainPanel.getWidth();
        int basicHeight = mainPanel.getHeight();
        int size;
        int newY=0;
        int newX=0;
        
        int tempSize;
        int oneSquareBefore;
        
        int rest;
        int count;
        
       
        //calculate size of ine square
        tempSize = basicWidth*basicHeight;
        oneSquareBefore= tempSize/p.getTotalPlaces();
        size=(int)sqrt(oneSquareBefore);
        
        
        if(basicHeight%size!=0){
            rest=basicHeight%size;
            count = basicHeight/size;
            size = size -((size-rest)/count+1);
        }

        
        
        for(int i=0; i<p.getTotalPlaces(); i++){

           
            if(i!=0){
                newX +=size; 
            }
            if(newX+size>basicWidth){
                newX=0;
                newY+=size;
            }
            JPanel panel = new JPanel();
            panel.addMouseListener(ml);
            
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setBounds(newX, newY, size, size);
            JLabel label = new JLabel(Integer.toString(i),JLabel.CENTER);
            if(p.getPlaces().get(i).isStatus()==false){
                panel.setBackground(Color.green);
            }
            else{
                panel.setBackground(Color.red);
            }
            panel.setLayout(new FlowLayout()); 
            panel.add(label);
            mainPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(newX, newY, size, size));
        }
        
        
        
    } 
  
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        carParkNameLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        freePlacesLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        totalPlacesLabel = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        takenPlacesLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        topLabel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        logOutLabel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        leftPanel.setBackground(new java.awt.Color(77, 72, 78));
        leftPanel.setAlignmentX(0.0F);
        leftPanel.setAlignmentY(0.0F);
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        carParkNameLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        carParkNameLabel.setForeground(new java.awt.Color(240, 240, 240));
        carParkNameLabel.setText("Parking Kleparz");
        leftPanel.add(carParkNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 36, -1, -1));

        addressLabel.setForeground(new java.awt.Color(240, 240, 240));
        addressLabel.setText("Kraków, ul.Długa 63");
        leftPanel.add(addressLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 83, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("Total number of places");
        leftPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 194, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("Free places");
        leftPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 439, -1, -1));

        freePlacesLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        freePlacesLabel.setForeground(new java.awt.Color(51, 204, 0));
        freePlacesLabel.setText("70");
        leftPanel.add(freePlacesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 482, -1, -1));

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        leftPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 525, 230, 20));

        totalPlacesLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        totalPlacesLabel.setForeground(new java.awt.Color(240, 240, 240));
        totalPlacesLabel.setText("180");
        leftPanel.add(totalPlacesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(94, 237, 44, 35));

        jSeparator3.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));
        leftPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 290, 230, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 240, 240));
        jLabel10.setText("Taken places");
        leftPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 318, -1, -1));

        takenPlacesLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        takenPlacesLabel.setForeground(new java.awt.Color(255, 51, 51));
        takenPlacesLabel.setText("110");
        leftPanel.add(takenPlacesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 361, -1, -1));

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator4.setAlignmentX(0.0F);
        leftPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 0, 240, 20));

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(240, 240, 240));
        leftPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 422, 230, 20));

        topLabel.setBackground(new java.awt.Color(77, 72, 78));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Person_48px.png"))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(240, 240, 240));
        nameLabel.setText("Imie Nazwisko");
        nameLabel.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Car-Park Manager");
        jLabel6.setMaximumSize(new java.awt.Dimension(230, 48));
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        logOutLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        logOutLabel.setForeground(new java.awt.Color(240, 240, 240));
        logOutLabel.setText("Log Out");
        logOutLabel.setToolTipText("");
        logOutLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout topLabelLayout = new javax.swing.GroupLayout(topLabel);
        topLabel.setLayout(topLabelLayout);
        topLabelLayout.setHorizontalGroup(
            topLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLabelLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 511, Short.MAX_VALUE)
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(logOutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        topLabelLayout.setVerticalGroup(
            topLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(topLabelLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(logOutLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                mainPanelPropertyChange(evt);
            }
        });
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(topLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutLabelMouseClicked
      
        setVisible(false);
        dispose();
        loginPanel loginFrame = new loginPanel();
        loginFrame.setTitle("Car-Park Manager");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
        factory.close();
    }//GEN-LAST:event_logOutLabelMouseClicked

    private void mainPanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_mainPanelPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_mainPanelPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel carParkNameLabel;
    private javax.swing.JLabel freePlacesLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel logOutLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel takenPlacesLabel;
    private javax.swing.JPanel topLabel;
    private javax.swing.JLabel totalPlacesLabel;
    // End of variables declaration//GEN-END:variables
}

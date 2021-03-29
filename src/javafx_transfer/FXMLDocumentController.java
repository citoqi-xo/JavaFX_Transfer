/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_transfer;

import db.MyKoneksi;
import java.net.URL;
import java.sql.Array;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author arifika.aop06542
 */
public class FXMLDocumentController implements Initializable {
    MyKoneksi myKon = new MyKoneksi();
    PreparedStatement preMy = null;
    String valueIn;
    
    private ObservableList<ObservableList> data = FXCollections.observableArrayList();
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        myKon.db();
        transfer();
    }
    
    public void transfer(){
        //query source data
        String allprojectview = "SELECT * FROM arma_ca_sd.0_raw_mastertoko";
        
        try {
            //1. delete all data from mysql
            myKon.stat.executeUpdate("DELETE FROM db_israrmonev.`0_raw_mastertoko` ");
            
            //2. koneksi data resultset
            myKon.res = myKon.stat.executeQuery(allprojectview);
            
            //3. query insert ke table yang baru
            String sql = "INSERT INTO db_israrmonev.`0_raw_mastertoko`(`id`, `KodeToko`, `NamaToko`, `update`, `updatemySql`) "
                + "VALUES (?,?,?,?,?)";
            
            //4. Menyiapkan koneksi eksekusi statement
            preMy = myKon.con.prepareStatement(sql);
            
            //5. Load data query source
            while (myKon.res.next()) {
                
                //6. eksekusi insert data 
                //6.1 looping banyak kolom
                 for (int i = 1; i <= myKon.res.getMetaData().getColumnCount(); i++) {
                    //6.2 menyiapkan tampungan object yang diload kedalam kolom2
                    preMy.setObject(i, myKon.res.getObject(i));
                 }
                 
                 preMy.addBatch();
                 
             }
            
            //7. Eksekusi data tranfer ke source baru
            preMy.executeBatch();
            preMy.close();
            myKon.close();
             
           
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
     public void buildData(){
        try {
            //SQL FOR SELECTING allprojectview
            //String allprojectview = "SELECT * FROM `viewprojectauditor2019`";
            String allprojectview = "SELECT * FROM gtik8869_trackingarma.project_has_master_karyawan";
            
            //ResultSet
            myKon.res = myKon.stat.executeQuery(allprojectview);
            
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < myKon.res.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(myKon.res.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        if(param.getValue().get(j) == null) {
                            valueIn = "NULL";
                        } else {
                            valueIn = param.getValue().get(j).toString() ;
                        }
                        return new SimpleStringProperty(valueIn);
                        
                    }
                });
               
                System.out.println(myKon.res.getMetaData().getColumnName(i + 1));
            }
            
            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             * */
             while (myKon.res.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= myKon.res.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    
                    row.add(myKon.res.getString(i));
                    //System.out.print(myKon.res.getString(i));
                    if (i == 7) {
                        
                        System.out.println(" added " + row);
                    }
                    
                }
                //System.out.println("Row [1] added " + row);
                data.add(row);

            }
            
            //FINALLY ADDED TO TableView
            //tableProject.setItems(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    
}

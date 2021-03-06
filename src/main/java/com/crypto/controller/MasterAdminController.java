/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import com.crypto.AES128;
import com.crypto.Prefs;
import com.crypto.model.Admin;
import com.crypto.utility.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Asus-Ricky
 */
public class MasterAdminController implements Initializable {
    
    @FXML private AnchorPane mainPane;
    @FXML private TableView<Admin> adm_tableview;
    @FXML private JFXButton adm_savebut;
    @FXML private JFXButton adm_closebut;
    @FXML private JFXButton adm_deletebut;
    @FXML private JFXButton adm_updatebut;
    @FXML private TextField adm_kode;
    @FXML private TextField adm_nama;
    @FXML private TextArea adm_alamat;
    @FXML private TextField adm_email;
    @FXML private TextField adm_telepon;
    @FXML private TextField adm_username;
    @FXML private PasswordField adm_password;
    @FXML private JFXToggleButton btnSwitch;
    @FXML private TableColumn<Admin, String> colKode;
    @FXML private TableColumn<Admin, String> colNama;
    @FXML private TableColumn<Admin, String> colUsername;
    @FXML private TableColumn<Admin, String> colPassword;
    @FXML private TableColumn<Admin, String> colAlamat;
    @FXML private TableColumn<Admin, String> colEmail;
    @FXML private TableColumn<Admin, String> colTelepon;
    @FXML private Button but_searchclear;
    @FXML private TextField txtSearch;
    
    AES128 crypt;
    
    DbHandler objDBHandler;
    Connection con;
    private PreparedStatement pst;
    private ObservableList<Admin> dataAdmin;
    private int mode = 1;
    private int LastClick = -1;
    public String ADM_KODE = "";
    Prefs pref = new Prefs();
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crypt = new AES128();
        assert adm_tableview != null : "fx:id=\"adm_tableview\" was not injected: check your FXML file 'MasterAdmin.fxml'.";
        colKode.setCellValueFactory(new PropertyValueFactory<Admin, String>("Kode"));
        colNama.setCellValueFactory(new PropertyValueFactory<Admin, String>("Nama"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<Admin, String>("Alamat"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Admin, String>("Email"));
        colTelepon.setCellValueFactory(new PropertyValueFactory<Admin, String>("Telp"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Admin, String>("Username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<Admin, String>("Password"));
        ADM_KODE = pref.getPrefs("loged_kode");
        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        buildData(ADM_KODE);
        clearFields();
        //initFilter();
    }   

    public void buildData(String admkode){        
        dataAdmin = FXCollections.observableArrayList();
        try{  
            String SQL = "";    
            if (ADM_KODE.equals("ADM001")){
                SQL = "Select * from admin Order By kode";       
            } else {
                SQL = "Select * from admin where kode = '" + admkode + "'" ;       
            }
                
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            while(rs.next()){  
                BooleanProperty isOn = btnSwitch.selectedProperty();
                if (isOn.get()==true){
                
                    Admin cm = new Admin();
                    cm.kode.set(rs.getString("kode"));
                    cm.nama.set(crypt.decrypt(rs.getString("nama")));
                    cm.alamat.set(crypt.decrypt(rs.getString("alamat")));
                    cm.email.set(crypt.decrypt(rs.getString("email")));
                    cm.telp.set(crypt.decrypt(rs.getString("telp")));
                    cm.username.set(crypt.decrypt(rs.getString("username")));
                    cm.password.set("**********");
                    cm.status.set(rs.getString("status"));
                    dataAdmin.add(cm); 
                } else {
                    Admin cm = new Admin();
                    cm.kode.set(rs.getString("kode"));
                    cm.nama.set(rs.getString("nama"));
                    cm.alamat.set(rs.getString("alamat"));
                    cm.email.set(rs.getString("email"));
                    cm.telp.set(rs.getString("telp"));
                    cm.username.set(rs.getString("username"));
                    cm.password.set("**********");
                    cm.status.set(rs.getString("status"));
                    dataAdmin.add(cm);
                }
            }
            adm_tableview.setItems(dataAdmin);
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
        }
    }
    
    @FXML protected void adm_closebutclick(){
        //MDIWindow myMDI = Utility.getMDIWindow(mainPane);
        //myMDI.closeMdiWindow();
        clearFields();
    }
    
    @FXML protected void adm_tableviewclick(){
       //System.out.println("Cell Click");
       Admin selectedData = adm_tableview.getSelectionModel().getSelectedItem();
       
       if ((selectedData==null)&&(LastClick!=-1)) {
           adm_tableview.getSelectionModel().select(LastClick);
           selectedData = adm_tableview.getSelectionModel().getSelectedItem();
       } 
       
       if (selectedData!=null){
            mode = 2;
            adm_kode.setText(selectedData.getKode());
            adm_kode.setEditable(false);
            adm_nama.setText(selectedData.getNama());
            adm_nama.setEditable(true);
            adm_alamat.setText(selectedData.getAlamat());
            adm_alamat.setEditable(true);
            adm_email.setText(selectedData.getEmail());
            adm_email.setEditable(true);
            adm_telepon.setText(selectedData.getTelp());
            adm_telepon.setEditable(true);
            adm_username.setText(selectedData.getUsername());
            adm_username.setEditable(true);
            adm_password.setText(selectedData.getPassword());
            adm_password.setEditable(true);
            //adm_status.getSelectionModel().select(selectedData.getStatus());
       }
       
    }
    
    @FXML protected void adm_tableviewpressed(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")){
            Admin selectedData = adm_tableview.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                mode = 2;
                adm_kode.setText(selectedData.getKode());
                adm_kode.setEditable(false);
                adm_nama.setText(selectedData.getNama());
                adm_nama.setEditable(true);
                adm_alamat.setText(selectedData.getAlamat());
                adm_alamat.setEditable(true);
                adm_email.setText(selectedData.getEmail());
                adm_email.setEditable(true);
                adm_telepon.setText(selectedData.getTelp());
                adm_telepon.setEditable(true);
                adm_username.setText(selectedData.getUsername());
                adm_username.setEditable(true);
                adm_password.setText(selectedData.getPassword());
                adm_password.setEditable(true);
                //adm_status.getSelectionModel().select(selectedData.getStatus());
            }
        }
    }
    
    @FXML protected void adm_tableviewreleased(KeyEvent event){
        if ((event.getCode().toString().equals("UP")) || 
            (event.getCode().toString().equals("DOWN")) ){
            Admin selectedData = adm_tableview.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                mode = 2;
                adm_kode.setText(selectedData.getKode());
                adm_kode.setEditable(false);
                adm_nama.setText(selectedData.getNama());
                adm_nama.setEditable(true);
                adm_alamat.setText(selectedData.getAlamat());
                adm_alamat.setEditable(true);
                adm_email.setText(selectedData.getEmail());
                adm_email.setEditable(true);
                adm_telepon.setText(selectedData.getTelp());
                adm_telepon.setEditable(true);
                adm_username.setText(selectedData.getUsername());
                adm_username.setEditable(true);
                adm_password.setText(selectedData.getPassword());
                adm_password.setEditable(true);
                //adm_status.getSelectionModel().select(selectedData.getStatus());
            }
        }
    }
    
    @FXML protected void adm_deletebutclick(){
        Admin selectedData = adm_tableview.getSelectionModel().getSelectedItem();
        if (selectedData!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus data");
            alert.setHeaderText("Hapus data admin " + selectedData.getNama() + " ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                    String delet = "DELETE FROM admin where kode  = ?"; 
                    pst = con.prepareStatement(delet);
                    pst.setString(1, selectedData.getKode());
                    int success = pst.executeUpdate();
                    if (success == 1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Hapus data");
                        alert2.setHeaderText("Sukses menghapus data");
                        alert2.show();
                        buildData(ADM_KODE);
                        clearFields();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error on Building Data");   
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Hapus data");
                    alert2.setHeaderText("Gagal menghapus data! " + e.getMessage().toString());
                    alert2.show();
                }
            } 
        }
    }
    
    @FXML protected void adm_updatebutclick() throws SQLException{
        if ((mode == 2)&&(!adm_kode.getText().equals(null))) {
            BooleanProperty isOn = btnSwitch.selectedProperty();
            String updat = "UPDATE  admin set nama = ?, alamat = ?, email = ?, telp = ?, username = ?, password = ?, status = ?" + 
                               " WHERE kode = ?";

            pst = con.prepareStatement(updat);
            
            if (isOn.get()==true){
                pst.setString(1, crypt.encrypt(adm_nama.getText()));
                pst.setString(2, crypt.encrypt(adm_alamat.getText()));
                pst.setString(3, crypt.encrypt(adm_email.getText()));
                pst.setString(4, crypt.encrypt(adm_telepon.getText()));
                pst.setString(5, crypt.encrypt(adm_username.getText().toLowerCase()));
                pst.setString(6, crypt.encrypt(adm_password.getText()));
                pst.setString(7, "Aktif");
                pst.setString(8, adm_kode.getText());
            } else {
                pst.setString(1, adm_nama.getText());
                pst.setString(2, adm_alamat.getText());
                pst.setString(3, adm_email.getText());
                pst.setString(4, adm_telepon.getText());
                pst.setString(5, adm_username.getText().toLowerCase());
                pst.setString(6, adm_password.getText());
                pst.setString(7, "Aktif");
                pst.setString(8, adm_kode.getText());
            }

            int success = pst.executeUpdate();
            if (success == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Simpan data");
                alert.setHeaderText("Sukses menyimpan data");
                alert.show();
                buildData(ADM_KODE);
                clearFields();
            }
        }
    }
    
    @FXML protected void adm_savebutclick(ActionEvent event) throws SQLException {
        if ((mode == 1)&&(!adm_kode.getText().equals(null))){ //new record
           try{
                String insert = "INSERT INTO admin(kode,nama,alamat,email,telp,username,password,status,creator) "
                     +  "VALUES (?,?,?,?,?,?,?,?,?)"; 

                 pst = con.prepareStatement(insert);
                 pst.setString(1, adm_kode.getText().toUpperCase());
                 pst.setString(2, crypt.encrypt(adm_nama.getText()));
                 pst.setString(3, crypt.encrypt(adm_alamat.getText()));
                 pst.setString(4, crypt.encrypt(adm_email.getText()));
                 pst.setString(5, crypt.encrypt(adm_telepon.getText()));
                 pst.setString(6, crypt.encrypt(adm_username.getText().toLowerCase()));
                 pst.setString(7, crypt.encrypt(adm_password.getText()));
                 pst.setString(8, "Aktif");
                 pst.setString(9, ADM_KODE);

                 int success = pst.executeUpdate();
                 if (success == 1) {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Tambah data");
                     alert.setHeaderText("Sukses menambah data");
                     alert.show();
                     buildData(ADM_KODE);
                     clearFields();
                 }
           } catch (NullPointerException np){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tambah data");
                alert.setHeaderText("Semua field harus diisi dahulu");
                alert.show();
           }
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        mode = 1;
        if (ADM_KODE.equals("ADM001")){
            adm_kode.setText(getNewKode());
            adm_kode.setEditable(false);
            adm_nama.setText(null);
            adm_alamat.setText(null);
            adm_email.setText(null);
            adm_telepon.setText(null);
            adm_username.setText(null);
            adm_password.setText(null);
            adm_nama.requestFocus();
        } else {
            adm_kode.setText(null);
            adm_kode.setEditable(false);
            adm_nama.setText(null);
            adm_nama.setEditable(false);
            adm_alamat.setText(null);
            adm_alamat.setEditable(false);
            adm_email.setText(null);
            adm_email.setEditable(false);
            adm_telepon.setText(null);
            adm_telepon.setEditable(false);
            adm_username.setText(null);
            adm_username.setEditable(false);
            adm_password.setText(null);
            adm_password.setEditable(false);
            adm_nama.requestFocus();
            adm_deletebut.disableProperty().set(true);
        }
    }
    
    private String getNewKode(){
        String res = "";
        try {
            String SQL = "Select max(kode) from admin ";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            if( (rs!=null) &&(rs.next()) ){
                String dres = rs.getString(1);
                if (dres!=null){
                    dres = String.valueOf(Integer.parseInt(dres.substring(3)) + 1);
                    res = "ADM" + "000".substring(dres.length()) + dres;
                } else 
                    res = "ADM001";
            } else {
                res = "ADM001"; 
            }
            /*if (res=="ADM001") {
                try{
                    String insert = "INSERT INTO admin(kode,nama,alamat,email,telp,username,password,status,creator) "
                         +  "VALUES (?,?,?,?,?,?,?,?,?)"; 

                     pst = con.prepareStatement(insert);
                     pst.setString(1, res);
                     pst.setString(2, crypt.encrypt("MASTER ADMIN"));
                     pst.setString(3, crypt.encrypt("nowhere"));
                     pst.setString(4, crypt.encrypt("dont@ask.com"));
                     pst.setString(5, crypt.encrypt("nophone"));
                     pst.setString(6, crypt.encrypt("admin"));
                     pst.setString(7, crypt.encrypt("admin"));
                     pst.setString(8, "Aktif");
                     pst.setString(9, "SYSTEM");

                     int success = pst.executeUpdate();
                     if (success == 1) {
                         res = "ADM002"; 
                     }
               } finally {
                    res = "ADM002";
               }
            }*/
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on GetNewCode");            
        }
        return res;
    }
    
    @FXML protected void btnSwitchClick(){
        LastClick = adm_tableview.getSelectionModel().getSelectedIndex();
        buildData(ADM_KODE);
        adm_tableviewclick();
        adm_tableview.requestFocus();
    }
    
    @FXML protected void butSearchClearClick(){
       txtSearch.setText("");
       txtSearch.requestFocus();
       adm_tableview.setItems(dataAdmin);
    }
    
    
    @FXML protected void txtSearchReleased(){
        if(txtSearch.getText().isEmpty()) {
            adm_tableview.setItems(dataAdmin);
            return;
        }
        ObservableList<Admin> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<Admin, ?>> cols = adm_tableview.getColumns();
        for(int i=0; i<dataAdmin.size(); i++) {

            for(int j=0; j<cols.size(); j++) {
                TableColumn col = cols.get(j);
                String cellValue = col.getCellData(dataAdmin.get(i)).toString();
                cellValue = cellValue.toLowerCase();
                if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                    tableItems.add(dataAdmin.get(i));
                    break;
                }                        
            }

        }
        adm_tableview.setItems(tableItems);
    }
    

}

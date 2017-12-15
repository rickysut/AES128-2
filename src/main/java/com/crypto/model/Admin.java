/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.model;

import javafx.beans.property.SimpleStringProperty;


public class Admin {
    public SimpleStringProperty kode = new SimpleStringProperty();
    public SimpleStringProperty nama = new SimpleStringProperty();
    public SimpleStringProperty alamat = new SimpleStringProperty();
    public SimpleStringProperty email = new SimpleStringProperty();
    public SimpleStringProperty telp = new SimpleStringProperty();
    public SimpleStringProperty username = new SimpleStringProperty();
    public SimpleStringProperty password = new SimpleStringProperty();
    public SimpleStringProperty status = new SimpleStringProperty();
    
    public String getKode() {
      return kode.get();
    }
    public String getNama() {
      return nama.get();
    }
    public String getAlamat() {
      return alamat.get();
    }
    public String getEmail() {
      return email.get();
    }
    public String getTelp() {
      return telp.get();
    }
    public String getUsername() {
      return username.get();
    }
    public String getPassword() {
      return password.get();
    }
    public String getStatus() {
      return status.get();
    }

}

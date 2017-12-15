/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.model;

import javafx.beans.property.SimpleStringProperty;

public class Barang {
    public SimpleStringProperty kode    = new SimpleStringProperty();
    public SimpleStringProperty nama    = new SimpleStringProperty();
    public SimpleStringProperty harga   = new SimpleStringProperty();
    public SimpleStringProperty status  = new SimpleStringProperty();
    
    public String getKode(){
        return kode.get();
    }
    
    public String getNama(){
        return nama.get();
    }
    
    public String getHarga(){
        return harga.get();
    }
    
    
    public String getStatus(){
        return status.get();
    }
    
}

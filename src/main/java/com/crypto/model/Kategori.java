/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Asus-Ricky
 */
public class Kategori {
    public SimpleStringProperty kode    =  new SimpleStringProperty();
    public SimpleStringProperty kategori=  new SimpleStringProperty();
    public SimpleStringProperty active  =  new SimpleStringProperty();
    
    public String getKode(){
        return kode.get();
    }
    public String getKategori(){
        return kategori.get();
    }
    public String getActive(){
        return active.get();
    }
    
    
    
}

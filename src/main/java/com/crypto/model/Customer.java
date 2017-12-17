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
public class Customer {
    public SimpleStringProperty kode    =  new SimpleStringProperty();
    public SimpleStringProperty nama    =  new SimpleStringProperty();
    public SimpleStringProperty namafb =  new SimpleStringProperty();
    public SimpleStringProperty alamat  =  new SimpleStringProperty();
    public SimpleStringProperty kota    =  new SimpleStringProperty();
    public SimpleStringProperty provinsi=  new SimpleStringProperty();
    public SimpleStringProperty kodepos=  new SimpleStringProperty();
    public SimpleStringProperty notelp1=  new SimpleStringProperty();
    public SimpleStringProperty notelp2=  new SimpleStringProperty();
    public SimpleStringProperty urlfb  =  new SimpleStringProperty();
    public SimpleStringProperty email   =  new SimpleStringProperty();

    public String getKode(){
        return kode.get();
    }
    public String getNama(){
        return nama.get();
    }
    public String getNamaFB(){
        return namafb.get();
    }
    public String getAlamat(){
        return alamat.get();
    }
    public String getKota(){
        return kota.get();
    }
    public String getProvinsi(){
        return provinsi.get();
    }
    public String getKodePos(){
        return kodepos.get();
    }
    public String getNoTelp1(){
        return notelp1.get();
    }
    public String getNoTelp2(){
        return notelp2.get();
    }
    public String getUrlFB(){
        return urlfb.get();
    }
    public String getEmail(){
        return email.get();
    }
            
    
}

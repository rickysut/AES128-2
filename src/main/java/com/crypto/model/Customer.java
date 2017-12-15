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
    public SimpleStringProperty nama_fb =  new SimpleStringProperty();
    public SimpleStringProperty alamat  =  new SimpleStringProperty();
    public SimpleStringProperty kota    =  new SimpleStringProperty();
    public SimpleStringProperty provinsi=  new SimpleStringProperty();
    public SimpleStringProperty kode_pos=  new SimpleStringProperty();
    public SimpleStringProperty no_telp1=  new SimpleStringProperty();
    public SimpleStringProperty no_telp2=  new SimpleStringProperty();
    public SimpleStringProperty url_fb  =  new SimpleStringProperty();
    public SimpleStringProperty email   =  new SimpleStringProperty();

    public String getKode(){
        return kode.get();
    }
    public String getNama(){
        return nama.get();
    }
    public String getNamaFB(){
        return nama_fb.get();
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
        return kode_pos.get();
    }
    public String getNoTelp1(){
        return no_telp1.get();
    }
    public String getNoTelp2(){
        return no_telp2.get();
    }
    public String getUrlFB(){
        return url_fb.get();
    }
    public String getEmail(){
        return email.get();
    }
            
    
}

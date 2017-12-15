/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto;

import java.util.prefs.Preferences;

/**
 *
 * @author Asus-Ricky
 */
public class Prefs {
    Preferences preferences;

    public Prefs() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        //preferences = Preferences.userNodeForPackage(getClass());
    }

    public void setPrefs(String key, String value) {
        preferences.put(key, value);
    }

    public String getPrefs(String key) {
        return preferences.get(key, key);
    }
}

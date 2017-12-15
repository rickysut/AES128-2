/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto;

import java.text.NumberFormat;
import java.util.Locale;
import com.jfoenix.controls.JFXTextField;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Asus-Ricky
 */
public class TextMoney extends JFXTextField{
    private int maxlength;
    private String valor = "";

    public TextMoney() {
        this.maxlength = 11;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // Delete or backspace user input.
        if (getText()== null || getText().equalsIgnoreCase("")) {
            valor = "";
        }
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else{


            text = text.replaceAll("[^0-9]", "");
            valor += text;

            super.replaceText(start, end, text);
            if (!valor.equalsIgnoreCase(""))
                setText(formata(Double.parseDouble(valor)));
            int CaretPos = getCaretPosition();
            setCaretPosition(getText().length());
        }
    }
    public void setCaretPosition(int pos){
        positionCaret(pos);
    }
    
    @Override
    public void replaceSelection(String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
            // Add characters, but don't exceed maxlength.
            // text = MascaraFinanceira.show(text);
            if (text.length() > maxlength - getText().length()) {
                // text = MascaraFinanceira.show(text);
                text = text.substring(0, maxlength - getText().length());
            }
            super.replaceSelection(text);
        }
    }

    /*
     *Return the number without money mask
     **/

    public String getCleanValue(){
        String cleanString = getText().replaceAll("[^0-9]", "");
        Double cleanNumber = new Double(cleanString);
        return String.valueOf(cleanNumber/100);
    }

    private String formata(Double valor) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getNumberInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        //kursIndonesia.setMaximumIntegerDigits(0);
        //kursIndonesia.setMinimumIntegerDigits(0);
        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(valor);
    }

    //public String formata(String valor) {
    //    double v = new Double(valor);
    //    return formata(v/100);
    //}
}

package br.com.supremeforever.mdi;


import javafx.scene.Node;



public class Utility {

    /**
     * @param mainPane
     * @return
     */
    public static MDIWindow getMDIWindow(Node mainPane){
        MDIWindow mdiW = (MDIWindow) mainPane.getParent().getParent();
        return mdiW;
    }
}

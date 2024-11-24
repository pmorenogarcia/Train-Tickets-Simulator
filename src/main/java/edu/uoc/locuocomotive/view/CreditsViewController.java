package edu.uoc.locuocomotive.view;

import edu.uoc.locuocomotive.LocUOComotive;
import javafx.fxml.FXML;

import java.io.IOException;

public class CreditsViewController {

    /**
     * It goes to the "main" scene.
     */
    @FXML
    public void backMain(){
        try{
            LocUOComotive.main.goScene("main");
        }catch(IOException e){
            System.exit(1);
        }
    }

}

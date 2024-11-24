package edu.uoc.locuocomotive.view;

import edu.uoc.locuocomotive.LocUOComotive;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainViewController {

    /**
     * It goes to the "play" scene.
     */
    @FXML
    public void newGame(){
        try{
            LocUOComotive.main.goScene("play");
        }catch(IOException e){
            System.exit(1);
        }
    }

    /**
     * It goes to the "credits" scene.
     */
    @FXML
    public void readCredits(){
        try{
            LocUOComotive.main.goScene("credits");
        }catch(IOException e){
            System.exit(1);
        }
    }

}

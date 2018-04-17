/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlegame;

import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author filippopiggici
 */
public class FXML_PageGameController implements Initializable {

    @FXML
    private Label gamenumber;

    @FXML
    private Button firstnumb;

    @FXML
    private Button secondnumb;

    @FXML
    private Label scoretext;

    @FXML
    private Label timerlabel = new Label();

    @FXML
    private Label comment;

    @FXML
    private Label label_time_down;

    @FXML
    private Label label_time_up;

    @FXML
    public AnchorPane endgame;
    
    @FXML
    private Label again;
    
    @FXML
    private ImageView again_button;

    private Random rand;

    private int number1;        //smallest number

    private int number2;        // biggest number

    private int number0;       // game number

    private int x;              // width of distance between number 0 and the others.

    private int score_lost;

    private int score_won;

    private int score;

    private Timeline timeline;

    private static Integer gametime;

    private IntegerProperty timeSeconds;

    private Time t;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        endgame.setVisible(false);
        label_time_down.setVisible(false);
        label_time_up.setVisible(false);
        x = 2;        // initialise x to 0
        score = 0;
        score_won = +1;
        score_lost = -1;
        gametime = 30;          // initial game time
        timeSeconds = new SimpleIntegerProperty(gametime);
        manageTime();
        generateMainNumb();
        generateTwoNumb();

    }

    public void generateMainNumb() {
        rand = new Random();
        number0 = (rand.nextInt(60) + 1);
        gamenumber.setText(String.valueOf(number0));     // value between 0 and 60, set and the main number.

    }

    public void generateTwoNumb() {
        rand = new Random();
        number1 = number0 - (rand.nextInt(10) + x);

        do {
            number2 = number0 + (rand.nextInt(10) + x);
            // System.out.println(number2 - number0);
            // System.out.println(number1 - number0);
        } while (number2 - number0 == number0 - number1);       // if the distance between the two numbers and the main game is the same, the second numbers gets regenerated.

        firstnumb.setText(String.valueOf(number1));         // set the value for the first number in the FXML
        secondnumb.setText(String.valueOf(number2));        // set the value for the second number ( the biggest ) in the FXML
    }

    public void firstChoosen(ActionEvent event) throws IOException {
        //  System.out.println("h1");
        if (number2 - number0 < number0 - number1) // if the second numebr is bigger than the first one
        {
            comment.setText("No");
            manageLose();
            //scoretext.setText(String.valueOf(score));
            number0 = number2;
            labelTime(label_time_down);
        } else {
            manageWin();
            number0 = number1;
        }
        //System.out.println(score);
        scoretext.setText(String.valueOf(score));
        // System.out.println("h5");
        gamenumber.setText(String.valueOf(number0));
        // System.out.println("h6");
        generateTwoNumb();

    }

    public void secondChoosen(ActionEvent event) throws IOException {
        // System.out.println("h1");
        if (number2 - number0 < number0 - number1) // if the distance - second numebr is smaller than the first one
        {
            comment.setText("Yes");
            manageWin();

            number0 = number2;
            //System.out.println("h2");

        } else {
            comment.setText("No");
            manageLose();
            //scoretext.setText(String.valueOf(score));
            number0 = number1;
            labelTime(label_time_down);
        }
        // System.out.println(score);
        scoretext.setText(String.valueOf(score));
        // System.out.println("h5");
        gamenumber.setText(String.valueOf(number0));
        // System.out.println("h6");
        generateTwoNumb();

    }

    public void manageTime() {
        // 60 seconds from the beggining of the game
        timerlabel.textProperty().bind(timeSeconds.asString());

        if (timeline != null) {
            timeline.stop();

        }
        timeSeconds.set(gametime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(gametime),
                        new KeyValue(timeSeconds, 0))
        );
        System.out.println(timerlabel.getText());
        timeline.playFromStart();
        t = new Time(endgame);
        t.Timer(gametime);
        if (timeSeconds.getValue() <= 10) {
            timerlabel.setTextFill(Color.web("#0076a3"));
        }
    }

    public void manageWin() {
        score += score_won;
        score_won = score_won * 2;
        score_lost = score_lost * 2;
        x += (x / 2) - 1 ;           // increase width
        System.out.println("x is : " + x);
        if (gametime > 2) {
            gametime = timeSeconds.getValue() + 2;        // increase current time left
        }
        t.cancel();
        manageTime();
        labelTime(label_time_up);
    }

    public void manageLose() {
        if (score + score_lost > 0) {
            score += score_lost;
        }
        if (gametime > 2) {
            gametime = timeSeconds.getValue() - 2;        // increase current time left
        }
        t.cancel();
        manageTime();
        labelTime(label_time_down);
    }

    public void labelTime(Label upordown) {
        upordown.setVisible(true);
        new Time(upordown).Timer2(2);

    }
    
    public void tryAgain(MouseEvent event) throws IOException{
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("FXML_PageGame.fxml"));
            System.out.println(loader);
            Parent homepage_parent = (Parent) loader.load();
            Scene homepage_scene = new Scene(homepage_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            app_stage.hide();
            app_stage.setScene(homepage_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class HangmanController {
    @FXML
    public HBox rootContainer;
    @FXML
    public VBox hangSceneContainer;
    @FXML
    public HBox answerContainer;
    @FXML
    public GridPane keysGrid;

    @FXML
    private SVGPath hangmanSvg;
    private int currentStage = 9;

    private final String[] hangmanStages = {
            "",
            "M 50,80 L 100,80",
            "M 50,80 L 100,80 M 50,190 L 130,190",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30 M 130,50 m -20,0 a 20,20 0 1,0 40,0 a 20,20 0 1,0 -40,0",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30 M 130,50 m -20,0 a 20,20 0 1,0 40,0 a 20,20 0 1,0 -40,0 M 130,70 L 130,130",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30 M 130,50 m -20,0 a 20,20 0 1,0 40,0 a 20,20 0 1,0 -40,0 M 130,70 L 130,130 M 130,70 L 90,90",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30 M 130,50 m -20,0 a 20,20 0 1,0 40,0 a 20,20 0 1,0 -40,0 M 130,70 L 130,130 M 130,70 L 90,90 M 130,70 L 170,90",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30 M 130,50 m -20,0 a 20,20 0 1,0 40,0 a 20,20 0 1,0 -40,0 M 130,70 L 130,130 M 130,70 L 90,90 M 130,70 L 170,90 M 130,130 L 90,170",
            "M 50,190 L 130,190 M 90,10 L 90,50 M 50,10 L 130,10 M 90,10 L 90,30 M 130,50 m -20,0 a 20,20 0 1,0 40,0 a 20,20 0 1,0 -40,0 M 130,70 L 130,130 M 130,70 L 90,90 M 130,70 L 170,90 M 130,130 L 90,170 M 130,130 L 170,170",
    };

    public void initialize(){
        // Make The Input Keys.
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int column = 0;
        int row = 0;
        for (char letter : letters.toCharArray()) {
            Button button = new Button(String.valueOf(letter));
            button.getStyleClass().add("inpLetterBtn");
            button.setOnAction(e -> handleLetterClick(letter));
            keysGrid.add(button, column, row);

            column++;
            if (column == 9) { // Adjust the number to fit your preferred layout
                column = 0;
                row++;
            }
        }

        setPart();
    }

    private void handleLetterClick(char letter) {
        System.out.println("Letter clicked: " + letter);
    }

    public void setPart(){
        hangmanSvg.setContent(hangmanStages[currentStage]);
    }

    // @FXML
    // public void inputAct(ActionEvent actionEvent) {
    //     currentStage = Integer.parseInt(inp.getText());
    //     setPart();
    // }
}
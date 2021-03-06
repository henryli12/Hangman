import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.net.URISyntaxException;

public class Hangman extends Application {
    static Game game;
    static Button saveButton, newButton, loadButton, exitButton, startGame;
    static Label hangman, guesses;
    static ImageView displayIV;
    static LetterLabels letterLabels;
    static TilePane rightAlpha, displayWord;
    AlphaButtons alphaButton;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, URISyntaxException {
        primaryStage.setTitle("Hangman");
        primaryStage.getIcons().add(new Image(new FileInputStream(Files.getFile("icons/smiley.png"))));
        //Top;navbar thingy
        saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #CEC4C2");
        saveButton.setDisable(true);
        ImageView saveIcon = new ImageView(new Image(new FileInputStream(Files.getFile("icons/saveIcon.png"))));
        saveIcon.setFitWidth(15);
        saveIcon.setFitHeight(15);
        saveButton.setGraphic(saveIcon);
        newButton = new Button("New");
        newButton.setStyle("-fx-background-color: #CEC4C2");
        ImageView newIcon = new ImageView(new Image(new FileInputStream(Files.getFile("icons/newIcon.png"))));
        newIcon.setFitWidth(15);
        newIcon.setFitHeight(15);
        newButton.setGraphic(newIcon);
        loadButton = new Button("Load");
        loadButton.setStyle("-fx-background-color: #CEC4C2");
        ImageView loadIcon = new ImageView(new Image(new FileInputStream(Files.getFile("icons/loadIcon.png"))));
        loadIcon.setFitWidth(15);
        loadIcon.setFitHeight(15);
        loadButton.setGraphic(loadIcon);
        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #CEC4C2");
        ImageView exitIcon = new ImageView(new Image(new FileInputStream(Files.getFile("icons/exitIcon.png"))));
        exitIcon.setFitWidth(15);
        exitIcon.setFitHeight(15);
        exitButton.setGraphic(exitIcon);
        HBox topNav = new HBox();
        topNav.setSpacing(15);
        topNav.setPadding(new Insets(20,20,20,20));
        topNav.setStyle("-fx-background-color: #181716");
        topNav.getChildren().addAll(saveButton, newButton, loadButton, exitButton);

        StackPane topLabel = new StackPane();
        hangman = new Label("Hangman");
        hangman.setFont(new Font("Arial", 30));
        hangman.setVisible(false);
        startGame = new Button("Start Game");
        startGame.setVisible(false);
        topLabel.getChildren().addAll(hangman,startGame);
        VBox top = new VBox();
        top.getChildren().addAll(topNav,topLabel);

        //Right Side; letters
        guesses = new Label();
        rightAlpha = new TilePane();
        displayWord = new TilePane();
        VBox right = new VBox();
        right.setVisible(false);
        alphaButton = new AlphaButtons();
        rightAlpha.setOrientation(Orientation.HORIZONTAL);
        rightAlpha.setPrefRows(4);
        rightAlpha.setPrefWidth(250);
        rightAlpha.setPrefTileHeight(35);
        rightAlpha.setPrefTileWidth(35);
        rightAlpha.getChildren().addAll(alphaButton.alphaButtons);
        displayWord.setOrientation(Orientation.HORIZONTAL);
        displayWord.setPrefTileWidth(30);
        displayWord.setPrefTileHeight(30);
        displayWord.setPrefWidth(300);
        right.getChildren().addAll(guesses, displayWord, rightAlpha);

        //Left side; hangman
        displayIV = new ImageView(new Image(new FileInputStream(Files.getFile("images/miss0.png"))));
        displayIV.setFitHeight(400);
        displayIV.setFitWidth(400);
        StackPane left = new StackPane();
        left.getChildren().addAll(displayIV);
        left.setVisible(false);
        startGame.setOnAction(e->{
            right.setVisible(true);
            left.setVisible(true);
            hangman.setVisible(true);
            startGame.setVisible(false);
            rightAlpha.setDisable(false);
        });
        newButton.setOnAction(e->{
            boolean close = true;
            if(!saveButton.isDisabled()) close = SaveWindow.display(game);
            if(close) {
                try {
                    game = new Game(Files.chooseWord());
                    startGame.setVisible(true);
                    right.setVisible(false);
                    left.setVisible(false);
                    hangman.setVisible(false);
                    displayWord.getChildren().clear();
                    letterLabels = new LetterLabels(game.getWord().length());
                    displayWord.getChildren().addAll(letterLabels.labels);
                    rightAlpha.setDisable(true);
                    for (int i = 0; i < alphaButton.alphaButtons.length; i++) {
                        alphaButton.alphaButtons[i].setDisable(false);
                    }
                    guesses.setText("Remaining Guesses: " + game.getRemainingGuesses());
                    displayIV.setImage(new Image(new FileInputStream(Files.getFile("images/miss0.png"))));
                    topNav.requestFocus();
                    saveButton.setDisable(true);
                } catch (NullPointerException | FileNotFoundException | URISyntaxException ex) {
                    System.out.println("Error");
                }
            }
        });
        saveButton.setOnAction(e -> {
            SaveWindow.display(game);
        });
        loadButton.setOnAction(e -> {
            boolean close = true;
            Game temp;
            if(!saveButton.isDisabled()) close = SaveWindow.display(game);
            if(close){
                try {
                    if((temp = Files.load(primaryStage, game)) != null) {
                        game = temp;
                        displayWord.getChildren().clear();
                        letterLabels = new LetterLabels(game.getWord().length());
                        letterLabels.fillIn(game.getWordList());
                        displayWord.getChildren().addAll(letterLabels.labels);
                        right.setVisible(true);
                        left.setVisible(true);
                        hangman.setVisible(true);
                        startGame.setVisible(false);
                        rightAlpha.setDisable(false);
                        for (int i = 0; i < alphaButton.alphaButtons.length; i++) {
                            if (game.getGuessed().contains(alphaButton.alphaButtons[i].getText()))
                                alphaButton.alphaButtons[i].setDisable(true);
                            else alphaButton.alphaButtons[i].setDisable(false);
                        }
                        guesses.setText("Remaining Guesses: " + game.getRemainingGuesses());
                        displayIV.setImage(new Image(new FileInputStream(Files.getFile("images/miss" + (10 - game.getRemainingGuesses()) + ".png"))));
                        topNav.requestFocus();
                        saveButton.setDisable(true);
                    }
                } catch (FileNotFoundException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitButton.setOnAction(e->{
            boolean close = true;
            if(!saveButton.isDisabled()) close = SaveWindow.display(game);
            if(close) primaryStage.close();
        });
        //Root
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setRight(right);
        root.setLeft(left);
        root.setOnKeyPressed(e->{
            try{
                if(e.getCode() != null){
                    alphaButton.alphaButtons[(int)(e.getText().charAt(0)-97)].fire();
                    e.consume();
                }
            }catch (ArrayIndexOutOfBoundsException | NullPointerException | StringIndexOutOfBoundsException e1){return;}

        });
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
        topNav.requestFocus();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
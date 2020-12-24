import javafx.scene.control.Button;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class AlphaButtons {
    Button[] alphaButtons;
    public AlphaButtons(){
        alphaButtons = new Button[26];
        for(int i = 0; i < alphaButtons.length; i++){
            alphaButtons[i] = new Button();
            alphaButtons[i].setText((char)(65+i)+"");
            int finalI = i;
            alphaButtons[i].setOnAction(e -> {
                String letter = alphaButtons[finalI].getText();
                Hangman.game.addGuess(letter);
                ArrayList<Integer> indices = Hangman.game.findLetter(letter.charAt(0));
                Hangman.saveButton.setDisable(false);
                alphaButtons[finalI].setDisable(true);
                if(indices.size()==0){
                    try {
                        Hangman.game.wrongGuess();
                        Hangman.guesses.setText("Remaining Guesses: " + Hangman.game.getRemainingGuesses());
                        URL filepath = Files.class.getResource("miss" + (10 - Hangman.game.getRemainingGuesses())+ ".png");
                        Hangman.displayIV.setImage(new Image(new FileInputStream(Files.getFile("images/miss" + (10 - Hangman.game.getRemainingGuesses())+".png"))));
                        if(Hangman.game.getRemainingGuesses() == 0){
                            Hangman.saveButton.setDisable(true);
                            GameoverWindow.display("Game Over", "You lost (the word was \"" +
                                    Hangman.game.getWord() + "\")");
                            Hangman.rightAlpha.setDisable(true);
                            Hangman.letterLabels.showMissing(Hangman.game.getWord());

                        }
                    } catch (FileNotFoundException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    for (Integer index : indices) {
                        Hangman.game.wordList[index] = letter;
                        Hangman.letterLabels.setLabels(index, letter);
                        if (Hangman.game.isWin()) {
                            Hangman.rightAlpha.setDisable(true);
                            Hangman.saveButton.setDisable(true);
                            GameoverWindow.display("Game Over", "You won.");
                        }
                    }
                }
            });
        }
    }

}

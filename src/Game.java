import java.util.ArrayList;

public class Game {
    private int remainingGuesses;
    private String word;
    private ArrayList<String> guessed;
    String[] wordList;
    public Game(){
        this.remainingGuesses = 10;
        this.word = "";
        this.guessed = new ArrayList<>();
        this.wordList = new String[0];
    }
    public Game(String word){
        this.remainingGuesses = 10;
        this.word = word;
        this.guessed = new ArrayList<>();
        this.wordList = new String[word.length()];
    }
    public Game(int remainingGuesses, String word, ArrayList<String> guessed, String[] wordList){
        this.remainingGuesses = remainingGuesses;
        this.word = word;
        this.guessed = guessed;
        this.wordList = wordList;
    }

    public void wrongGuess(){
        this.remainingGuesses -= 1;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public void setRemainingGuesses(int remainingGuesses) {
        this.remainingGuesses = remainingGuesses;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void addGuess(String letter){
        this.guessed.add(letter);
    }

    public ArrayList<String> getGuessed() {
        return guessed;
    }

    public String guessedToString(){
        if(guessed.size() == 0) return "";
        String result = "";
        for(int i = 0; i < this.guessed.size() - 1; i++){
            result = result + this.guessed.get(i) +",";
        }
        result = result + this.guessed.get(this.guessed.size() - 1);
        return result;
    }

    public void setGuessed(ArrayList<String> guessed) {
        this.guessed = guessed;
    }

    public String[] getWordList() {
        return wordList;
    }

    public void setWordList(String[] wordList) {
        this.wordList = wordList;
    }

    public String wordListToString(){
        if(wordList.length == 0) return "";
        String result = "";
        for(int i = 0; i < this.wordList.length - 1; i++){
            if(this.wordList[i] == null) result = result + "_,";
            else result = result + this.wordList[i] + ",";
        }
        if(this.wordList[this.wordList.length - 1] == null) result = result + "_";
        else result = result + this.wordList[this.wordList.length - 1];
        return result;
    }

    public ArrayList<Integer> findLetter(char letter){
        ArrayList<Integer> indices = new ArrayList<>();
        for(int i = 0; i < word.length();i++){
            if(Character.toUpperCase(this.word.charAt(i)) == letter){
                indices.add(i);
            }
        }
        return indices;
    }

    public String toString(){
        return "RemainingGuess: " + this.remainingGuesses
                + "\nWord: " + this.word
                + "\nGuessedLetters: " + this.guessedToString()
                + "\nWordDisplay: " + this.wordListToString();
    }

    public boolean isWin(){
        for(int i = 0; i < this.word.length(); i++){
            if(wordList[i] == null) return false;
        }
        return true;
    }

}

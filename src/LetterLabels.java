import javafx.scene.control.Label;

public class LetterLabels {

    public Label[] labels;
    public LetterLabels(int wordLength){
        labels = new Label[wordLength];
        for(int i = 0; i < wordLength; i++){
            labels[i] = new Label("?");
            labels[i].setStyle("-fx-background-color: White;" +
                    " -fx-pref-width:20;" +
                    " -fx-pref-height:20; " +
                    " -fx-alignment: center; ");
        }
    }

    public void setLabels(int index, String text){
        this.labels[index].setText(text);
    }

    public void showMissing(String word){
        for(int j = 0 ; j < this.labels.length; j++){
            if(this.labels[j].getText().equals("?")){
                this.labels[j].setText(Character.toUpperCase(word.charAt(j)) + "");
                this.labels[j].setStyle("-fx-background-color: Gray;" +
                        " -fx-pref-width:20;" +
                        " -fx-pref-height:20; " +
                        " -fx-alignment: center; ");

            }
        }
    }
    public void fillIn(String[] word){
        for(int j = 0 ; j < this.labels.length; j++){
            if(word[j] != null){
                this.labels[j].setText(word[j].toUpperCase());
            }
        }
    }

}

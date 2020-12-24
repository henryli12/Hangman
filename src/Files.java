import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class Files {
    public static String chooseWord() {
        try {
            ArrayList<String> listWords = new ArrayList<>();
            URI filepath = Files.class.getResource("words.txt").toURI();
            File f = new File(filepath);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while((line = reader.readLine()) != null){
                listWords.add(line);
            }
            int index = (int)(Math.random()* (listWords.size()));
            return listWords.get(index);
        }catch(NullPointerException e){
            Alert.display("Error", "words.txt not found");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static File getFile(String file) throws URISyntaxException {
        URI path = Hangman.class.getResource(file).toURI();
        return new File(path);
    }
    public static boolean save(Stage stage, Game game){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("hng", "*.hng")
        );
        File file = fc.showSaveDialog(stage);
        if (file != null){
            try{
                PrintWriter writer = new PrintWriter(file);
                writer.println(game);
                writer.close();
                return true;
            }catch (Exception ex){ System.out.println("Error");}
        }else{
            Alert.display("Error", "Invalid File Name!!!");
        }
        return false;
    }
    public static Game load(Stage stage, Game game){
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("hng", "*.hng")
            );
            File file = fc.showOpenDialog(stage);
            if(file != null) {
                BufferedReader reader = new BufferedReader((new FileReader(file)));
                String line;
                ArrayList<String> info = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    String[] temp = line.split(" ");
                    info.add(temp[1]);
                }
                int remaining = Integer.parseInt(info.get(0));
                String word = info.get(1);
                ArrayList<String> guessed = new ArrayList<>(Arrays.asList((info.get(2).split(","))));
                String[] wordList = info.get(3).split(",");
                for (int i = 0; i < word.length(); i++) {
                    if (wordList[i].equals("_")) wordList[i] = null;
                }
                return new Game(remaining, word, guessed, wordList);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

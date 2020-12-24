import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;


public class SaveWindow {
    public static boolean display(Game game){
        AtomicBoolean close = new AtomicBoolean(true);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Save");
        Label messageLabel = new Label("Do you want to save?");
        Button yes = new Button("Yes");
        Button no = new Button("No");
        Button cancel = new Button("Cancel");
        yes.setOnAction(e ->
            {
                boolean saved = Files.save(window, game);
                if(!saved){
                    window.close();
                    display(game);
                }
                Hangman.saveButton.setDisable(true);
                window.close();
            });
        no.setOnAction(e -> {
            window.close();
        });
        cancel.setOnAction(e -> {
            close.set(false);
            window.close();
        });
        VBox root = new VBox(10);
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(yes, no, cancel);
        buttons.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(messageLabel,buttons);
        Scene scene = new Scene(root, 300, 150);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        return close.get();
    }
}

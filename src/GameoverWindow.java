import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameoverWindow{
    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Label messageLabel = new Label(message);
        Button close = new Button("CLOSE");
        close.setOnAction(e -> window.close());
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(messageLabel, close);
        Scene scene = new Scene(root, 300, 150);
        window.setScene(scene);
        window.setResizable(false);
        window.show();

    }
}

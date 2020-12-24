import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;

public class Alert {

    public static void display(String title,String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Label m = new Label(message);
        Button ok = new Button("ok");
        ok.setOnAction(e-> window.close());
        layout.getChildren().addAll(m,ok);
        Scene scene = new Scene(layout,300,150);
        window.setScene(scene);
        window.showAndWait();
    }
}
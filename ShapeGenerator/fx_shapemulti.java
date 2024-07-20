import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class fx_shapemulti extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    private Canvas fx_canvas;
    private volatile boolean fx_running;

    private fx_runner fx_runner;
    private Button fx_startButton;

    public void start(Stage stage) {
        fx_canvas = new Canvas(700, 480);
        fx_redraw();
        fx_startButton = new Button("start!");
        fx_startButton.setOnAction(e -> fx_doStartorStop());
        HBox fx_bottom = new HBox(fx_startButton);
        fx_bottom.setStyle("-fx-padding:6px; -fx-border-color:black;");
        fx_bottom.setAlignment(Pos.CENTER);
        BorderPane fx_root = new BorderPane(fx_canvas);
        fx_root.setBottom(fx_bottom);
        Scene fx_scene = new Scene(fx_root);
        stage.setScene(fx_scene);
        stage.setTitle("Click start to Make Random Art!");
        stage.setResizable(false);
        stage.show();
    }

    private class fx_runner extends Thread {
        public void run() {
            while (fx_running) {
                Platform.runLater(() -> fx_redraw());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    private void fx_redraw() {
        GraphicsContext fx_g = fx_canvas.getGraphicsContext2D();
        double fx_width = fx_canvas.getWidth();
        double fx_height = fx_canvas.getHeight();

        if (!fx_running) {
            fx_g.setFill(Color.WHITE);
            fx_g.fillRect(0, 0, fx_width, fx_height);
            return;
        }
        Color fx_randomGray = Color.hsb(1, 0, Math.random());
        fx_g.setFill(fx_randomGray);
        fx_g.fillRect(0, 0, fx_width, fx_height);
        int fx_artType = (int) (3 * Math.random());

        switch (fx_artType) {
            case 0:
                fx_g.setLineWidth(2);
                for (int i = 0; i < 500; i++) {
                    int x1 = (int) (fx_width * Math.random());
                    int y1 = (int) (fx_height * Math.random());
                    int x2 = (int) (fx_width * Math.random());
                    int y2 = (int) (fx_height * Math.random());
                    Color randomHue = Color.hsb(360 * Math.random(), 1, 1);
                    fx_g.setStroke(randomHue);
                    fx_g.strokeLine(x1, y1, x2, y2);

                }
                break;
            case 1:
                for (int i = 0; i < 200; i++) {
                    int fx_centerX = (int) (fx_width * Math.random());
                    int fx_centerY = (int) (fx_height * Math.random());
                    Color fx_randomHue = Color.hsb(360 * Math.random(), 1, 1);
                    fx_g.setStroke(fx_randomHue);
                    fx_g.strokeLine(fx_centerX - 50, fx_centerY - 50, 100, 100);
                }
                break;
            default:
                fx_g.setStroke(Color.BLACK);
                fx_g.setLineWidth(4);
                for (int i = 0; i < 25; i++) {
                    int centerX = (int) (fx_width * Math.random());
                    int centerY = (int) (fx_height * Math.random());
                    int size = 30 + (int) (170 * Math.random());
                    Color randomColor = Color.hsb(360 * Math.random(), Math.random(), Math.random());
                    fx_g.setFill(randomColor);
                    fx_g.fillRect(centerX - size / 2, centerY - size / 2, size, size);
                    fx_g.strokeRect(centerX - size / 2, centerY - size / 2, size, size);

                }
                break;
        }
    }

    private void fx_doStartorStop() {
        if (fx_running == false) {
            fx_startButton.setText("stop");
            fx_runner = new fx_runner();
            fx_running = true;
            fx_runner.start();
        } else {
            fx_startButton.setDisable(true);

            fx_running = false;
            fx_redraw();
            fx_runner.interrupt();

            try {
                fx_runner.join(1000);

            } catch (InterruptedException e) {

            }
            fx_runner = null;

            fx_startButton.setText("Start");
            fx_startButton.setDisable(false);
        }
    }
}
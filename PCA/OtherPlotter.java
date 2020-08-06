import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;

public class OtherPlotter extends Application {
    private static List<String[]> scoresData;

    public static void instance(List<String[]> scoresData) {
        OtherPlotter.scoresData = scoresData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis(0, 1000, 100);
        xAxis.setLabel("X");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y");

        ScatterChart<String, Number> scatterChart = new ScatterChart(xAxis, yAxis);

        XYChart.Series scores = new XYChart.Series();
        for (int i = 0; i < scoresData.size(); i++) {
            scores.getData().add(new XYChart.Data(i + 1,
                    Float.parseFloat(scoresData.get(i)[1])));
        }
        scores.setName("Scores");

        scatterChart.getData().addAll(scores);

        Group root = new Group(scatterChart);

        //Creating a scene object
        Scene scene = new Scene(root, 1400, 800);
        scatterChart.setPrefSize(1400, 800);

        //Setting title to the Stage
        primaryStage.setTitle("GA");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}

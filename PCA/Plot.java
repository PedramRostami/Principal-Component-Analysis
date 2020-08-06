import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Plot extends Application {
    private static List<Float[]> realData;
    private static List<Float[]> predictedData;

    public static void instance(List<Float[]> realData, List<Float[]> predictedData) {
        Plot.realData = realData;
        Plot.predictedData = predictedData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis(0, 50, 1);
        xAxis.setLabel("X");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y");

        ScatterChart<String, Number> scatterChart = new ScatterChart(xAxis, yAxis);

        XYChart.Series real = new XYChart.Series();
        for (int i = 0; i < realData.size(); i++) {
            real.getData().add(new XYChart.Data(Float.valueOf(realData.get(i)[0]),
                    Float.valueOf(realData.get(i)[1])));
        }
        real.setName("Real");

        XYChart.Series predicted = new XYChart.Series();
        for (int i = 0; i < predictedData.size(); i++) {
            predicted.getData().add(new XYChart.Data(Float.valueOf(predictedData.get(i)[0]),
                    Float.valueOf(predictedData.get(i)[1])));
        }
        predicted.setName("Predicted");



        scatterChart.getData().addAll(real, predicted);

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

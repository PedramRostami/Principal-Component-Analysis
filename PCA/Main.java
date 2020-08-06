import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        FileHandler fileHandler = new FileHandler();
        try {
            List<String[]> data = fileHandler.readCSV("Dataset\\Dataset2.csv");
            List<String[]> scores = new ArrayList<>();
            data.remove(0);
            ES es = ES.init(10, 70, 0.1f, 0.01f, 0.01f, data);
            Chromosome.init(2, 1000, -1000);
            List<Chromosome> population = es.generateInitialPopulation();
            population = es.evaluateNewGenerations(population);
            while (!es.checkConvergence(population)){
                String[] tempScores = new String[3];
                List<Chromosome> newSeeds = es.generateNewSeeds(population);
                newSeeds = es.evaluateNewGenerations(newSeeds);
                population = es.chooseNewGeneration(population, newSeeds);
                tempScores[0] = population.get(0).getScore().toString();
                tempScores[2] = population.get(population.size() - 1).getScore().toString();
                tempScores[1] = Double.toString(0d);
                Double t = 0d;
                for (int j = 0; j < population.size(); j++) {
                    t += population.get(j).getScore();
                }
                t /= population.size();
                tempScores[1] = t.toString();
                scores.add(tempScores);
            }
            population = es.evaluateNewGenerations(population);
            Chromosome best = population.get(0);
            List<Float[]> realData = new ArrayList<>();
            List<Float[]> predictedData = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                Float[] temp = new Float[data.get(i).length];
                for (int j = 0; j < data.get(i).length; j++) {
                    temp[j] = Float.valueOf(data.get(i)[j]);
                }
                realData.add(temp);

                Float[] temp1 = new Float[data.get(i).length];
                temp1[0] = temp[0];
                temp1[1] = (float) ((best.getGenes()[1] / best.getGenes()[0]) * temp1[0]);
                predictedData.add(temp1);
            }
            FileWriter csvWriter = new FileWriter("scores.csv");
            String header = String.format("%s , %s , %s , %s \n", "Generation", "Best Score", "Average Score", "Worst Score");
            csvWriter.append(header);
            for (int i = 0; i < scores.size(); i++) {
                String line = String.format("%s , %s , %s , %s \n", Integer.toString(i), scores.get(i)[0], scores.get(i)[1], scores.get(i)[2]);
                csvWriter.append(line);
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Number of generations : " + scores.size());
            System.out.println("best score, worst score and average score of each generation stored in \"scores.csv\"");
            Plot.instance(realData, predictedData);
            Plot.main(args);

//            OtherPlotter.instance(scores);
//            OtherPlotter.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome>{
    public static Integer geneLen = null;
    public static Double maxGeneValue = null;
    public static Double minGeneValue = null;

    private Double[] genes;
    private Double score;

    public static void init(int geneLen, double maxGeneValue, double minGeneValue) {
        Chromosome.geneLen = geneLen;
        Chromosome.maxGeneValue = maxGeneValue;
        Chromosome.minGeneValue = minGeneValue;
    }

    public Chromosome() {
        genes = new Double[geneLen];
        for (int i = 0; i < geneLen; i++)
            genes[i] = (Math.random() * (maxGeneValue - minGeneValue)) + minGeneValue;
        score = 0d;
    }

    public Chromosome(Double[] genes) {
        this.genes = new Double[geneLen];
        for (int i = 0; i < geneLen; i++)
            this.genes[i] = genes[i];
        score = null;
    }

    public void evaluate(List<String[]> data){
        List<Double> z = new ArrayList<>();
        double genesAmount = 0d;
        for (int i = 0; i < geneLen; i++) {
            genesAmount += Math.pow(genes[i], 2);
        }
        genesAmount = Math.sqrt(genesAmount);
        for (int i = 0; i < data.size(); i++) {
            double result = 0;
            for (int j = 0; j < genes.length; j++)
                result +=  (genes[j] / genesAmount) * Double.parseDouble(data.get(i)[j]);
            z.add(result);
        }
        double avg = 0;
        for (int i = 0; i < z.size(); i++)
            avg += (z.get(i) / z.size());
        double deviation = 0;
        for (int i = 0; i < z.size(); i++)
            deviation += Math.pow((avg - z.get(i)), 2);
        deviation /= z.size();
        deviation = Math.sqrt(deviation);
        score = deviation;
        return;
    }

    public Double[] getGenes() {
        return genes;
    }

    public Double getScore() {
        return score;
    }

    public void mutate(int geneIndex, double mean, double variance) {
        Random random = new Random();
        genes[geneIndex] += random.nextGaussian() + Math.sqrt(variance) + mean;
    }

    @Override
    public boolean equals(Object obj) {
        for (int i = 0; i < geneLen; i++) {
            try {
                if (!genes[i].equals(((Chromosome) obj).genes[i]))
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String genes = getGenes()[0].toString();
        for (int i = 1; i < getGenes().length; i++) {
            genes += "#" + getGenes()[i].toString();
        }
        return genes;
    }

    @Override
    protected Chromosome clone() throws CloneNotSupportedException {
        Chromosome chromosome = new Chromosome(genes);
        chromosome.score = this.score;
        return chromosome;
    }


    @Override
    public int compareTo(Chromosome o) {
        return this.score.compareTo(o.getScore());
    }
}

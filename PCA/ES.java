import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ES {
    private int lambda;
    private int mu;
    private float crossoverProbability;
    private float mutationRate;
    private float variance;
    private List<String[]> data;



    public static ES init(int lambda, int mu, float crossoverProbability, float mutationRate, float variance, List<String[]> data) {
        ES es = new ES();
        es.lambda = lambda;
        es.mu = mu;
        es.crossoverProbability = crossoverProbability;
        es.mutationRate = mutationRate;
        es.variance = variance;
        es.data = data;
        return es;
    }

    public List<Chromosome> generateInitialPopulation() {
        List<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < mu; i++) {
            chromosomes.add(new Chromosome());
        }
        return chromosomes;
    }


    public List<Chromosome> generateNewSeeds(List<Chromosome> population)
            throws CloneNotSupportedException {
        int i = 0;
        List<Chromosome> newSeeds = new ArrayList<>();
        while (i < lambda) {
            List<Chromosome> candidateParents = new ArrayList<>();
            for (int j = 0; j < population.size() ; j++) {
                double chosenProbability = Math.random();
                if (chosenProbability < crossoverProbability) {
                    candidateParents.add(population.get(j));
                }
            }
            if (candidateParents.size() < 2) {
                continue;
            } else {
                candidateParents.sort(Collections.reverseOrder());
                Chromosome newSeed = crossover(candidateParents.get(0),
                        candidateParents.get(1));
                newSeed = mutation(newSeed);
                newSeeds.add(newSeed);
                i++;
            }
        }
        return newSeeds;
    }



    public List<Chromosome> evaluateNewGenerations(List<Chromosome> newSeeds) {
        for (int i = 0; i < newSeeds.size(); i++) {
            newSeeds.get(i).evaluate(data);
        }
        return newSeeds;
    }

    public List<Chromosome> chooseNewGeneration(List<Chromosome> population, List<Chromosome> newSeeds) throws CloneNotSupportedException {
        List<Chromosome> allIndividuals = new ArrayList<>();
        allIndividuals.addAll(population);
        allIndividuals.addAll(newSeeds);
        allIndividuals.sort(Collections.reverseOrder());
        return allIndividuals.subList(0, population.size());
    }

    public boolean checkConvergence(List<Chromosome> population) {
        for (int i = 0; i < population.size(); i++) {
            if (!population.get(i).equals(population.get(0)))
                return false;
        }
        return true;
    }


    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int crossPoint = (int) (Math.random() * Chromosome.geneLen);
        Double[] genes = new Double[Chromosome.geneLen];
        for (int i = 0; i < crossPoint; i++)
            genes[i] = parent1.getGenes()[i];
        for (int i = crossPoint; i < Chromosome.geneLen; i++)
            genes[i] = parent2.getGenes()[i];
        return new Chromosome(genes);
    }

    private Chromosome mutation(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getGenes().length; i++) {
            if (Math.random() < mutationRate) {
                chromosome.mutate(i, 0, variance);
            }
        }
        return chromosome;
    }


}

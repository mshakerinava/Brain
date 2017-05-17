import edu.sharif.ce.mshakerinava.brain.networks.MLP;
import edu.sharif.ce.mshakerinava.brain.neurons.Perceptron;
import edu.sharif.ce.mshakerinava.brain.neurons.Sigmoid;
import edu.sharif.ce.mshakerinava.brain.neurons.SquareLoss;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        /* construct neural network */
        MLP neuralNet = new MLP(2, 1, new SquareLoss());
        neuralNet.addPerceptron(new Perceptron(2, new Sigmoid()), 0);
        neuralNet.fullyConnect();
        /* train neural network to learn the 'OR' function */
        Random gen = new Random();
        for (int i = 0; i < 7000; i += 1) {
            int x0 = gen.nextInt(2);
            int x1 = gen.nextInt(2);
            double[] x = {x0, x1};
            double[] y = {x0 | x1};
            neuralNet.update(x, y, 7.0);
            neuralNet.updateHistory();
        }
        /* show results */
        for (int x0 = 0; x0 < 2; x0 += 1)
            for (int x1 = 0; x1 < 2; x1 += 1) {
                double[] x = {x0, x1};
                double y = neuralNet.evaluate(x)[0];
                System.out.println(x0 + " | " + x1 + " ~= " + y);
            }
        neuralNet.writeHistory("history.txt");
    }
}
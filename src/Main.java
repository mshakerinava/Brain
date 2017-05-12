import edu.sharif.ce.mshakerinava.brain.NeuralNetwork;
import edu.sharif.ce.mshakerinava.brain.neurons.Lin;
import edu.sharif.ce.mshakerinava.brain.neurons.Loss;
import edu.sharif.ce.mshakerinava.brain.neurons.Sig;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        /* construct neural network */
        NeuralNetwork neuralNet = new NeuralNetwork();
        neuralNet.addInput(2);
        neuralNet.addLayer(3);
        neuralNet.addNode(new Lin(2), 0);
        neuralNet.addNode(new Sig(), 1);
        neuralNet.addNode(new Loss(), 2);
        neuralNet.connectInput(0, 0, 0, 0);
        neuralNet.connectInput(1, 0, 0, 1);
        neuralNet.connectNode(0, 0, 0, 1, 0, 0);
        neuralNet.connectNode(1, 0, 0, 2, 0, 0);
        /* train neural network to learn the 'OR' function */
        Random gen = new Random();
        for (int i = 0; i < 1000; i += 1) {
            int x0 = gen.nextInt(2);
            int x1 = gen.nextInt(2);
            double[] x = {x0, x1};
            double[] y = {x0 | x1};
            neuralNet.update(x, y, 10.0);
        }
        /* show results */
        for (int x0 = 0; x0 < 2; x0 += 1)
            for (int x1 = 0; x1 < 2; x1 += 1) {
                double[] x = {x0, x1};
                double y = neuralNet.evaluate(x)[0];
                System.out.println(x0 + " | " + x1 + " ~= " + y);
            }
    }
}
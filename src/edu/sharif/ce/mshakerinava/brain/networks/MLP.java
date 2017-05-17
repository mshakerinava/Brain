package edu.sharif.ce.mshakerinava.brain.networks;

import edu.sharif.ce.mshakerinava.brain.base.Brain;
import edu.sharif.ce.mshakerinava.brain.base.Neuron;
import edu.sharif.ce.mshakerinava.brain.neurons.Perceptron;

/**
 * Multi-Layer Perceptron (MLP)
 */
public class MLP extends Brain {
    public MLP(int numberOfInputs, int numberOfLayers, Neuron lossFunction) {
        for (int i = 0; i < numberOfInputs; i += 1) addInput();
        for (int i = 0; i < numberOfLayers + 1; i += 1) addLayer();
        addNeuron(lossFunction, getNumberOfLayers() - 1);
    }

    public void addPerceptron(Perceptron perceptron, int layer) {
        addNeuron(perceptron, layer);
    }

    public void fullyConnect() {
        for (int i = 0; i < getNumberOfInputs(); i += 1)
            connectInput(i, 0, i);
        for (int i = 0; i < getNumberOfLayers() - 1; i += 1)
            for (int j = 0; j < getLayerSize(i); j += 1)
                connectNeuron(i, j, 0, i + 1, j);
    }
}

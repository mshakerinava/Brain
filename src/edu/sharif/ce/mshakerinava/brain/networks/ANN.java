package edu.sharif.ce.mshakerinava.brain.networks;

import edu.sharif.ce.mshakerinava.brain.base.Brain;
import edu.sharif.ce.mshakerinava.brain.base.Neuron;

public class ANN extends Brain {
    public ANN() {
        super();
    }

    /**
     * Adds an input synapse to the network.
     */
    public void addInput() {
        super.addInput();
    }

    /**
     * Adds n input synapses to the network.
     *
     * @param n The number of inputs to add to the network
     */
    public void addInput(int n) {
        super.addInput(n);
    }

    /**
     * Adds an empty layer of neurons to the end of the network.
     * The last layer must contain a single neuron that computes the loss function.
     */
    public void addLayer() {
        super.addLayer();
    }

    /**
     * Adds n empty layers of neurons to the end of the network.
     * The last layer must contain a single neuron that computes the loss function.
     *
     * @param n The number of layers to add to the network
     */
    public void addLayer(int n) {
        super.addLayer(n);
    }

    /**
     * Adds a neuron to a layer of the network.
     * The first half of the inputs to the loss function must be the network's output
     * and the second half must be the expected output.
     *
     * @param neuron The neuron to be added to the network
     * @param layer  The index of the layer the neuron is being added to
     */
    public void addNeuron(Neuron neuron, int layer) {
        super.addNeuron(neuron, layer);
    }

    /**
     * Connects a network input (lhs) to a neuron (rhs).
     *
     * @param lhsIndex Input of network
     * @param rhsLayer Layer of neuron
     * @param rhsIndex Index of neuron
     * @param rhsInput Input of neuron
     */
    public void connectInput(int lhsIndex, int rhsLayer, int rhsIndex, int rhsInput) {
        super.connectInput(lhsIndex, rhsLayer, rhsIndex, rhsInput);
    }

    /**
     * Connects a network input to all neurons in a layer (rhs).
     *
     * @param lhsIndex Input of network
     * @param rhsLayer Layer of neurons
     * @param rhsInput Input of neurons
     */
    public void connectInput(int lhsIndex, int rhsLayer, int rhsInput) {
        super.connectInput(lhsIndex, rhsLayer, rhsInput);
    }

    /**
     * Connects the output of a neuron (lhs) to the input of a neuron (rhs).
     *
     * @param lhsLayer  Layer of lhs neuron
     * @param lhsIndex  Index of lhs neuron
     * @param lhsOutput Output of lhs neuron
     * @param rhsLayer  Layer of rhs neuron
     * @param rhsIndex  Index of rhs neuron
     * @param rhsInput  Input of rhs neuron
     */
    public void connectNeuron(int lhsLayer, int lhsIndex, int lhsOutput, int rhsLayer, int rhsIndex, int rhsInput) {
        super.connectNeuron(lhsLayer, lhsIndex, lhsOutput, rhsLayer, rhsIndex, rhsInput);
    }

    /**
     * Connects the output of a neuron (lhs) to the input of all neurons in a layer (rhs).
     *
     * @param lhsLayer  Layer of lhs neuron
     * @param lhsIndex  Index of lhs neuron
     * @param lhsOutput Output of lhs neuron
     * @param rhsLayer  Layer of rhs neurons
     * @param rhsInput  Input of rhs neurons
     */
    public void connectNeuron(int lhsLayer, int lhsIndex, int lhsOutput, int rhsLayer, int rhsInput) {
        super.connectNeuron(lhsLayer, lhsIndex, lhsOutput, rhsLayer, rhsInput);
    }
}

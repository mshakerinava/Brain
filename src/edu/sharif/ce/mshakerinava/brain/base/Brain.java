package edu.sharif.ce.mshakerinava.brain.base;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Brain {
    protected ArrayList<Synapse> input;
    protected ArrayList<ArrayList<Node>> neurons;

    public Brain() {
        neurons = new ArrayList<>();
        input = new ArrayList<>();
    }

    /**
     * Copy[Forward/Backward]Synapses is used to copy the forward/backward values in the synapses to or from an array.
     * The syntax is very similar to System.arraycopy().
     */
    protected static void copyForwardSynapses(Synapse[] synapses, int srcPos, double[] values, int destPos, int length) {
        for (int i = 0; i < length; i += 1)
            values[destPos + i] = synapses[srcPos + i].forward;
    }

    protected static void copyForwardSynapses(double[] values, int srcPos, Synapse[] synapses, int destPos, int length) {
        for (int i = 0; i < length; i += 1)
            synapses[destPos + i].forward = values[srcPos + i];
    }

    protected static void copyBackwardSynapses(Synapse[] synapses, int srcPos, double[] values, int destPos, int length) {
        for (int i = 0; i < length; i += 1)
            values[destPos + i] = synapses[srcPos + i].backward;
    }

    protected static void copyBackwardSynapses(double[] values, int srcPos, Synapse[] synapses, int destPos, int length) {
        for (int i = 0; i < length; i += 1)
            synapses[destPos + i].backward = values[srcPos + i];
    }

    /**
     * Returns the number of inputs to the network.
     *
     * @return The number of inputs to the network
     */
    public int getNumberOfInputs() {
        return input.size();
    }

    /**
     * Returns the number of layers in the network.
     *
     * @return The number of layers in the network
     */
    public int getNumberOfLayers() {
        return neurons.size();
    }

    /**
     * Returns the number of neurons in the specified layer.
     *
     * @param index Index of the layer in question
     * @return The number of neurons in the specified layer
     */
    public int getLayerSize(int index) {
        return neurons.get(index).size();
    }

    /**
     * Returns the specified neuron.
     *
     * @param layer Index of the layer
     * @param index Index of the neuron
     * @return The requested neuron
     */
    public Neuron getNeuron(int layer, int index) {
        return neurons.get(layer).get(index).neuron;
    }

    /**
     * Calls <code>Neuron.reset()</code> to reset the weights of all neurons in the network.
     */
    public void reset() {
        for (ArrayList<Node> layer : neurons) {
            for (Node node : layer) node.neuron.reset();
        }
    }

    protected void addInput() {
        input.add(new Synapse());
    }

    protected void addInput(int n) {
        for (int i = 0; i < n; i += 1)
            addInput();
    }

    protected void addLayer() {
        neurons.add(new ArrayList<>());
    }

    protected void addLayer(int n) {
        for (int i = 0; i < n; i += 1)
            this.addLayer();
    }

    protected void addNeuron(Neuron neuron, int layer) {
        neurons.get(layer).add(new Node(neuron));
    }

    protected void connectInput(int lhsIndex, int rhsLayer, int rhsIndex, int rhsInput) {
        neurons.get(rhsLayer).get(rhsIndex).input[rhsInput] = input.get(lhsIndex);
    }

    protected void connectInput(int lhsIndex, int rhsLayer, int rhsInput) {
        int n = neurons.get(rhsLayer).size();
        for (int i = 0; i < n; i += 1)
            connectInput(lhsIndex, rhsLayer, i, rhsInput);
    }

    protected void connectNeuron(int lhsLayer, int lhsIndex, int lhsOutput, int rhsLayer, int rhsIndex, int rhsInput) {
        Synapse synapse = new Synapse();
        neurons.get(lhsLayer).get(lhsIndex).output[lhsOutput] = synapse;
        neurons.get(rhsLayer).get(rhsIndex).input[rhsInput] = synapse;
    }

    protected void connectNeuron(int lhsLayer, int lhsIndex, int lhsOutput, int rhsLayer, int rhsInput) {
        int n = neurons.get(rhsLayer).size();
        for (int i = 0; i < n; i += 1)
            connectNeuron(lhsLayer, lhsIndex, lhsOutput, rhsLayer, i, rhsInput);
    }

    /**
     * Loads all network weights from specified file.
     *
     * @param filename The file to load from
     */
    public void readWeights(String filename) {
        try {
            Scanner scanner = new Scanner(new FileReader(filename));
            for (ArrayList<Node> layer : neurons) {
                for (Node node : layer) {
                    Neuron neuron = node.neuron;
                    if (neuron.WEIGHT_LEN == 0) continue;
                    String[] ws = scanner.nextLine().split(" ");
                    double[] w = new double[neuron.WEIGHT_LEN];
                    for (int i = 0; i < ws.length; i += 1)
                        w[i] = Double.parseDouble(ws[i]);
                    neuron.setWeights(w);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all network weights to specified file.
     *
     * @param filename The file to save to
     */
    public void writeWeights(String filename) {
        File file = new File(filename);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            for (ArrayList<Node> layer : neurons) {
                for (Node node : layer) {
                    Neuron neuron = node.neuron;
                    if (neuron.WEIGHT_LEN == 0) continue;
                    double[] weights = neuron.getWeights();
                    for (double weight : weights) writer.write(Double.toString(weight) + " ");
                    writer.write("\n");
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies the network's function to the given input.
     *
     * @param input The input to the network
     * @return The output of the network
     */
    public double[] evaluate(double[] input) {
        /* put input on synapses */
        for (int i = 0; i < input.length; i += 1)
            this.input.get(i).forward = input[i];
        /* do a forward pass without the loss layer */
        for (int layerIndex = 0; layerIndex < neurons.size() - 1; layerIndex += 1) {
            ArrayList<Node> layer = neurons.get(layerIndex);
            for (int neuronIndex = 0; neuronIndex < layer.size(); neuronIndex += 1) {
                Node node = layer.get(neuronIndex);
                node.forwardPass();
            }
        }
        /* the first half of the input to the loss function is the output */
        Node lossNode = neurons.get(neurons.size() - 1).get(0);
        double[] output = new double[lossNode.neuron.INPUT_LEN / 2];
        copyForwardSynapses(lossNode.input, 0, output, 0, output.length);
        return output;
    }

    /**
     * Teaches (input, output) to the neural network with a rate of alpha.
     *
     * @param input  Array of length equal to inputs.length representing the provided input
     * @param output Array of length equal to half the number of inputs to the loss neuron
     *               representing the expected output for the given input
     * @param alpha  The learning rate
     * @return The computed loss
     */
    public double update(double[] input, double[] output, double alpha) {
        /* do a forward pass without the loss neuron */
        evaluate(input);
        /* get loss neuron */
        Node lossNode = neurons.get(neurons.size() - 1).get(0);
        /* put expected output on the loss neuron's input synapses */
        assert lossNode.input.length == output.length * 2;
        copyForwardSynapses(output, 0, lossNode.input, output.length, output.length);
        /* do a forward pass on the loss neuron */
        lossNode.forwardPass();
        /* put backward input (1.0) on synapse */
        lossNode.output[0].backward = 1.0;
        /* do a complete backward pass on the whole network */
        for (int layerIndex = neurons.size() - 1; layerIndex >= 0; layerIndex -= 1) {
            ArrayList<Node> layer = neurons.get(layerIndex);
            for (int neuronIndex = 0; neuronIndex < layer.size(); neuronIndex += 1) {
                Node node = layer.get(neuronIndex);
                double[] dl_dw = node.backwardPass();
                node.neuron.update(dl_dw, alpha);
            }
        }
        /* return loss */
        return lossNode.output[0].forward;
    }

    protected static class Node {
        public Synapse[] input;
        public Synapse[] output;
        public Neuron neuron;

        public Node(Neuron neuron) {
            this.neuron = neuron;
            input = new Synapse[neuron.INPUT_LEN];
            for (int i = 0; i < input.length; i += 1)
                input[i] = new Synapse();
            output = new Synapse[neuron.OUTPUT_LEN];
            for (int i = 0; i < output.length; i += 1)
                output[i] = new Synapse();
        }

        /**
         * The forward pass of a neuron.
         */
        public void forwardPass() {
            /* get input from synapses */
            double[] x = new double[neuron.INPUT_LEN];
            copyForwardSynapses(input, 0, x, 0, x.length);
            /* apply neuron function */
            double[] y = neuron.y(x);
            /* put output on synapses */
            copyForwardSynapses(y, 0, output, 0, y.length);
        }

        /**
         * The backward pass of a neuron.
         *
         * @return Array of length neuron.WEIGHT_LEN representing the gradient of loss with respect to the weights
         */
        public double[] backwardPass() {
            /* get backward input from synapses */
            double[] dl_dy = new double[neuron.OUTPUT_LEN];
            copyBackwardSynapses(output, 0, dl_dy, 0, dl_dy.length);
            /* apply neuron backward pass */
            double[] dl_dx = neuron.dl_dx(dl_dy);
            double[] dl_dw = neuron.dl_dw(dl_dy);
            /* put backward output on synapses */
            copyBackwardSynapses(dl_dx, 0, input, 0, dl_dx.length);
            return dl_dw;
        }
    }

    protected static class Synapse {
        public double forward;
        public double backward;
    }
}

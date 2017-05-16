package edu.sharif.ce.mshakerinava.brain.base;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Brain {
    protected ArrayList<Synapse> input;
    protected ArrayList<ArrayList<Node0>> neurons;

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
        for (ArrayList<Node0> layer : neurons) {
            for (Node0 node0 : layer) node0.neuron.reset();
        }
    }

    /**
     * Adds an input synapse to the network.
     */
    public void addInput() {
        input.add(new Synapse());
    }

    /**
     * Adds n input synapses to the network.
     *
     * @param n The number of inputs to add to the network
     */
    public void addInput(int n) {
        for (int i = 0; i < n; i += 1)
            addInput();
    }

    /**
     * Adds an empty layer of neurons to the end of the network.
     * The last layer must contain a single neuron that computes the loss function.
     */
    public void addLayer() {
        neurons.add(new ArrayList<>());
    }

    /**
     * Adds n empty layers of neurons to the end of the network.
     * The last layer must contain a single neuron that computes the loss function.
     *
     * @param n The number of layers to add to the network
     */
    public void addLayer(int n) {
        for (int i = 0; i < n; i += 1)
            this.addLayer();
    }

    /**
     * Adds a neuron to a layer of the network.
     * The first half of the inputs to the loss function must be the network's output
     * and the second half must be the expected output.
     *
     * @param neuron  The neuron to be added to the network
     * @param layer The index of the layer the neuron is being added to
     */
    public void addNode(Neuron neuron, int layer) {
        neurons.get(layer).add(new Node0(neuron));
    }

    public void connectInput(int lhsIndex, int rhsLayer, int rhsIndex, int rhsInput) {
        neurons.get(rhsLayer).get(rhsIndex).input[rhsInput] = input.get(lhsIndex);
    }

    public void connectInput(int lhsIndex, int rhsLayer, int rhsInput) {
        int n = neurons.get(rhsLayer).size();
        for (int i = 0; i < n; i += 1)
            connectInput(lhsIndex, rhsLayer, i, rhsInput);
    }

    public void connectNode(int lhsLayer, int lhsIndex, int lhsOutput, int rhsLayer, int rhsIndex, int rhsInput) {
        Synapse synapse = new Synapse();
        neurons.get(lhsLayer).get(lhsIndex).output[lhsOutput] = synapse;
        neurons.get(rhsLayer).get(rhsIndex).input[rhsInput] = synapse;
    }

    public void connectNode(int lhsLayer, int lhsIndex, int lhsOutput, int rhsLayer, int rhsInput) {
        int n = neurons.get(rhsLayer).size();
        for (int i = 0; i < n; i += 1)
            connectNode(lhsLayer, lhsIndex, lhsOutput, rhsLayer, i, rhsInput);
    }

    public void readWeights(String filename) {
        try {
            Scanner scanner = new Scanner(new FileReader(filename));
            for (ArrayList<Node0> layer : neurons) {
                for (Node0 node0 : layer) {
                    Neuron neuron = node0.neuron;
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

    public void writeWeights(String filename) {
        File file = new File(filename);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            for (ArrayList<Node0> layer : neurons) {
                for (Node0 node0 : layer) {
                    Neuron neuron = node0.neuron;
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

    public double[] evaluate(double[] input) {
        /* put input on synapses */
        for (int i = 0; i < input.length; i += 1)
            this.input.get(i).forward = input[i];
        /* put aside the loss layer */
        for (int layerIndex = 0; layerIndex < neurons.size() - 1; layerIndex += 1) {
            ArrayList<Node0> layer = neurons.get(layerIndex);
            Thread[] threads = new Thread[layer.size()];
            /* create forwardPass pass threads */
            for (int neuronIndex = 0; neuronIndex < layer.size(); neuronIndex += 1) {
                Node0 node0 = layer.get(neuronIndex);
                threads[neuronIndex] = new Thread(() -> {
                    node0.forwardPass();
                });
                threads[neuronIndex].start();
            }
            /* join all threads */
            for (int neuronIndex = 0; neuronIndex < layer.size(); neuronIndex += 1) {
                try {
                    threads[neuronIndex].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /* the first half of the input to the loss function is the output */
        Node0 lossNode0 = neurons.get(neurons.size() - 1).get(0);
        double[] output = new double[lossNode0.neuron.INPUT_LEN / 2];
        copyForwardSynapses(lossNode0.input, 0, output, 0, output.length);
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
        Node0 lossNode0 = neurons.get(neurons.size() - 1).get(0);
        /* put expected output on the loss neuron's input synapses */
        assert lossNode0.input.length == output.length * 2;
        copyForwardSynapses(output, 0, lossNode0.input, output.length, output.length);
        /* do a forward pass on the loss neuron */
        lossNode0.forwardPass();
        /* put backward input (1.0) on synapse */
        lossNode0.output[0].backward = 1.0;
        /* do a complete multithreaded backward pass on the whole network */
        for (int layerIndex = neurons.size() - 1; layerIndex >= 0; layerIndex -= 1) {
            ArrayList<Node0> layer = neurons.get(layerIndex);
            Thread[] threads = new Thread[layer.size()];
            /* create backward pass threads */
            for (int neuronIndex = 0; neuronIndex < layer.size(); neuronIndex += 1) {
                Node0 node0 = layer.get(neuronIndex);
                threads[neuronIndex] = new Thread(() -> {
                    double[] dl_dw = node0.backwardPass();
                    node0.neuron.update(dl_dw, alpha);
                });
                threads[neuronIndex].start();
            }
            /* join all threads */
            for (int neuronIndex = 0; neuronIndex < layer.size(); neuronIndex += 1) {
                try {
                    threads[neuronIndex].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /* return loss */
        return lossNode0.output[0].forward;
    }

    protected static class Node0 {
        public Synapse[] input;
        public Synapse[] output;
        public Neuron neuron;

        public Node0(Neuron neuron) {
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

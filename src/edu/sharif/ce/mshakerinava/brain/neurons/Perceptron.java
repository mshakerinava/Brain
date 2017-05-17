package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * A linear neuron plus an activation function.
 */
public class Perceptron extends Neuron {
    Neuron linear, activation;

    /**
     * Creates a perceptron with n inputs and specified activation function.
     *
     * @param n          The number of inputs
     * @param activation The activation function
     */
    public Perceptron(int n, Neuron activation) {
        super(n, 1, 0);
        /* assert that the neuron really is an activation function */
        assert activation.INPUT_LEN == 1;
        assert activation.OUTPUT_LEN == 1;
        assert activation.WEIGHT_LEN == 0;
        this.activation = activation;
        this.linear = new Linear(n);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        return activation.y(linear.y(x));
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        return linear.dl_dw(activation.dl_dx(dl_dy));
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        return linear.dl_dx(activation.dl_dx(dl_dy));
    }
}

package edu.sharif.ce.mshakerinava.brain.base;

import java.util.Random;

public abstract class Neuron {
    public final int INPUT_LEN;
    public final int OUTPUT_LEN;
    public final int WEIGHT_LEN;

    protected double[] w;
    protected double[] x;

    public Neuron(int inputLen, int outputLen, int weightLen) {
        INPUT_LEN = inputLen;
        OUTPUT_LEN = outputLen;
        WEIGHT_LEN = weightLen;
        this.w = new double[weightLen];
        reset();
    }

    /**
     * The function that the neuron computes.
     *
     * @param x Array of length INPUT_LEN representing the inputs
     * @return Array of length OUTPUT_LEN representing the outputs
     */
    public abstract double[] y(double[] x);

    /**
     * Gradient of the loss function with respect to the weights.
     *
     * @param dl_dy Array of length OUTPUT_LEN representing the gradient of loss with respect to the outputs
     * @return Array of length WEIGHT_LEN representing the gradient of loss with respect to the weights
     */
    public abstract double[] dl_dw(double[] dl_dy);

    /**
     * Gradient of the loss function with respect to the inputs.
     *
     * @param dl_dy Array of length OUTPUT_LEN representing the gradient of loss with respect to the outputs
     * @return Array of length INPUT_LEN representing the gradient of loss with respect to the inputs
     */
    public abstract double[] dl_dx(double[] dl_dy);

    public void reset() {
        Random gen = new Random(System.currentTimeMillis());
        for (int i = 0; i < WEIGHT_LEN; i += 1)
            w[i] = gen.nextGaussian();
    }

    public void update(double[] dl_dw, double alpha) {
        for (int i = 0; i < WEIGHT_LEN; i += 1)
            w[i] -= dl_dw[i] * alpha;
    }

    public double[] getWeights() {
        return w.clone();
    }

    public void setWeights(double[] w) {
        this.w = w;
    }
}
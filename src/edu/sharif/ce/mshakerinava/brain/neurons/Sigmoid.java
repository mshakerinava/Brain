package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * A neuron that computes the sigmoid function.
 */
public class Sigmoid extends Neuron {
    public Sigmoid() {
        super(1, 1, 0);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        double t = x[0];
        double y = 1.0 / (1.0 + Math.exp(-t));
        return new double[]{y};
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        double t = x[0];
        double dy_dx = Math.exp(t) / (1 + 2 * Math.exp(t) + Math.exp(2 * t));
        return new double[]{dy_dx * dl_dy[0]};
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        return new double[0];
    }
}
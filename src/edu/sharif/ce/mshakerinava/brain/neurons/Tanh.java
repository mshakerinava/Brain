package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * A neuron that computes the hyperbolic tangent function.
 */
public class Tanh extends Neuron {
    public Tanh() {
        super(1, 1, 0);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        double t = x[0];
        double y = Math.tanh(t);
        return new double[]{y};
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        double t = x[0];
        double dy_dx = 1 - Math.tanh(t) * Math.tanh(t);
        return new double[]{dy_dx * dl_dy[0]};
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        return new double[0];
    }
}
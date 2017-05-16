package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * The Rectified Linear Unit.
 * A neuron that computes the function y = max(0, x).
 */
public class ReLU extends Neuron {
    public ReLU() {
        super(1, 1, 0);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        double t = x[0];
        double y = Math.max(0.0, t);
        return new double[]{y};
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        double t = x[0];
        double dy_dx = (t >= 0.0 ? 1.0 : 0.0);
        return new double[]{dy_dx * dl_dy[0]};
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        return new double[0];
    }
}
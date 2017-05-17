package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * A neuron that computes the function f(x) = x.
 */
public class Identity extends Neuron {
    public Identity() {
        super(1, 1, 0);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        return new double[]{x[0]};
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        return new double[]{dl_dy[0]};
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        return new double[0];
    }
}
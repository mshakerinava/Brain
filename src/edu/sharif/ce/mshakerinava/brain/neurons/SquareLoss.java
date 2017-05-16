package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * A neuron that calculates the squared loss function.
 */
public class SquareLoss extends Neuron {
    public SquareLoss() {
        super(2, 1, 0);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        double delta = x[0] - x[1];
        double y = delta * delta;
        return new double[]{y};
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        double[] dy_dx = new double[]{2 * (x[0] - x[1]), 2 * (x[1] - x[0])};
        return new double[]{dy_dx[0] * dl_dy[0], dy_dx[1] * dl_dy[0]};
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        return null;
    }
}
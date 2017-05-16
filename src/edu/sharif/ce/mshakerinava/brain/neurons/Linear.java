package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.base.Neuron;

/**
 * A linear neuron that computes the dot product plus a bias.
 */
public class Linear extends Neuron {
    /**
     * Creates a linear neuron with n inputs.
     *
     * @param n The number of inputs
     */
    public Linear(int n) {
        super(n, 1, n + 1);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        double y = w[INPUT_LEN];
        for (int i = 0; i < INPUT_LEN; i += 1)
            y += w[i] * x[i];
        return new double[]{y};
    }

    @Override
    public double[] dl_dw(double[] dl_dy) {
        double[] dy_dw = new double[INPUT_LEN + 1];
        dy_dw[INPUT_LEN] = 1;
        for (int i = 0; i < INPUT_LEN; i += 1)
            dy_dw[i] = x[i];
        double[] dl_dw = new double[INPUT_LEN + 1];
        for (int i = 0; i < INPUT_LEN + 1; i += 1)
            dl_dw[i] = dy_dw[i] * dl_dy[0];
        return dl_dw;
    }

    @Override
    public double[] dl_dx(double[] dl_dy) {
        double[] dy_dx = new double[INPUT_LEN];
        for (int i = 0; i < INPUT_LEN; i += 1)
            dy_dx[i] = w[i];
        double[] dl_dx = new double[INPUT_LEN];
        for (int i = 0; i < INPUT_LEN; i += 1)
            dl_dx[i] = dy_dx[i] * dl_dy[0];
        return dl_dx;
    }
}
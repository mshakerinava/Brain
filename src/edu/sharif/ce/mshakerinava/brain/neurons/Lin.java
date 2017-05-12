package edu.sharif.ce.mshakerinava.brain.neurons;

import edu.sharif.ce.mshakerinava.brain.Node;

public class Lin extends Node {
    public Lin(int n) {
        super(n, 1, n);
    }

    @Override
    public double[] y(double[] x) {
        this.x = x;
        double y = 0;
        for (int i = 0; i < INPUT_LEN; i += 1)
            y += w[i] * x[i];
        return new double[]{y};
    }

    @Override
    @SuppressWarnings("Duplicates")
    public double[] dl_dw(double[] dl_dy) {
        double[] dy_dw = new double[INPUT_LEN];
        for (int i = 0; i < INPUT_LEN; i += 1)
            dy_dw[i] = x[i];
        double[] dl_dw = new double[INPUT_LEN];
        for (int i = 0; i < INPUT_LEN; i += 1)
            dl_dw[i] = dy_dw[i] * dl_dy[0];
        return dl_dw;
    }

    @Override
    @SuppressWarnings("Duplicates")
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
package me.soldier.util;

import java.util.Random;

public class NoiseMap {

	public double[] values;
	private int w, h;
	private Random rand;

	public NoiseMap(int w, int h) {
		this.w = w;
		this.h = h;
		values = new double[w * h];
		rand = new Random();

		int stepSize = w;
		do {
			int halfStep = stepSize / 2;
			double scale = 1.0 / w;
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);

					double e = (a + b + c + d) / 4.0 + (rand.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}

			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);

					double H = (a + b + d + e) / 4.0 + (rand.nextFloat() * 2 - 1) * stepSize * scale;
					double g = (a + c + d + f) / 4.0 + (rand.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}
			stepSize /= 2;
		} while (stepSize > 1);
		System.out.println("Noise generated");
	}

	private void setSample(int x, int y, double value) {
		values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
	}

	private double sample(int x, int y) {
		return values[(x & (w - 1)) + (y & (h - 1)) * w];
	}
}

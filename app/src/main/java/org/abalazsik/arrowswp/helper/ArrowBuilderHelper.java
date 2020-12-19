
package org.abalazsik.arrowswp.helper;

/**
 *
 * @author ador
 */
public class ArrowBuilderHelper {
	
	private int left;
	private int right;
	private final int segments;
	
	private final float xs[];
	private final float ys[];
	
	public ArrowBuilderHelper (int segments) {
		this.segments = segments;
		left = 0;
		right = segments * 2;
		xs = new float[segments * 2 + 1];
		ys = new float[segments * 2 + 1];
	}
	
	public void addLeft(float x, float y) {
		if (left >= segments) {
			throw new RuntimeException();
		}
		xs[left] = x;
		ys[left] = y;
		left++;
	}
	
	public void addRight(float x, float y) {
		if (right <= segments) {
			throw new RuntimeException();
		}
		xs[right] = x;
		ys[right] = y;
		right--;
	}

	public float[] getXs() {
		return xs;
	}

	public float[] getYs() {
		return ys;
	}
	
	public int[] getXsAsInts() {
		int[] ints = new int[xs.length];
		
		for (int i = 0; i < ints.length; i++) {
			ints[i] = (int)xs[i];
		}
		
		return ints;
	}

	public int[] getYsAsInts() {
		int[] ints = new int[ys.length];
		
		for (int i = 0; i < ints.length; i++) {
			ints[i] = (int)ys[i];
		}
		
		return ints;
	}
	
	public void setMidPoint(float x, float y) {
		xs[segments] = x;
		ys[segments] = y;
	}
	
}

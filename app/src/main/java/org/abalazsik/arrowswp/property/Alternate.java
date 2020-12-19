package org.abalazsik.arrowswp.property;

/**
 *
 * @author ador
 */
public class Alternate implements Property {

	private final int period;
	private final float m;

	public Alternate(int period, float m) {
		this.period = period;
		this.m = m;
	}
	
	@Override
	public float getValue(float t) {
		return ((int) (t / period) % 2 == 0 ? -m : m);
	}

}

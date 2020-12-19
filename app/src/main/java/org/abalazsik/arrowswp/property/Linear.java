
package org.abalazsik.arrowswp.property;

/**
 *
 * @author ador
 */
public class Linear implements Property {
	private final float m;

	public Linear(float m) {
		this.m = m;
	}

	@Override
	public float getValue(float t) {
		return t * m;
	}
	
}

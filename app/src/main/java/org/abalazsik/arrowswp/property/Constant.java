
package org.abalazsik.arrowswp.property;

/**
 *
 * @author ador
 */
public class Constant implements Property {

	private final float c;

	public Constant(float c) {
		this.c = c;
	}
	
	@Override
	public float getValue(float t) {
		return c;
	}
	
}

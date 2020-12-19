
package org.abalazsik.arrowswp.property;

/**
 *
 * @author ador
 */
public class SignedAlternate extends Alternate {

	private final boolean negative;
	
	public SignedAlternate(int period, float m, boolean negative) {
		super(period, m);
		this.negative = negative;
	}

	@Override
	public float getValue(float t) {
		if (negative) {
			return -super.getValue(t);
		} else {
			return super.getValue(t);
		}
	}
	
	
}

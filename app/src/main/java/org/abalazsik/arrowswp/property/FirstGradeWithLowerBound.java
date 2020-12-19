
package org.abalazsik.arrowswp.property;

/**
 *
 * @author ador
 */
public class FirstGradeWithLowerBound implements Property {
	
	private final float lowerBound;
	private final float m;
	private final float c;

	public FirstGradeWithLowerBound(float lowerBound, float m, float c) {
		this.lowerBound = lowerBound;
		this.m = m;
		this.c = c;
	}

	@Override
	public float getValue(float t) {
		float value = t * m + c;
		
		if (value < lowerBound) {
			value = lowerBound;
		}
		
		return value;
	}

}

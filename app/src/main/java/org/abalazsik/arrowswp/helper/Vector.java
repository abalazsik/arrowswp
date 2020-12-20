
package org.abalazsik.arrowswp.helper;

/**
 *
 * @author ador
 */
public class Vector {

	private final float i;
	private final float j;

	public Vector(float i, float j) {
		this.i = i;
		this.j = j;
	}

	public float getI() {
		return i;
	}

	public float getJ() {
		return j;
	}

	@Override
	public String toString() {
		return "Vector{" + "i=" + i + ", j=" + j + '}';
	}

	public static Vector gaussElimination(Vector baseI, Vector baseJ, float x, float y) {
		
		float a = baseI.getI();
		float b = baseJ.getI();
		
		float c = baseI.getJ();
		float d = baseJ.getJ();

		//gauss elimination. Only do the necessary calculations
		
		b /= a;
		x /= a;
		//a = 1f;
		
		d /= c;
		y /= c;
		//c = 1f;
		
		//c = 0;// c -= a
		d -= b;
		y -= x;
		
		y /= d;
		//d = 1f;
		
		x -= b * y;
		//b = 0 //b -= b * 1
		
		return new Vector(x, y);
	}
}

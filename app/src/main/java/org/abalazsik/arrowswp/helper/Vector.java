
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

		if ((baseI.i == 0 && baseJ.i == 0) || (baseI.j == 0 && baseJ.j == 0)
				|| (baseI.i == 0 && baseI.j == 0) || (baseJ.i == 0 && baseJ.j == 0)) {

			throw new ArithmeticException(String.format("Can't solve: %s, %s, %f, %f", baseI.toString(), baseJ.toString(), x, y));//expecting two real results, so its ok to throw exception
		}

		if (baseI.i == 0) {
			Vector tmp = baseI;
			baseI = baseJ;
			baseJ = tmp;
			float t = x;
			x = y;
			y = t;
		}

		float a = baseI.getI();
		float b = baseI.getJ();

		float c = baseJ.getI();
		float d = baseJ.getJ();

		//gauss elimination. Only do the necessary calculations

		if(a != 0f) {
			b /= a;
			x /= a;
		} else {
			x /= b;
			//b = 1;

			y -= d * x;
			//d = 0;
			y /= c;
			//c = 1;

			return new Vector(x, y);
		}
		//a = 1f;

		if (c != 0){
			d /= c;
			y /= c;
		}
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

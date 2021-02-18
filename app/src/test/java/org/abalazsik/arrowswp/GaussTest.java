
package org.abalazsik.arrowswp;

import org.abalazsik.arrowswp.helper.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 *
 * @author ador
 */
public class GaussTest {

	@Test
	public void test() {

		Random random = new Random();

		for (int i = 0; i< 3; i++) {
			float a = randNum(random), b = randNum(random);
			float c = randNum(random), d = randNum(random);

			final float C1 = 25f;
			final float C2 = 15f;

			float x = a * C1 + b * C2;
			float y = c * C1 + d * C2;

			System.out.println("x: " + x + " y: " + y);

			Vector v = Vector.gaussElimination(new Vector(a, b), new Vector(c, d), x, y);

			System.out.println(v);

			Assert.assertEquals(C1, v.getI(), 0.000001f);
			Assert.assertEquals(C2, v.getJ(), 0.000001f);

		}
	}

	private float randNum(Random random) {
		int n = random.nextInt(4);

		if (n == 0) {
			return -1f;
		} else {
			return (float)n;
		}
	}

	@Test
	public void test2() {
		Vector v = Vector.gaussElimination(new Vector(2f, 5f), new Vector(0f, 1f), 7f, 3f);

		System.out.println(v);

		Assert.assertEquals(8/3f, v.getI(), 0.000001f);
		Assert.assertEquals(1/3f, v.getJ(), 0.000001f);
	}
	
}

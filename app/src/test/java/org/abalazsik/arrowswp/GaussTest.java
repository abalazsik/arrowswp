
package org.abalazsik.arrowswp;

import org.abalazsik.arrowswp.helper.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ador
 */
public class GaussTest {

	@Test
	public void test1() {
		Vector v = Vector.gaussElimination(new Vector(2, 1), new Vector(2, 2), 6, 4);
		
		Assert.assertEquals(2, v.getI(), 0.00001f);
		Assert.assertEquals(1, v.getJ(), 0.00001f);
	}
	
	@Test
	public void test2() {
		Vector v = Vector.gaussElimination(new Vector(2, 4), new Vector(6, 2), 1, 5);
		
		Assert.assertEquals(14f / 10, v.getI(), 0.00001f);
		Assert.assertEquals(-3f / 10, v.getJ(), 0.00001f);
	}
	
}

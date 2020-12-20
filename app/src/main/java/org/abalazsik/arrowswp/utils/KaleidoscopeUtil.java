
package org.abalazsik.arrowswp.utils;

import android.graphics.Bitmap;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.helper.Vector;

/**
 *
 * @author ador
 */
public class KaleidoscopeUtil {

	private static final float DIST =  10f;

	public static Bitmap generate(Bitmap source, float srcCenterX, float srcCenterY, float dstCenterX, float dstCenterY, float rot, int noMirror, GraphicsOptions options) {
		if (noMirror < 2) {
			throw new RuntimeException();
		}

		KPoint[] kpoints = new KPoint[noMirror];
		
		float angle = Constants.Math.TAU / noMirror;
		
		for (int i = 0; i < noMirror; i++) {
			
			float cos = (float)Math.cos(angle * i + rot);
			float sin = (float)Math.sin(angle * i + rot);
			
			float x = (float)(dstCenterX + DIST * cos);
			float y = (float)(dstCenterY + DIST * sin);
			
			if (i % 2 == 0) {
				kpoints[i] = new KPoint(x, y, new Vector(cos, sin), new Vector(-sin, cos));
			} else {
				kpoints[i] = new KPoint(x, y, new Vector(cos, sin), new Vector(sin, -cos));
			}
		}

		Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		
		KPoint closest;
		
		for (int y = 0; y < source.getHeight(); y++ ) {
			for (int x = 0; x < source.getWidth(); x++) {
				closest = closest(kpoints, x, y);
				
				Vector point = Vector.gaussElimination(closest.getBasei(), closest.getBasej(), (float)x - dstCenterX, (float)y - dstCenterY);
				
				int srcX = (int)(point.getI() + srcCenterX);
				int srcY = (int)(point.getJ() + srcCenterY);
				
				if (srcX >= 0 && srcX < source.getWidth() && srcY >= 0 && srcY < source.getHeight()) {
					result.setPixel(x, y, source.getPixel(srcX, srcY));
				} else if (options.isWrapBackground()) {
					int sx = (srcX + source.getWidth()) % source.getWidth();
					int sy = (srcY + source.getHeight()) % source.getHeight();
					
					result.setPixel(x, y, source.getPixel(sx, sy));
				} else {
					result.setPixel(x, y, options.getBackgroundColor());
				}
			}
		}
		
		return result;
	}
	
	private static KPoint closest(KPoint[] points, int x, int y) {
		float minDist = (points[0].getX() - x) * (points[0].getX() - x) + (points[0].getY() - y) * (points[0].getY() - y);
		KPoint closest = points[0];
		
		for (int i = 1 ; i < points.length; i++) {
			float tmp = (points[i].getX() - x) * (points[i].getX() - x) + (points[i].getY() - y) * (points[i].getY() - y);
			if (tmp < minDist) {
				closest = points[i];
				minDist = tmp;
			}
		}
		
		return closest;
	}

	private static class KPoint {
		private float x, y;
		private Vector basei , basej;

		public KPoint() {
		}

		public KPoint(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public KPoint(float x, float y, Vector basei, Vector basej) {
			this.x = x;
			this.y = y;
			this.basei = basei;
			this.basej = basej;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public Vector getBasei() {
			return basei;
		}

		public void setBasei(Vector basei) {
			this.basei = basei;
		}

		public Vector getBasej() {
			return basej;
		}

		public void setBasej(Vector basej) {
			this.basej = basej;
		}

		@Override
		public String toString() {
			return "KPoint{" + "x=" + x + ", y=" + y + ", basei=" + basei + ", basej=" + basej + '}';
		}

	}

}

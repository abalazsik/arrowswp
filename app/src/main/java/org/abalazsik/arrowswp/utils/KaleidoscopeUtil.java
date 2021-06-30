
package org.abalazsik.arrowswp.utils;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Float2;
import android.renderscript.RenderScript;
import android.renderscript.Short4;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.ScriptC_kaleidoscope;
import org.abalazsik.arrowswp.ScriptField_kpoint;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.helper.Vector;

/**
 *
 * @author ador
 */
public class KaleidoscopeUtil {

	private static final float DIST =  10f;

	public static Bitmap generate(Bitmap source, float srcCenterX, float srcCenterY, float dstCenterX, float dstCenterY, float rot, int noMirror, ArrowsContext context) {
		if (noMirror < 2) {
			throw new RuntimeException();
		}

		RenderScript rs = RenderScript.create(context.getContext(), RenderScript.ContextType.DEBUG);

		ScriptField_kpoint kpoints = new ScriptField_kpoint(rs, noMirror);

		float angle = Constants.Math.TAU / noMirror;
		
		for (int i = 0; i < noMirror; i++) {
			
			float cos = (float)Math.cos(angle * i + rot);
			float sin = (float)Math.sin(angle * i + rot);
			
			float x = dstCenterX + DIST * cos;
			float y = dstCenterY + DIST * sin;
			
			if (i % 2 == 0) {
				kpoints.set_x(i, x, false);
				kpoints.set_y(i, y, false);
				kpoints.set_basei(i, new Float2(cos, -sin), false);
				kpoints.set_basej(i, new Float2(sin, cos), false);
			} else {
				kpoints.set_x(i, x, false);
				kpoints.set_y(i, y, false);
				kpoints.set_basei(i, new Float2(cos, sin), false);
				kpoints.set_basej(i, new Float2(sin, -cos), false);
			}
		}

		kpoints.copyAll();

		Bitmap bmOut = context.get();
		bmOut.eraseColor(context.getGraphicsOptions().getBackgroundColor());

		ScriptC_kaleidoscope kaleidoscope = new ScriptC_kaleidoscope(rs);

		GraphicsOptions options = context.getGraphicsOptions();

		kaleidoscope.set_backgroundColor(
				new Short4(
						(short)((options.getBackgroundColor() >> 24) & 0xff),
						(short)((options.getBackgroundColor() >> 16) & 0xff),
						(short)((options.getBackgroundColor() >> 8) & 0xff),
						(short)((options.getBackgroundColor()) & 0xff)
				));

		Allocation tmpIn = Allocation.createFromBitmap(rs, source);
		Allocation tmpOut = Allocation.createFromBitmap(rs, bmOut);

		kaleidoscope.bind_kpoints(kpoints);
		kaleidoscope.set_kpointsSize(noMirror);
		kaleidoscope.set_dstCenterX(dstCenterX);
		kaleidoscope.set_dstCenterY(dstCenterY);
		kaleidoscope.set_gIn(tmpIn);
		kaleidoscope.set_gOut(tmpOut);
		kaleidoscope.set_srcCenterX(srcCenterX);
		kaleidoscope.set_srcCenterY(srcCenterY);
		kaleidoscope.set_wrapbackground(options.isWrapBackground());

		kaleidoscope.forEach_transform(tmpIn);

		tmpOut.copyTo(bmOut);

		tmpIn.destroy();
		tmpOut.destroy();

		return bmOut;
	}

}

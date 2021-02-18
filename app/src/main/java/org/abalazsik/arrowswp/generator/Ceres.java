package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;
import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.property.Constant;
import org.abalazsik.arrowswp.property.Property;
import org.abalazsik.arrowswp.utils.ArrowUtil;
import org.abalazsik.arrowswp.utils.GraphicsUtil;

/**
 *
 * @author ador
 */
public class Ceres implements BackgroundGenerator {

	private static final int NUMBER_OF_ARROWS = 200;
	private static final int NUMBER_OF_SEGMENTS = 18;
	private static final float CENTER_RADIUS = 40f;

	@Override
	public Bitmap generate(ArrowsContext context) {
		int width = context.getWidth();
		int height = context.getHeight();
		GraphicsOptions options = context.getGraphicsOptions();
		Random random = context.getRandom();

		Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(result);

		c.drawColor(options.getBackgroundColor());

		float rad = Constants.Math.TAU / NUMBER_OF_ARROWS;

		Property tangent = new Constant(0.4f * (random.nextFloat() - 0.5f));

		int cx = width / 2;
		int cy = height / 2;

		for (int i : ArrowUtil.shuffledRange(NUMBER_OF_ARROWS, context)) {
			float cos = (float) Math.cos(i * rad);
			float sin = (float) Math.sin(i * rad);

			int length = (width + height) / 6;

			length = (int)((length * random.nextFloat() * 0.9f) + length * 0.1f);
			
			GraphicsUtil.drawArrow(c, cx + (CENTER_RADIUS * cos), cy + (CENTER_RADIUS * sin),
					i * rad, length, NUMBER_OF_SEGMENTS, tangent, context);
		}

		return result;
	}

	@Override
	public GraphicsOptions getPrefferedGraphicsOptions() {
		return new GraphicsOptions()
				.setColor(Constants.ColorsAsInts.RED1)
				.setColor2(Constants.ColorsAsInts.RED2)
				.setRandomColor(true)
				.setDisplacementEnabled(true)
				.setAntialiasing(true)
				.setDisp1(0.5f)
				.setDisp2(0.5f)
				.setBackgroundColor(Constants.ColorsAsInts.BLACK);
	}

}

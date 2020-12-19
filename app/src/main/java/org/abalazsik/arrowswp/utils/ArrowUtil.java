package org.abalazsik.arrowswp.utils;

import org.abalazsik.arrowswp.helper.ArrowBuilderHelper;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.property.Property;

/**
 *
 * @author ador
 */
public class ArrowUtil {

	public static ArrowBuilderHelper createBoldArrow(float[] xs, float[] ys, int len, Property weight) {
		ArrowBuilderHelper lrp = new ArrowBuilderHelper(len + 1);

		for (int i = 0; i < len - 1; i++) {
			float dx = xs[i + 1] - xs[i];
			float dy = ys[i + 1] - ys[i];

			float dlen = (float) Math.sqrt(dx * dx + dy * dy);

			float nx = dx / dlen;
			float ny = dy / dlen;
			float t = (float) i / len;

			float weightVal = weight.getValue(t);

			lrp.addLeft(xs[i] - (ny * weightVal), ys[i] + (nx * weightVal));
			lrp.addRight(xs[i] + (ny * weightVal), ys[i] - (nx * weightVal));
		}

		float dx = xs[len - 1] - xs[len - 2];
		float dy = ys[len - 1] - ys[len - 2];

		float dlen = (float) Math.sqrt(dx * dx + dy * dy);

		float nx = dx / dlen;
		float ny = dy / dlen;

		float weightVal = weight.getValue(1f);

		float x = xs[len - 1] - (ny * weightVal);
		float y = ys[len - 1] + (nx * weightVal);

		lrp.addLeft(x, y);
		lrp.addLeft(x - (ny * weightVal), y + (nx * weightVal));

		x = xs[len - 1] + (ny * weightVal);
		y = ys[len - 1] - (nx * weightVal);

		lrp.addRight(x, y);
		lrp.addRight(x + (ny * weightVal), y - (nx * weightVal));

		lrp.setMidPoint(xs[len - 1] + dx, ys[len - 1] + dy);

		return lrp;
	}

	public static int[] shuffledRange(int range, ArrowsContext context) {
		int[] result = new int[range];

		for (int i = 0; i < range; i++) {
			result[i] = i;
		}

		for (int i = range - 1; i > 1; i--) {
			int j = context.getRandom().nextInt(i);
			int tmp = result[i];
			result[i] = result[j];
			result[j] = tmp;
		}

		return result;
	}
}

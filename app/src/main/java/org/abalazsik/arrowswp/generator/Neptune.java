package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.property.FirstGradeWithLowerBound;
import org.abalazsik.arrowswp.property.SignedAlternate;
import org.abalazsik.arrowswp.property.Property;
import org.abalazsik.arrowswp.utils.GraphicsUtil;

public class Neptune implements BackgroundGenerator {

    private static final float DIRECTION = -1.1886737f;
    private static final int NUMBER_OF_SEGMENTS = 10;

    @Override
    public Bitmap generate(ArrowsContext context) {
        int width = context.getWidth();
        int height = context.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(result);

        int length = height / 4;
        Property weight = new FirstGradeWithLowerBound(6f, -20f, 20f);

        for (int k = 0; k < 2; k++) {
            for (int i = 20; i < height + 100; i += 50) {
                for (int j = k * 15; j < width; j += 30) {
                    Property tangent = new SignedAlternate(context.getRandom().nextInt(6) + 3,
                            context.getRandom().nextFloat() * 0.2f, context.getRandom().nextBoolean());

                    GraphicsUtil.drawBoldArrow(c, j, i, DIRECTION, length, weight, NUMBER_OF_SEGMENTS, tangent, context);
                }
            }
        }

        return result;
    }

    @Override
    public GraphicsOptions getPrefferedGraphicsOptions() {
        return new GraphicsOptions()
                .setColor(Constants.ColorsAsInts.RED1)
                .setColor(Constants.ColorsAsInts.RED2)
                .setRandomColor(true)
                .setBackgroundColor(Constants.ColorsAsInts.BLACK);
    }
}

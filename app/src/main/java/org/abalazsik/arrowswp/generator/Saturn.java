package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.property.Alternate;
import org.abalazsik.arrowswp.property.Property;
import org.abalazsik.arrowswp.utils.ArrowUtil;
import org.abalazsik.arrowswp.utils.GraphicsUtil;

public class Saturn implements BackgroundGenerator {

    private static final int NUMBER_OF_ARROWS = 390;
    private static final int NUMBER_OF_SEGMENTS = 16;
    private static final float CENTER_RADIUS = 20f;

    @Override
    public Bitmap generate(ArrowsContext context) {
        int width = context.getWidth();
        int height = context.getHeight();
        GraphicsOptions options = context.getGraphicsOptions();

        Bitmap result = context.get();

        Canvas c = new Canvas(result);

        c.drawColor(options.getBackgroundColor());

        float rad = Constants.Math.TAU / NUMBER_OF_ARROWS;

        Property tangent ;

        int cx = width / 2;
        int cy = height / 2;

        for (int i : ArrowUtil.shuffledRange(NUMBER_OF_ARROWS, context)) {
            float cos = (float)Math.cos(i * rad);
            float sin = (float)Math.sin(i * rad);

            tangent = new Alternate(context.getRandom().nextInt(6) + 3, 0.35f * context.getRandom().nextFloat() + 0.1f);

            int length = width / 3;

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
                .setLineWeight(2f)
                .setWrapBackground(true)
                .setBackgroundColor(Constants.ColorsAsInts.BLACK);
    }
}

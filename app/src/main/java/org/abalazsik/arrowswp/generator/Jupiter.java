package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.property.FirstGradeWithLowerBound;
import org.abalazsik.arrowswp.property.Property;
import org.abalazsik.arrowswp.property.SignedAlternate;
import org.abalazsik.arrowswp.utils.ArrowUtil;
import org.abalazsik.arrowswp.utils.GraphicsUtil;

import java.util.Random;

public class Jupiter implements BackgroundGenerator {

    private static final int NUMBER_OF_ARROWS = 37;
    private static final int NUMBER_OF_SEGMENTS = 18;

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

        Property tangent;

        Property weight = new FirstGradeWithLowerBound(3f, -20f, 20f);

        int cx = width / 2;
        int cy = height / 2;

        for (int j = 150; j > 0; j -= 40) {
            for (int i : ArrowUtil.shuffledRange(NUMBER_OF_ARROWS, context)) {
                float cos = (float)Math.cos(i * rad);
                float sin = (float)Math.sin(i * rad);

                tangent = new SignedAlternate(random.nextInt(6) + 3, 0.35f * random.nextFloat() + 0.1f, random.nextBoolean());

                int length = width / 3;

                GraphicsUtil.drawBoldArrow(c, cx + (j * cos), cy + (j * sin), i * rad, length, weight, NUMBER_OF_SEGMENTS, tangent, context);
            }
        }

        return result;
    }

    @Override
    public GraphicsOptions getPrefferedGraphicsOptions() {
        return new GraphicsOptions()
                .setColor(Constants.ColorsAsInts.RED1)
                .setColor2(Constants.ColorsAsInts.RED2)
                .setRandomColor(true)
                .setBackgroundColor(Constants.ColorsAsInts.WHITE);
    }
}

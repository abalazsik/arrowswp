package org.abalazsik.arrowswp.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import org.abalazsik.arrowswp.helper.ArrowBuilderHelper;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.property.Property;

public class GraphicsUtil {

    private static final float P1 = 0.2f;
    private static final float P2 = 0.8f;

    //simple arrow
    public static void drawArrow(Canvas g, float x, float y, float m, float length, int segments, Property dtangent, ArrowsContext context) {

        Paint paint = optionsToPaint(context);

        float K = length / segments;

        for (float i = 0; i < segments; i++) {
            m += dtangent.getValue(i);
            float x2 = (float)Math.cos(m) * K + x;
            float y2 = (float)Math.sin(m) * K + y;

            GraphicsUtil.drawLine(g, (int)x, (int)y, (int)x2, (int)y2, context);

            x = x2;
            y = y2;
        }

        final float dAngle = (float) Math.PI * 5f / 6f;

        g.drawLine(x, y, (float)(x + Math.cos(m + dAngle) * K), (float)(y + Math.sin(m + dAngle) * K), paint);
        g.drawLine(x, y, (float)(x + Math.cos(m - dAngle) * K), (float)(y + Math.sin(m - dAngle) * K), paint);
    }

    public static void drawBoldArrow(Canvas g, float x, float y, float m, float length, Property weight, int segments, Property dtangent, ArrowsContext context) {

        int noPoints = segments + 1;

        float[] xs = new float[noPoints];
        float[] ys = new float[noPoints];

        float K = length / segments;

        xs[0] = x;
        ys[0] = y;

        for (int i = 0; i < segments; i++) {
            m += dtangent.getValue(i);
            float x2 = (float)Math.cos(m) * K + x;
            float y2 = (float)Math.sin(m) * K + y;

            x = x2;
            y = y2;

            xs[i + 1] = x2;
            ys[i + 1] = y2;
        }

        Paint paint = optionsToPaint(context);

        ArrowBuilderHelper body = ArrowUtil.createBoldArrow(xs, ys, noPoints, weight);
        drawPolygon(g, body.getXs(), body.getYs(), paint);

        paint.setColor(0xff000000);
        paint.setStyle(Paint.Style.STROKE);
        drawPolygon(g, body.getXs(), body.getYs(), paint);
    }

    public static void drawPolygon(Canvas c, float[] xs, float[] ys, Paint paint) {
        Path path = new Path();

        path.moveTo(xs[0], ys[0]);

        for (int i = 1; i < xs.length; i++) {
            path.lineTo(xs[i], ys[i]);
        }

        path.close();

        c.drawPath(path, paint);
    }

    public static void drawLine(Canvas g, int fromX, int fromY, int toX, int toY, ArrowsContext context) {

        GraphicsOptions options = context.getGraphicsOptions();

        Paint paint = optionsToPaint(context);

        g.drawLine(fromX, fromY, toX, toY, paint);

        if (options.isDisplacementEnabled()) {
            int dx = toX - fromX;
            int dy = toY - fromY;

            dispDrawLine(g,
                    fromX, fromY, dx, dy,
                    -options.getDisp1() * context.getRandom().nextFloat(), context.getRandom().nextFloat() * P1,
                    P2 + context.getRandom().nextFloat() * P1, paint);

            dispDrawLine(g, fromX, fromY, dx, dy,
                    options.getDisp2() * context.getRandom().nextFloat(), context.getRandom().nextFloat() * P1,
                    P2 + context.getRandom().nextFloat() * P1, paint);
        }
    }

    private static Paint optionsToPaint(ArrowsContext context) {
        Paint paint =  new Paint();
        paint.setColor(getColor(context));
        return  paint;
    }

    private static void dispDrawLine(Canvas g, int fromX, int fromY, int dx, int dy, float d, float start, float end, Paint paint) {
        int x1 = fromX + (int) (dx * start + d * dy);
        int y1 = fromY + (int) (dy * start + d * dx);

        int x2 = fromX + (int) (dx * end + d * dy);
        int y2 = fromY + (int) (dy * end + d * dx);

        g.drawLine(x1, y1, x2, y2, paint);
    }

    public static int getColor(ArrowsContext context) {
        GraphicsOptions options = context.getGraphicsOptions();

        if (options.isRandomColor()) {
            return getRandomColor(context);
        } else {
            return options.getColor();
        }
    }

    public static int getRandomColor(ArrowsContext context) {

        int color1 = context.getGraphicsOptions().getColor();
        int color2 = context.getGraphicsOptions().getColor2();

        float f = context.getRandom().nextFloat();

        int a = (color2 >> 24) & 0xff;
        int r = (color2 >> 16) & 0xff;
        int g = (color2 >> 8) & 0xff;
        int b = (color2) & 0xff;

        int adiff = (color1 >> 24) & 0xff - a;
        int rdiff = (color1 >> 16) & 0xff - r;
        int gdiff = (color1 >> 8) & 0xff - g;
        int bdiff = (color1) & 0xff - b;

        int a2 = (int)(adiff * f);
        int r2 = (int)(rdiff * f);
        int g2 = (int)(gdiff * f);
        int b2 = (int)(bdiff * f);

        return (a + a2) << 24 | (r + r2) << 16 | (g + g2) << 8 | (b + b2);
    }

    public static int getComplementerColor(int color) {
        return 0xffffffff - color;
    }
}

package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.utils.KaleidoscopeUtil;

import java.util.Random;

public class Mars extends Jupiter {

    @Override
    public Bitmap generate(ArrowsContext context) {
        Bitmap baseImage =  super.generate(context);

        Random random = context.getRandom();

        Bitmap result = KaleidoscopeUtil.generate(baseImage,
                random.nextFloat() * context.getWidth(),
                random.nextFloat() * context.getHeight(),
                context.getWidth() / 2f,
                context.getHeight() / 2f,
                random.nextFloat() * Constants.Math.TAU,
                6,
                context.getGraphicsOptions()
                );

        baseImage.recycle();

        return result;
    }

    @Override
    public GraphicsOptions getPrefferedGraphicsOptions() {
        return super.getPrefferedGraphicsOptions().setWrapBackground(true);
    }
}

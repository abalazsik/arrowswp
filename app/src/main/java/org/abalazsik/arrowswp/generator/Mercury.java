package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;

import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.utils.GlowEffectUtil;

public class Mercury extends Saturn {

    @Override
    public Bitmap generate(ArrowsContext context) {
        Bitmap baseImage = super.generate(context);

        Bitmap result = GlowEffectUtil.glow(baseImage, 5);

        baseImage.recycle();

        return result;
    }

    @Override
    public GraphicsOptions getPrefferedGraphicsOptions() {
        return super.getPrefferedGraphicsOptions();
    }
}

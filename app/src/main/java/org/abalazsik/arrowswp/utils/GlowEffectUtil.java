package org.abalazsik.arrowswp.utils;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlend;
import android.renderscript.ScriptIntrinsicBlur;

import org.abalazsik.arrowswp.helper.ArrowsContext;

public class GlowEffectUtil {

    public static Bitmap glow(Bitmap source, ArrowsContext context, float blurSize) {
        if (blurSize < 2) {
            throw new RuntimeException();
        }

        RenderScript rs = RenderScript.create(context.getContext());
        Bitmap bmOut = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.RGBA_8888(rs));;
        Allocation tmpIn = Allocation.createFromBitmap(rs, source);
        Allocation tmpOut = Allocation.createFromBitmap(rs, bmOut);
        blur.setRadius(blurSize);
        blur.setInput(tmpIn);
        blur.forEach(tmpOut);

        ScriptIntrinsicBlend blend = ScriptIntrinsicBlend.create(rs, Element.RGBA_8888(rs));

        blend.forEachAdd(tmpIn, tmpOut);
        tmpOut.copyTo(bmOut);

        return bmOut;
    }

}

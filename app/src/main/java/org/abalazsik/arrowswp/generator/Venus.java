
package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.utils.GlowEffectUtil;
import org.abalazsik.arrowswp.utils.KaleidoscopeUtil;

import java.util.Random;

/**
 *
 * @author ador
 */
public class Venus extends Saturn {
	
	@Override
	public Bitmap generate(ArrowsContext context) {
		Bitmap baseImage =  super.generate(context);
		
		Random random = context.getRandom();
		float srcCenterX = (random.nextFloat() * context.getWidth()/ 10);
		float srcCenterY = (context.getHeight() / 4) + (random.nextFloat() * context.getHeight() / 4);

		Bitmap result = KaleidoscopeUtil.generate(baseImage,
                srcCenterX,
                srcCenterY,
                context.getWidth() / 2f,
                context.getHeight() / 2f,
                random.nextFloat() * Constants.Math.TAU,
                random.nextBoolean()?6:8,
                context.getGraphicsOptions()
                );

		baseImage.recycle();

		Bitmap result2 = GlowEffectUtil.glow(result, context, 7.5f);

		result.recycle();

        return result2;
	}

	@Override
	public GraphicsOptions getPrefferedGraphicsOptions() {
		return super.getPrefferedGraphicsOptions().setLineWeight(2f);
	}
}

package org.abalazsik.arrowswp.generator;

import android.graphics.Bitmap;

import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;

//I'm bad at naming things, so i will go with planet names

public interface BackgroundGenerator {
    Bitmap generate(ArrowsContext context);
    GraphicsOptions getPrefferedGraphicsOptions();
}

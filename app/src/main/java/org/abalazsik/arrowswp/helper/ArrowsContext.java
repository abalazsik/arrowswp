package org.abalazsik.arrowswp.helper;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Random;

public class ArrowsContext {
    private int width;
    private int height;
    private GraphicsOptions graphicsOptions;
    private Random random;//for random, but reproducible images
    private Context context;//for hardware accelerated effects

    private static Entry[] entries;

    public ArrowsContext(int width, int height, Context context, GraphicsOptions graphicsOptions, Random random) {
        init(4, width, height);
        this.width = width;
        this.height = height;
        this.graphicsOptions = graphicsOptions;
        this.context = context;
        this.random = random;
    }

    public ArrowsContext(int width, int height, GraphicsOptions graphicsOptions, Random random) {
        init(3, width, height);
        this.width = width;
        this.height = height;
        this.graphicsOptions = graphicsOptions;
        this.random = random;
    }

    public int getWidth() {
        return width;
    }

    public ArrowsContext setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ArrowsContext setHeight(int height) {
        this.height = height;
        return this;
    }

    public GraphicsOptions getGraphicsOptions() {
        return graphicsOptions;
    }

    public ArrowsContext setGraphicsOptions(GraphicsOptions graphicsOptions) {
        this.graphicsOptions = graphicsOptions;
        return this;
    }

    public Random getRandom() {
        return random;
    }

    public ArrowsContext setRandom(Random random) {
        this.random = random;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public ArrowsContext setContext(Context context) {
        this.context = context;
        return this;
    }

    public static void init(int noEntries, int width, int height) {
        if (entries == null) {
            entries = new Entry[noEntries];

            for (int i = 0; i < noEntries; i++) {
                entries[i] = new Entry(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
            }
        }
    }

    public static void destory() {
        for (Entry e : entries) {
            e.bitmap.recycle();
        }
        entries = null;
    }

    public synchronized Bitmap get() {
        for (Entry e : entries) {
            if (!e.used) {
                e.used = true;
                return e.bitmap;
            }
        }

        throw new RuntimeException("No available Bitmap!");
    }

    public static synchronized void release(Bitmap b) {
        for(Entry e : entries) {
            if (e.bitmap == b) {
                e.used = false;
                e.bitmap.eraseColor(0xff9944aa);
                return;
            }
        }
    }

    private static class Entry {
        boolean used = false;
        Bitmap bitmap;

        public Entry(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }
}

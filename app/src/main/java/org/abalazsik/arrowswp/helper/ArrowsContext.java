package org.abalazsik.arrowswp.helper;

import java.util.Random;

public class ArrowsContext {
    private int width;
    private int height;
    private GraphicsOptions graphicsOptions;
    private Random random;//for random, but reproducible images

    public ArrowsContext(int width, int height, GraphicsOptions graphicsOptions) {
        this.width = width;
        this.height = height;
        this.graphicsOptions = graphicsOptions;
        random = new Random();
    }

    public ArrowsContext(int width, int height, GraphicsOptions graphicsOptions, Random random) {
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
}


package org.abalazsik.arrowswp.helper;

import org.abalazsik.arrowswp.Constants;

/**
 *
 * @author ador
 */
public class GraphicsOptions {

	private boolean displacementEnabled = false;
	private float disp1 = 0.1f;
	private float disp2 = 0.2f;

	private int color = Constants.ColorsAsInts.RED1;
	private int color2 = Constants.ColorsAsInts.RED2;
	private int borderColor = Constants.ColorsAsInts.BLACK;

	private boolean randomColor = false;

	private int backgroundColor = Constants.ColorsAsInts.BLACK;
	private boolean wrapBackground = false;
	private boolean withBorder = true;
	
	public boolean isDisplacementEnabled() {
		return displacementEnabled;
	}

	public GraphicsOptions setDisplacementEnabled(boolean displacementEnabled) {
		this.displacementEnabled = displacementEnabled;
		return this;
	}

	public float getDisp1() {
		return disp1;
	}

	public GraphicsOptions setDisp1(float disp1) {
		this.disp1 = disp1;
		return this;
	}

	public float getDisp2() {
		return disp2;
	}

	public GraphicsOptions setDisp2(float disp2) {
		this.disp2 = disp2;
		return this;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public GraphicsOptions setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public boolean isWrapBackground() {
		return wrapBackground;
	}

	public GraphicsOptions setWrapBackground(boolean wrapBackground) {
		this.wrapBackground = wrapBackground;
		return this;
	}

	public int getColor() {
		return color;
	}

	public GraphicsOptions setColor(int color) {
		this.color = color;
		return this;
	}

	public int getColor2() {
		return color2;
	}

	public GraphicsOptions setColor2(int color2) {
		this.color2 = color2;
		return this;
	}

	public boolean isRandomColor() {
		return randomColor;
	}

	public GraphicsOptions setRandomColor(boolean randomColor) {
		this.randomColor = randomColor;
		return this;
	}

	public int getBorderColor() {
		return borderColor;
	}

	public GraphicsOptions setBorderColor(int borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	public boolean withBorder() {
		return withBorder;
	}

	public GraphicsOptions setWithBorder(boolean withBorder) {
		this.withBorder = withBorder;
		return this;
	}
}

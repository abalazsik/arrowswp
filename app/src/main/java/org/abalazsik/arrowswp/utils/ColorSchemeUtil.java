
package org.abalazsik.arrowswp.utils;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;

import java.util.Random;

/**
 *
 * @author ador
 */
public class ColorSchemeUtil {

	private static final String[] SCHEMES = {
			Constants.Strings.RED,
			Constants.Strings.PINK,
			Constants.Strings.BLUE,
			Constants.Strings.GREEN,
			Constants.Strings.ORANGE
	};

	public static GraphicsOptions applyColorScheme(String scheme, GraphicsOptions options, Random random) {

		if (Constants.Strings.RED.equals(scheme)) {
			options
					.setColor(Constants.ColorsAsInts.RED1)
					.setColor2(Constants.ColorsAsInts.RED2);
		} else if (Constants.Strings.PINK.equals(scheme)) {
			options
					.setColor(Constants.ColorsAsInts.PINK)
					.setColor2(Constants.ColorsAsInts.GRAY);
		} else if (Constants.Strings.BLUE.equals(scheme)) {
			options
					.setColor(Constants.ColorsAsInts.BLUE1)
					.setColor2(Constants.ColorsAsInts.BLUE2);

		} else if (Constants.Strings.GREEN.equals(scheme)) {
			options
					.setColor(Constants.ColorsAsInts.GREEN1)
					.setColor2(Constants.ColorsAsInts.GREEN2);

		} else if (Constants.Strings.ORANGE.equals(scheme)) {
			options
					.setColor(Constants.ColorsAsInts.ORANGE1)
					.setColor2(Constants.ColorsAsInts.ORANGE2);
		} else if (Constants.Strings.RANDOM.equals(scheme)) {
			return applyColorScheme(SCHEMES[random.nextInt(SCHEMES.length)], options, null);
		}

		return options;
	}

	public static GraphicsOptions applyBackgroundShade(String shade, GraphicsOptions options) {
		if (Constants.Strings.DARK.equals(shade)) {
			return options.setBackgroundColor(Constants.ColorsAsInts.BLACK);
		} else {
			return options.setBackgroundColor(Constants.ColorsAsInts.WHITE);
		}
	}
}

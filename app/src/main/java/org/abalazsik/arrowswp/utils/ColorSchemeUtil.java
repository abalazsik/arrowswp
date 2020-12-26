
package org.abalazsik.arrowswp.utils;

import org.abalazsik.arrowswp.Constants;
import org.abalazsik.arrowswp.helper.GraphicsOptions;

/**
 *
 * @author ador
 */
public class ColorSchemeUtil {

	public static GraphicsOptions applyColorScheme(String scheme, GraphicsOptions options) {
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
		}

		return options;
	}
}

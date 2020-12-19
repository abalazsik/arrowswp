package org.abalazsik.arrowswp;

import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.helper.GraphicsOptions;
import org.abalazsik.arrowswp.utils.GraphicsUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

public class GraphicsUtilTest {

    private static final Logger LOG = Logger.getLogger(GraphicsUtilTest.class.getName());

    @Test
    public void randomColorTest() {

        ArrowsContext context = new ArrowsContext(200, 200,
                new GraphicsOptions()
                        .setColor(0xffff0000)
                        .setColor2(0xff304300)
                        .setRandomColor(true)
        );

        for (int i = 0; i < 3; i++) {
            int color = GraphicsUtil.getColor(context);

            int red = (color >> 16) & 0xff;

            int green = (color >> 8) & 0xff;

            LOG.info("color: " + Long.toString(color, 16));

            Assert.assertTrue(red <= 0xff);
            Assert.assertTrue(red >= 0x30);

            Assert.assertTrue(green >= 0x00);
            Assert.assertTrue(green <= 0x43);
        }
    }
}
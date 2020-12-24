package org.abalazsik.arrowswp.utils;

import android.graphics.Bitmap;

public class GlowEffectUtil {

    public static Bitmap glow(Bitmap source, int blurSize) {
        if (blurSize < 2) {
            throw new RuntimeException();
        }

        Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        float[] elements = new float[blurSize * blurSize];

        for (int y = 0; y < blurSize; y++) {
            for (int x = 0; x < blurSize; x++) {
                elements[y * blurSize + x] = 1f;
            }
        }

        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {

                int red = 0;
                int green = 0;
                int blue = 0;

                int cnt = 0;

                for (int ky = 0; ky < blurSize; ky++) {
                    for (int kx = 0; kx < blurSize; kx++) {

                        int _x = x + kx - blurSize / 2;
                        int _y = y + ky - blurSize / 2;

                        if (_x >= 0 && _x < source.getWidth() && _y >= 0 && _y < source.getHeight()) {
                            int color = source.getPixel(_x, _y);
                            float f = elements[ky * blurSize + kx];

                            red += (int) ((color >> 16) & 0xff) * f;
                            green += (int) ((color >> 8) & 0xff) * f;
                            blue += (int) ((color) & 0xff) * f;
                            cnt++;
                        }
                    }
                }
                int color = source.getPixel(x, y);

                int color3 = shiftMax(color, red / cnt, 16)
                        | shiftMax(color, green / cnt, 8)
                        | shiftMax(color, blue / cnt, 0)
                        | 0xff000000;

                result.setPixel(x, y, color3);
            }
        }

        return result;
    }

    private static int shiftMax(int max, int val, int shift) {
        int k = (max >> shift) & 0xff;

        if (k > val) {
            return k << shift;
        }

        return val << shift;
    }

}

package org.abalazsik.arrowswp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.abalazsik.arrowswp.generator.BackgroundGenerator;
import org.abalazsik.arrowswp.generator.Ceres;
import org.abalazsik.arrowswp.generator.Jupiter;
import org.abalazsik.arrowswp.generator.Mars;
import org.abalazsik.arrowswp.generator.Mercury;
import org.abalazsik.arrowswp.generator.Neptune;
import org.abalazsik.arrowswp.generator.Saturn;
import org.abalazsik.arrowswp.generator.Venus;
import org.abalazsik.arrowswp.helper.ArrowsContext;
import org.abalazsik.arrowswp.utils.ColorSchemeUtil;

import java.util.Random;

//CHECK OUT THIS CHOON: https://soundcloud.com/justicehardcore/5year

public class ArrowsWPService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new ArrowsWPEngine();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ArrowsContext.destory();
    }

    public class ArrowsWPEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {
        private Bitmap bitmap;
        private long lastUpdate = 0;
        private BackgroundGenerator generator;
        private SharedPreferences prefs;
        private boolean rendering = false;
        private String colorScheme = Constants.Strings.DEFAULT;
        private String backgroundShade = Constants.Strings.DARK;

        private static final long DEBOUNCE_INTERVAL = 4000;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.setTouchEventsEnabled(true);
            prefs = PreferenceManager
                    .getDefaultSharedPreferences(ArrowsWPService.this);
            prefs.registerOnSharedPreferenceChangeListener(this);
            generator = getGeneratorByString(prefs.getString(Constants.UI.GENERATOR_TYPE, Constants.PlanetNames.JUPITER));
            colorScheme = prefs.getString(Constants.UI.COLOR_SCHEME, Constants.Strings.DEFAULT);
            backgroundShade = prefs.getString(Constants.UI.BACKGROUND_SHADE, Constants.Strings.DARK);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                debounceAndRender(getSurfaceHolder(), false);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            prefs.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            debounceAndRender(holder, false);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            debounceAndRender(getSurfaceHolder(), false);
        }

        private void debounceAndRender(SurfaceHolder holder, boolean force) {
            if (!rendering) {
                if (force || System.currentTimeMillis() - lastUpdate > DEBOUNCE_INTERVAL) {
                    rendering = true;
                    Rect rect = holder.getSurfaceFrame();
                    if (bitmap != null) {
                        ArrowsContext.release(bitmap);
                    }
                    bitmap = renderImage(rect.width(), rect.height(), ArrowsWPService.this);
                    lastUpdate = System.currentTimeMillis();
                    rendering = false;
                }
                drawFrame();
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (Constants.UI.GENERATOR_TYPE.equals(key)) {
                generator = getGeneratorByString(prefs.getString(Constants.UI.GENERATOR_TYPE, Constants.PlanetNames.JUPITER));
            } else if (Constants.UI.COLOR_SCHEME.equals(key)) {
                colorScheme = prefs.getString(Constants.UI.COLOR_SCHEME, Constants.Strings.DEFAULT);
            } else if (Constants.UI.BACKGROUND_SHADE.equals(key)) {
                backgroundShade = prefs.getString(Constants.UI.BACKGROUND_SHADE, Constants.Strings.DARK);
            }
        }

        private Bitmap renderImage(int width, int height, Context context) {
            Random random = new Random();
            return generator.generate(
                    new ArrowsContext(width, height, context,
                            ColorSchemeUtil.applyBackgroundShade(backgroundShade,
                                    ColorSchemeUtil.applyColorScheme(colorScheme,
                                            generator.getPrefferedGraphicsOptions(), random
                                            )), random));
        }

        private void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;

            try {
                canvas = holder.lockCanvas();

                if (canvas != null) {
                    canvas.drawColor(0);
                    if(bitmap != null) {
                        canvas.drawBitmap(bitmap, 0, 0, null);

                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            } catch(IllegalStateException | IllegalArgumentException e) {
                //failed to lock canvas, nothing to do
            }
        }

        public BackgroundGenerator getGeneratorByString(String type) {
            if (type == null || Constants.PlanetNames.JUPITER.equals(type)) {
                return new Jupiter();
            } else if (Constants.PlanetNames.NEPTUNE.equals(type)) {
                return new Neptune();
            } else if (Constants.PlanetNames.MARS.equals(type)) {
                return new Mars();
            } else if (Constants.PlanetNames.MERCURY.equals(type)) {
                return new Mercury();
            } else if (Constants.PlanetNames.VENUS.equals(type)) {
                return new Venus();
            } else if (Constants.PlanetNames.CERES.equals(type)) {
                return new Ceres();
            } else {
                return new Saturn();
            }
        }
    }
}

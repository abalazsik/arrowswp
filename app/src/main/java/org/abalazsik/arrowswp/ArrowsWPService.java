package org.abalazsik.arrowswp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import org.abalazsik.arrowswp.generator.BackgroundGenerator;
import org.abalazsik.arrowswp.generator.Jupiter;
import org.abalazsik.arrowswp.generator.Mars;
import org.abalazsik.arrowswp.generator.Neptune;
import org.abalazsik.arrowswp.generator.Saturn;
import org.abalazsik.arrowswp.helper.ArrowsContext;

//CHECK OUT THIS CHOON: https://soundcloud.com/justicehardcore/5year

public class ArrowsWPService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new ArrowsWPEngine();
    }

    public class ArrowsWPEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {
        private Bitmap bitmap;
        private long lastUpdate = 0;
        private BackgroundGenerator generator;
        private SharedPreferences prefs;

        private static final long DEBOUNCE_INTERVAL = 7000;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            prefs = PreferenceManager
                    .getDefaultSharedPreferences(ArrowsWPService.this);
            prefs.registerOnSharedPreferenceChangeListener(this);
            generator = getGeneratorByString(prefs.getString("generator-type", "Jupiter"));
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
            if (force || System.currentTimeMillis() - lastUpdate > DEBOUNCE_INTERVAL) {
                Rect rect = holder.getSurfaceFrame();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                bitmap = renderImage(rect.width(), rect.height());
                lastUpdate = System.currentTimeMillis();
            }
            drawFrame();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (Constants.UI.GENERATOR_TYPE.equals(key)) {
                generator = getGeneratorByString(prefs.getString("generator-type", "Jupiter"));
                debounceAndRender(getSurfaceHolder(), true);
            }
        }

        private Bitmap renderImage(int width, int height) {
            return generator.generate(new ArrowsContext(width, height, generator.getPrefferedGraphicsOptions()));
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
                    }
                }
            } finally {
                holder.unlockCanvasAndPost(canvas);
            }
        }

        public BackgroundGenerator getGeneratorByString(String type) {
            if (type == null || "Jupiter".equals(type)) {
                return new Jupiter();
            } else if ("Neptune".equals(type)) {
                return new Neptune();
            } else if ("Mars".equals(type)) {
                return new Mars();
            } else {
                return new Saturn();
            }
        }
    }
}

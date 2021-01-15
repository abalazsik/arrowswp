package org.abalazsik.arrowswp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button toWallpapersButton = (Button)this.findViewById(R.id.to_wallpapers_button);

        toWallpapersButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        startActivity(intent);
    }
}
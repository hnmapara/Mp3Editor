package com.mapara.mp3editor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.mapara.mp3editor.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by harshit on 2/14/14.
 */
public class GIFView extends View {

    private Movie mMovie;
    private long movieStart;

    public GIFView(Context context) {
        super(context);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    private void initializeView() {
//        InputStream is = getContext().getResources().openRawResource(
//                R.drawable.rotating_earth);
//        mMovie = Movie.decodeStream(is);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();

        if (movieStart == 0) {
            movieStart = (int) now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight()
                    - mMovie.height());
            this.invalidate();
        }
    }}
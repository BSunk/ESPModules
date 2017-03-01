package com.bsunk.makeglow;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Bryan on 3/1/2017.
 */

public class Util {

     static Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return getBitmapFromBitmapDrawable(drawable);
        }
        else if(drawable instanceof VectorDrawable) {
            return getBitmapFromVectorDrawable(drawable);
        }
        return null;
    }

     private static Bitmap getBitmapFromVectorDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

     private static Bitmap getBitmapFromBitmapDrawable (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

     static BlurMaskFilter getFilter(int glow, int filter) {
        switch (filter) {
            case 0:
                return new BlurMaskFilter(glow, BlurMaskFilter.Blur.INNER);
            case 1:
                return new BlurMaskFilter(glow, BlurMaskFilter.Blur.OUTER);
            case 2:
                return new BlurMaskFilter(glow, BlurMaskFilter.Blur.NORMAL);
            case 3:
                return new BlurMaskFilter(glow, BlurMaskFilter.Blur.SOLID);
        }
        return new BlurMaskFilter(glow, BlurMaskFilter.Blur.SOLID);
    }
}

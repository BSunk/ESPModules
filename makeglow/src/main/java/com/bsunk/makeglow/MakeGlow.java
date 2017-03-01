package com.bsunk.makeglow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import static com.bsunk.makeglow.Util.getBitmap;
import static com.bsunk.makeglow.Util.getFilter;

/**
 * Created by Bryan on 2/28/2017.
 */

public class MakeGlow extends View {

    private int glowRadius = 5;
    private int margin = 24;
    private int halfMargin = margin / 2;

    private Drawable srcIcon;
    private int glowColor;
    private int glowOffColor;
    private boolean glowOff;
    private Bitmap src;
    private Bitmap bmp;
    private Bitmap alpha;
    private Paint paint = new Paint();
    private Canvas canvasImage;
    private int filter = 0;

    public MakeGlow(Context context) {
        super(context);
        init(context, null);
    }

    public MakeGlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        srcIcon = ContextCompat.getDrawable(context, R.drawable.ic_android_black_24dp);
        glowColor = ContextCompat.getColor(context, R.color.default_glow_color);
        glowOffColor = ContextCompat.getColor(context, R.color.default_off_color);

        if(attrs!=null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MakeGlow, 0, 0);

            Drawable indicatorIcon = a.getDrawable(R.styleable.MakeGlow_drawable);
            if (indicatorIcon != null) {
                srcIcon = indicatorIcon;}
            glowColor = a.getColor(R.styleable.MakeGlow_glowColor, glowColor);
            glowRadius = a.getInt(R.styleable.MakeGlow_glowRadius, glowRadius);
            glowOffColor = a.getColor(R.styleable.MakeGlow_glowOffColor, glowOffColor);
            glowOff = a.getBoolean(R.styleable.MakeGlow_glowOff, false);
            filter = a.getInt(R.styleable.MakeGlow_blurFilter, 0);
            a.recycle();
        }

        src = Util.getBitmap(srcIcon);
        drawImage();
    }

    private void drawImage() {
        bmp = Bitmap.createBitmap(src.getWidth() + margin, src.getHeight() + margin, Bitmap.Config.ARGB_8888);
        canvasImage = new Canvas(bmp);
        alpha = src.extractAlpha();
        if(glowOff) {
            paint.setColor(glowOffColor);
            BlurMaskFilter blurMaskFilter = getFilter(1, filter);
            paint.setMaskFilter(blurMaskFilter);
        }
        else {
            paint.setColor(glowColor);
            BlurMaskFilter blurMaskFilter = getFilter(glowRadius, filter);
            paint.setMaskFilter(blurMaskFilter);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasImage.drawBitmap(alpha, halfMargin, halfMargin, paint);
        canvas.drawBitmap(bmp, halfMargin, halfMargin, null);
    }

    public void setSrcIcon(Drawable srcIcon) {
        this.srcIcon = srcIcon;
        src = getBitmap(srcIcon);
        drawImage();
        invalidate();
        requestLayout();
    }

    public void setGlowColor(int glowColor) {
        this.glowColor = glowColor;
        drawImage();
        invalidate();
        requestLayout();
    }

    public int getGlowRadius() {
        return glowRadius;
    }

    public void setGlowRadius(int glowRadius) {
        this.glowRadius = glowRadius;
        drawImage();
        invalidate();
        requestLayout();
    }

    public int getGlowOffColor() {
        return glowOffColor;
    }

    public void setGlowOffColor(int glowOffColor) {
        this.glowOffColor = glowOffColor;
        drawImage();
        invalidate();
        requestLayout();
    }

    public boolean isGlowOff() {
        return glowOff;
    }

    public void setGlowOff(boolean glowOff) {
        this.glowOff = glowOff;
        drawImage();
        invalidate();
        requestLayout();
    }

}

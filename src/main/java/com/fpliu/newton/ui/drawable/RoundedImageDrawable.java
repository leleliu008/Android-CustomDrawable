package com.fpliu.newton.ui.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * 图片的圆角化
 *
 * @author 792793182@qq.com 2015-07-06
 */
public class RoundedImageDrawable extends Drawable {

    private final float cornerRadius;
    private final int margin;

    private final BitmapShader bitmapShader;
    private final RectF mRect = new RectF();
    private final RectF mBitmapRect;
    private final Paint paint;

    public RoundedImageDrawable(Bitmap bitmap, int cornerRadius, int margin) {
        this.cornerRadius = cornerRadius;
        this.margin = margin;

        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mBitmapRect = new RectF(margin, margin, bitmap.getWidth() - margin,
                bitmap.getHeight() - margin);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRect.set(margin, margin, bounds.width() - margin, bounds.height()
                - margin);

        // Resize the original bitmap to fit the new bound
        Matrix shaderMatrix = new Matrix();
        shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
        bitmapShader.setLocalMatrix(shaderMatrix);
    }
}
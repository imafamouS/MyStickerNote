package com.infamous.fdsa.mysticker.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by apple on 5/22/17.
 */

public class LineEditText extends android.support.v7.widget.AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint.setColor(Color.BLUE);
    }

    public void setColor(String colorBackground, String colorText) {
        setBackgroundColor(Color.parseColor(colorBackground));
        mPaint.setColor(Color.parseColor(colorText));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int i = 0;
        int lineCount = getLineCount();
        Rect rect = this.mRect;
        Paint paint = this.mPaint;
        int i2 = 0;
        while (i2 < lineCount) {
            int lineBounds = getLineBounds(i2, rect);
            canvas.drawLine((float) rect.left, (float) (lineBounds + 1), (float) rect.right, (float) (lineBounds + 1), paint);
            i2++;
            i = lineBounds;
        }
        while (i < getHeight()) {
            i2 = i + getLineHeight();
            canvas.drawLine((float) rect.left, (float) (i2 + 1), (float) rect.right, (float) (i2 + 1), paint);
            i = i2;
        }
        super.onDraw(canvas);

    }
}
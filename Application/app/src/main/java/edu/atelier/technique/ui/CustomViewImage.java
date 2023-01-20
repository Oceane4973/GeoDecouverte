package edu.atelier.technique.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

/**
 * Cette classe permet d'affiché des Bitmap de façon dynamique
 */
public class CustomViewImage extends androidx.appcompat.widget.AppCompatImageView {

    /**
     * CustomViewImage
     * @param context
     */
    public CustomViewImage(Context context) {
        super(context);
    }

    /**
     * CustomViewImage
     * @param context
     * @param attrs
     */
    public CustomViewImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * CustomViewImage
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomViewImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * redimentionne le bitmap
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
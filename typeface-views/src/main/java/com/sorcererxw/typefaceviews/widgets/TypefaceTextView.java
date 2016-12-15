package com.sorcererxw.typefaceviews.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sorcererxw.typefaceviews.TypefaceViews;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/11/25
 */

public class TypefaceTextView extends TextView {
    public TypefaceTextView(Context context) {
        super(context);
        init();
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setTypeface(TypefaceViews.getRegularTypeface());
    }
}

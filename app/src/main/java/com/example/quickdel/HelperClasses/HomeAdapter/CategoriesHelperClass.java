package com.example.quickdel.HelperClasses.HomeAdapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {

    int imageView;
    String textView;
    Drawable relativeLayout;

    public CategoriesHelperClass(int imageView, String textView, Drawable relativeLayout) {
        this.imageView = imageView;
        this.textView = textView;
        this.relativeLayout = relativeLayout;
    }

    public int getImageView() {
        return imageView;
    }

    public String getTextView() {
        return textView;
    }

    public Drawable getRelativeLayout() {
        return relativeLayout;
    }
}

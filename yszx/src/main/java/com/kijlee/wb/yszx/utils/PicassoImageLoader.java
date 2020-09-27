package com.kijlee.wb.yszx.utils;


import android.app.Activity;
import android.content.Context;

import com.kijlee.wb.yszx.R;
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;


/**
 * PicassoImageLoader
 * Created by Yancy on 2016/10/31.
 */
public class PicassoImageLoader implements ImageLoader {

    private final static String TAG = "PicassoImageLoader";

    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {

        Picasso.with(context)
                .load("file://" + path)
                .resize(width, height)
                .placeholder(R.mipmap.gallery_pick_photo)
                .error(R.mipmap.ic_launcher)
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
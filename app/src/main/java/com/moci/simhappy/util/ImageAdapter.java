package com.moci.simhappy.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.moci.simhappy.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    public ImageAdapter(Context c) {
        mContext = c;
    }
    /*获取当前图片数量*/
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
    /* 根据需要position获得在GridView中的对象*/
    @Override
    public Object getItem(int position) {
        return position;
    }
    /*获得在GridView中对象的ID */
    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            /*实例化ImageView对象*/
            imageView = new ImageView(mContext);
            /* 设置ImageView对象布局，设置View的height和width */
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            /* 设置边界对齐*/
            imageView.setAdjustViewBounds(false);
            /* 按比例同意缩放图片（保持图片的尺寸比例）*/
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            /* 设置间距*/
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.r, R.drawable.r,
            R.drawable.r, R.drawable.r, R.drawable.r,
            R.drawable.r, R.drawable.r, R.drawable.r,
            R.drawable.sr, R.drawable.ssr
    };
}

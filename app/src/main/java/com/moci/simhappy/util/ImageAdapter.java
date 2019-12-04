package com.moci.simhappy.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.moci.simhappy.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ImageView imageView2;
    private Button btn1;
    private Button btn2;
    private Integer[] icon;
    public ImageAdapter(Context c,ImageView iv, Button b1, Button b2, Integer[] i) {
        mContext = c;
        imageView2 = iv;
        btn1 = b1;
        btn2 = b2;
        icon = i;
    }
    /*获取当前图片数量*/
    @Override
    public int getCount() {
        return icon.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
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
        imageView.setImageResource(icon[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView2.bringToFront();
                imageView2.setVisibility(View.VISIBLE);
                imageView2.setClickable(true);
                imageView2.setImageResource(icon_img(icon[position]));

                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
            }
        });
        return imageView;
    }

    private Integer icon_img(Integer icon){
        return CardType.getImg(icon);
    }
}

package ru.ATetiukhin.checkers.checkers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import ru.ATetiukhin.checkers.R;

import java.util.ArrayList;

/**
 * This class consists methods that controlling resources.
 *
 * @author  Artyom Tetiukhin
 * @version 1.0
 */
public class ImageAdapterBoard extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mThumbIds;
    private int mCountCages;

    public ImageAdapterBoard(Context c, int countCages, int w, int h) {
        mContext = c;
        mCountCages = countCages;
        int width = w / countCages;
        int height = width;

        Integer[] thumbIds = {
                R.drawable.background_light_wood,
                R.drawable.background_dark_wood,
        };

        mThumbIds = new ArrayList<Bitmap>(thumbIds.length);

        for (int id : thumbIds) {
            Bitmap bmOriginal = BitmapFactory.decodeResource(mContext.getResources(), id);

            bmOriginal = Bitmap.createScaledBitmap(bmOriginal, width, height, false);
            mThumbIds.add(bmOriginal);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        if (position / mCountCages % 2 == 0) {
            imageView.setImageBitmap(mThumbIds.get(position % 2));
        } else {
            imageView.setImageBitmap(mThumbIds.get(1 - (position % 2)));
        }

        return imageView;
    }

    @Override
    public int getCount() {
        return mCountCages * mCountCages;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
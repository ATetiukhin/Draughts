package ru.ATetiukhin.checkers.checkers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import ru.ATetiukhin.checkers.Activity.CheckerBoard;
import ru.ATetiukhin.checkers.R;

import java.util.ArrayList;

public class ImageAdapterCheckers extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mThumbIds;

    CheckerBoard board;

    public ImageAdapterCheckers(Context c, int countCages, int w, int h) {
        mContext = c;
        int width = w / countCages;
        int height = width;

        Integer[] thumbIds = {
                R.drawable.pion_blanc,
                R.drawable.pion_bleu,
                R.drawable.blank,
        };

        mThumbIds = new ArrayList<Bitmap>(thumbIds.length);
        for (int id : thumbIds) {
            Bitmap bmOriginal = BitmapFactory.decodeResource(mContext.getResources(), id);
            bmOriginal = Bitmap.createScaledBitmap(bmOriginal, width, height, false);
            mThumbIds.add(bmOriginal);
        }

        board = new CheckerBoard(countCages);
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

        switch (board.getValue(position)) {
            case LIGHT_CHECKER:
                imageView.setImageBitmap(mThumbIds.get(0));
                break;
            case DARK_CHECKER:
                imageView.setImageBitmap(mThumbIds.get(1));
                break;
            default:
                imageView.setImageBitmap(mThumbIds.get(2));
                break;
        }

        return imageView;
    }

    public void touchScreen(int position) {
        if (board.checkerSelected) {
            board.setMovePiece(position);
        } else {
            board.checkerSelected(position);
            //board.isAdditionalCapture();
        }
    }

    @Override
    public int getCount() {
        int countCages = board.getCountCages();
        return countCages * countCages;
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
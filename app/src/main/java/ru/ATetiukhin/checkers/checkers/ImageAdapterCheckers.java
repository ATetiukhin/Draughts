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
public class ImageAdapterCheckers extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mThumbIds;

    CheckerBoard board;

    public ImageAdapterCheckers(Context c, int countCages, int w, int h) {
        mContext = c;
        int width = w / countCages;
        int height = width;

        Integer[] thumbIds = new Integer[]{
                R.drawable.pawn_wood_3d_beige,
                R.drawable.pawn_wood_3d_black,
                R.drawable.queen_wood_3d_beige,
                R.drawable.queen_wood_3d_black,
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
            case LIGHT_KING:
                imageView.setImageBitmap(mThumbIds.get(2));
                break;
            case DARK_KING:
                imageView.setImageBitmap(mThumbIds.get(3));
                break;
            default:
                imageView.setImageBitmap(mThumbIds.get(4));
                break;
        }

        if (board.checkerSelected && position == board.getActualCage()) {
            imageView.setBackgroundColor(R.color.background_selected_checker);
        } else {
            imageView.setBackground(null);
        }

        return imageView;
    }

    public void touchScreen(int position) {
        if (board.checkerSelected) {
            board.setMovePiece(position);
        } else {
            board.checkerSelected(position);
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
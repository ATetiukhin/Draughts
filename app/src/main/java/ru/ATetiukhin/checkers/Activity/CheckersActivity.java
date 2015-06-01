package ru.ATetiukhin.checkers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import ru.ATetiukhin.checkers.R;
import ru.ATetiukhin.checkers.checkers.ImageAdapterBoard;
import ru.ATetiukhin.checkers.checkers.ImageAdapterCheckers;

public class CheckersActivity extends ActionBarActivity implements View.OnClickListener {
    GridView gridViewCheckers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);

        Intent intent = getIntent();
        int countCages = Integer.parseInt(intent.getStringExtra("countCages"));

        GridView gridViewBoard = (GridView)findViewById(R.id.gridViewBoard);
        gridViewBoard.setAdapter(new ImageAdapterBoard(this, countCages, metrics.widthPixels, metrics.heightPixels));
        gridViewBoard.setNumColumns(countCages);

        gridViewCheckers = (GridView)findViewById(R.id.gridViewCheckers);
        gridViewCheckers.setAdapter(new ImageAdapterCheckers(this, countCages, metrics.widthPixels, metrics.heightPixels));
        gridViewCheckers.setNumColumns(countCages);

        gridViewCheckers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ((ImageAdapterCheckers)gridViewCheckers.getAdapter()).touchScreen(position);
                gridViewCheckers.invalidateViews();
//                Toast.makeText(CheckersActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_quit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}

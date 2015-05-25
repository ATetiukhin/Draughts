package ru.ATetiukhin.checkers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import ru.ATetiukhin.checkers.R;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Button mRussianCheckers;
    private Button mInternationalDraughts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRussianCheckers = (Button)findViewById(R.id.russian_checkers);
        mInternationalDraughts = (Button)findViewById(R.id.international_draughts);

        mRussianCheckers.setOnClickListener(this);
        mInternationalDraughts.setOnClickListener(this);
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

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.russian_checkers:
                intent = new Intent(this, CheckersActivity.class);
                intent.putExtra("countCages", "8");
                startActivity(intent);
                break;

            case R.id.international_draughts:
                intent = new Intent(this, CheckersActivity.class);
                intent.putExtra("countCages", "10");
                startActivity(intent);
                break;

            case R.id.load_saved_game:
                break;

            case R.id.options:
                break;
        }
    }
}

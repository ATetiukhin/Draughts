package ru.ATetiukhin.checkers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import ru.ATetiukhin.checkers.R;
import ru.ATetiukhin.checkers.checkers.ImageAdapterBoard;
import ru.ATetiukhin.checkers.checkers.ImageAdapterCheckers;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CheckersActivity extends ActionBarActivity implements View.OnClickListener {
    GridView gridViewCheckers;

    private Socket socket;
    private static final int SERVERPORT = 1520;
    private static final String SERVER_IP = "185.83.216.89";

    private EditText textField;
    private Button button;

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

        textField = (EditText) findViewById(R.id.EditText01); // reference to the text field
        button = (Button) findViewById(R.id.myButton); // reference to the send button

        new Thread(new ClientThread()).start();

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
    public void onClick(View view) {
        try {
            EditText et = (EditText) findViewById(R.id.EditText01);
            String str = et.getText().toString();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.print(str);
            out.flush();

            String line = in.readLine();
            Toast.makeText(CheckersActivity.this, "" + line, Toast.LENGTH_SHORT).show();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

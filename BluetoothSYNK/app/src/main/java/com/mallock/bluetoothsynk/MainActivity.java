package com.mallock.bluetoothsynk;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ProgressDialog pDialog;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        list= (ListView) findViewById(R.id.list);
        setList();
    }
    public void setList()
    {
        HashMap<String,String> songs;
        songs=getMusicFiles();
        ArrayList<String> listOfSongs= new ArrayList<>();
        for(String song:songs.keySet())
        {
            listOfSongs.add(song);
        }
        ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_child, listOfSongs);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView)view;
                Log.e("Clicked item: ",tv.getText().toString());
            }
        });
    }

    public HashMap<String,String> getMusicFiles()
    {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = MainActivity.this.managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        HashMap<String,String> songs = new HashMap<>();
        while(cursor.moveToNext()) {
            songs.put(cursor.getString(4).replace(".mp3","")+"\n",cursor.getString(3));
        }
        return songs;
    }

}

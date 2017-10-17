package taiwan.beginner.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    HashSet<Map<String, String>> dataset = new HashSet<>();
    Map<String, String> set = new HashMap<>();
    ArrayAdapter cityadapter, countryadapter, roadadapter;
    String[] cityArray, countryArray, roadArray;
    AutoCompleteTextView city, country, road;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initaddress();
        initcity();
        init();


//        country = (AutoCompleteTextView) findViewById(R.id.first);
//        countryadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countryArray);
//        country.setThreshold(1);
//        country.setAdapter(countryadapter);

    }

    public void onclick(View view) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=台北市中山北路七段&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void initaddress() {
        try {
            File file = new File(getFilesDir() + File.separator + "address.sqlite");
            /*
            如果沒有複製過就複製
             */
            if (!file.exists()) {
                InputStream is = getResources().openRawResource(R.raw.address);
                OutputStream os = new FileOutputStream(getFilesDir() + File.separator + "address.sqlite");
                int i = 0;
                while (i != -1) {
                    i = is.read();
                    os.write(i);
                }
                is.close();
                os.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        String path = getFilesDir().getAbsolutePath() + File.separator + "address.sqlite";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        String[] Columns = {"city", "country", "road"};
        Cursor cursor = sqLiteDatabase.query("streetname", Columns, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            set.put("city", cursor.getString(0));
            set.put("country", cursor.getString(1));
            set.put("road", cursor.getString(2));
            dataset.add(set);
        } while (cursor.moveToNext());




    }

    public void initcity() {
        Set<String> set = new HashSet();
        String path = getFilesDir().getAbsolutePath() + File.separator + "address.sqlite";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        String[] Columns = {"city", "country", "road"};
        Cursor cursor = sqLiteDatabase.query("streetname", Columns, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            set.add(cursor.getString(0));
        } while (cursor.moveToNext());
        cityArray = set.toArray(new String[set.size()]);
        city = (AutoCompleteTextView) findViewById(R.id.first);
        cityadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cityArray);
        city.setThreshold(1);
        city.setAdapter(cityadapter);
    }

    public void initcountry() {
        Set<String> set = new HashSet();
        String path = getFilesDir().getAbsolutePath() + File.separator + "address.sqlite";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        String[] Columns = {"city", "country", "road"};
        Cursor cursor = sqLiteDatabase.query("streetname", Columns, null, null, null, null, null);
        cursor.moveToFirst();


    }

}


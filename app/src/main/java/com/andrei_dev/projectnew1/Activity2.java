package com.andrei_dev.projectnew1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText dis,pair;
    Spinner day;
    String select_item;
    TextView textView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        dis = (EditText) findViewById(R.id.tx_dis);
        pair = (EditText) findViewById(R.id.tx_pair);
        day = (Spinner) findViewById(R.id.sp_day);
        textView =(TextView) findViewById(R.id.textView) ;

        dbHelper = new DBHelper(this);



        String[] list_day = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_day);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        String Dis = dis.getText().toString();
        String Pair = pair.getText().toString();


        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {

            case R.id.btnAdd:
                select_item = day.getSelectedItem().toString();
                contentValues.put(DBHelper.KEY_DIS, Dis);
                contentValues.put(DBHelper.KEY_PAIR, Pair);
                contentValues.put(DBHelper.KEY_DAY, select_item);
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null,null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int disIndex = cursor.getColumnIndex(DBHelper.KEY_DIS);
                    int pairIndex = cursor.getColumnIndex(DBHelper.KEY_PAIR);
                    int dayIndex = cursor.getColumnIndex(DBHelper.KEY_DAY);
                    do {

                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", dis = " + cursor.getString(disIndex) +
                                ", pair = " + cursor.getString(pairIndex) +
                                ", day = " + cursor.getString(dayIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);

                break;
        }
        dbHelper.close();
    }


}


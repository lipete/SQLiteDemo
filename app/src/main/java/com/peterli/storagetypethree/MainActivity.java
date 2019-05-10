package com.peterli.storagetypethree;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyDataBaseHelper mMyDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyDataBaseHelper = new MyDataBaseHelper(getApplicationContext(), "BookStore.db", null, 2);
        Button button1 = findViewById(R.id.create_db);
        Button button2 = findViewById(R.id.add_data);
        Button button3 = findViewById(R.id.update_data);
        Button button4 = findViewById(R.id.delete_data);
        Button button5 = findViewById(R.id.query_data);
        Button button6 = findViewById(R.id.delete_db);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDataBaseHelper.getWritableDatabase();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
//                sqLiteDatabase.execSQL("insert into Book(name,author,pages,price) values (?,?,?,?)",new String[]{"AAA","aa","434","234.2"});
//                sqLiteDatabase.execSQL("insert into Book(name,author,pages,price) values (?,?,?,?)",new String[]{"BBB","bb","356","567.2"});
                ContentValues values = new ContentValues();
                values.put("name", "AAA");
                values.put("author", "aa");
                values.put("pages", 434);
                values.put("price", 234.2);
                sqLiteDatabase.insert("Book", null, values);

                values.put("name", "BBB");
                values.put("author", "bb");
                values.put("pages", 356);
                values.put("price", 567.2);
                sqLiteDatabase.insert("Book", null, values);
                Toast.makeText(getApplicationContext(), "添加数据成功", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
//                sqLiteDatabase.execSQL("update Book set prcie = ? where name = ?",new String[]{"999","BBB"});
                ContentValues values = new ContentValues();
                values.put("price", 999);
                sqLiteDatabase.update("Book", values, "name = ?", new String[]{"BBB"});
                Toast.makeText(getApplicationContext(), "更新数据成功", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
//                sqLiteDatabase.execSQL("delete from Book where price > ?", new String[]{"800"});
                sqLiteDatabase.delete("Book", "price > ?", new String[]{"800"});
                Toast.makeText(getApplicationContext(), "删除数据成功", Toast.LENGTH_SHORT).show();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = mMyDataBaseHelper.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.query("Book", null, null, null, null, null, null);
//                Cursor cursor = sqLiteDatabase.rawQuery("select * from Book where id = ? or id = ?", new String[]{"1","3"});
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "书名：" + name);
                        Log.d(TAG, "作者：" + author);
                        Log.d(TAG, "页数：" + pages);
                        Log.d(TAG, "价格：" + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDatabase("BookStore.db");
                Toast.makeText(getApplicationContext(), "删除数据库成功，准备跑路", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

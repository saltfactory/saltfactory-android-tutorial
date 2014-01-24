package net.saltfactory.tutorial.sqlite3demo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MyActivity extends Activity {
    final String databaseName = "sf_db_sqlite3_demo.db";
    final String tableName = "sf_tb_contacts";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SQLiteDatabase database = null;
        if (database == null) {
            database = openOrCreateDatabase(databaseName, SQLiteDatabase.CREATE_IF_NECESSARY, null);
            database.execSQL("DROP TABLE " + tableName);
            database.execSQL("CREATE TABLE " + tableName + " (id INTEGER, contact TEXT)");
            database.execSQL("DELETE FROM " + tableName);

            String[] contacts = getResources().getStringArray(R.array.contacts);

            int i = 1;
            for (String contact : contacts) {
                database.execSQL("INSERT INTO " + tableName + "(id, contact) values (" + i + ", '" + contact + "')");
                i++;
            }
        }

        database.close();

    }
}

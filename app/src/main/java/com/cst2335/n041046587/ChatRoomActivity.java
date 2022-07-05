package com.cst2335.n041046587;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    ListView lv;
    EditText typeHere;
    List<MessageStorage> messageModelList2 = new ArrayList<>();
    Button buttonReceive;
    Button buttonSend;
    private SQLiteDatabase db;
    ChatAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        lv = (ListView) findViewById(R.id.listView);
        typeHere = (EditText) findViewById(R.id.typeHere);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonReceive = (Button) findViewById(R.id.buttonReceive);


        // Get a database
        DatabaseOpenHelper dbOpener = new DatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        // Query all the results from the database
        String [] columns = {DatabaseOpenHelper.COL_ID, DatabaseOpenHelper.COL_CHAT,
                DatabaseOpenHelper.COL_CHAT_TYPE};
        Cursor results = db.query(false, DatabaseOpenHelper.TABLE_NAME, columns,
                null, null, null, null, null, null);

        // Output query information to LogCat
        printCursor(results);

        // Finding the column indices
        int idColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_ID);
        int chatColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_CHAT);
        int chatTypeColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_CHAT_TYPE);

        // Iterate over the res
        // results, return true if there is a next item:
        results.moveToPosition(-1);
        while(results.moveToNext())
        {
            String message = results.getString(chatColIndex);
            Boolean messageType = results.getInt(chatTypeColIndex) == 1;
            long id = results.getLong(idColIndex);

            // Add the new chat to the array list
            messageModelList2.add(new MessageStorage(message, messageType, id));
        }


        // Creating an adapter and send it to the list view
        adapter = new ChatAdapter(messageModelList2, this);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("item", messageModelList2.get(position).message);
            dataToPass.putInt("id", position);
            dataToPass.putLong("db_id", messageModelList2.get(position).id);

        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // what happens when I click the send button
                if(typeHere.getText().toString().trim().equals("")){
                    Toast.makeText(ChatRoomActivity.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Get input
                    String input = typeHere.getText().toString();

                    // Add to the database and get new id
                    ContentValues cv = new ContentValues();
                    // Put string input in the COL_CHAT columnn
                    cv.put(DatabaseOpenHelper.COL_CHAT, input);
                    // Put 1 to stand for true in the COL_CHAT_TYPE column
                    cv.put(DatabaseOpenHelper.COL_CHAT_TYPE, 1);
                    // Insert in the database
                    long newId = db.insert(DatabaseOpenHelper.TABLE_NAME, null, cv);

                    // With the new id you can create the Message object
                    MessageStorage msg = new MessageStorage(input, true, newId);

                    // Add the new chat message to the list
                    messageModelList2.add(msg);
                    // Update the list view
                    adapter.notifyDataSetChanged();

                    // Clear the editText field
                    typeHere.setText("");
                }
            }
        });

        buttonReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // what happens when I click the receive button
                if(typeHere.getText().toString().trim().equals("")){
                    Toast.makeText(ChatRoomActivity.this,"Please input some text...", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Get input
                    String input = typeHere.getText().toString();

                    // Add to the database and get new id
                    ContentValues cv = new ContentValues();
                    // Put string input in the COL_CHAT columnn
                    cv.put(DatabaseOpenHelper.COL_CHAT, input);
                    // Put 1 to stand for true in the COL_CHAT_TYPE column
                    cv.put(DatabaseOpenHelper.COL_CHAT_TYPE, 0);
                    // Insert in the database
                    long newId = db.insert(DatabaseOpenHelper.TABLE_NAME, null, cv);

                    // With the new id you can create the Message object
                    MessageStorage msg = new MessageStorage(input, false, newId);

                    // Add the new chat message to the list
                    messageModelList2.add(msg);
                    // Update the list view
                    adapter.notifyDataSetChanged();

                    // Clear the editText field
                    typeHere.setText("");
                }
            }
        });


        Log.e("ChatRoomActivity", "onCreate");

        lv.setOnItemLongClickListener((parent, view, position, id) -> {

            MessageStorage whatWasClicked = messageModelList2.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatRoomActivity.this);
            alertDialogBuilder.setTitle("Make a choice")

                    //What is the message:
                    .setMessage("Do you want to delete this?")

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        messageModelList2.remove(position);
                        adapter.notifyDataSetChanged();

                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> {

                    })

                    //An optional third button:
                    .setNeutralButton("Maybe", (click, arg) -> {
                    }).show();
            return true;
        });

    }

//    private void viewMessage() {
//        Cursor cursor = db.getAllMessages();
//        Log.d("message", String.valueOf(cursor.getCount()));
//
//        if (cursor.getCount() != 0) {
//            while (cursor.moveToNext()) {
//                MessageStorage model = new MessageStorage(cursor.getString(1), cursor.getInt(2) == 0 ? true : false, cursor.getLong(0));
//                messageModelList2.add(model);
//                ChatAdapter adt = new ChatAdapter(messageModelList2, getApplicationContext());
//                lv.setAdapter(adt);
//
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 345) {
//            if (resultCode == RESULT_OK) //if you hit the delete button instead of back button
//            {
//                long id = data.getLongExtra("db_id", 0);
//                deleteMessageId((int) id);
//            }
//        }
//    }
//
//    public void deleteMessageId(int id) {
//
//        db.deleteEntry(id);
//        messageModelList2.clear();
//        viewMessage();
//    }



    public void printCursor(Cursor c) {
        // Database Version
        Log.e("DB Version:", String.valueOf(DatabaseOpenHelper.VERSION_NUM));

        // The number of columns in the cursor.
        Log.e("No of Columns:", String.valueOf(c.getColumnCount()));

        // The name of the columns in the cursor.
        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.i("Column " + i, c.getColumnName(i));
        }


        // The number of results in the cursor
        Log.e("Result count:", String.valueOf(c.getCount()));

        // Each row of results in the cursor.
        int idColIndex = c.getColumnIndex("_id");
        int chatColIndex = c.getColumnIndex("CHAT");
        int chatTypeColIndex = c.getColumnIndex("CHAT_TYPE");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Long id = c.getLong(idColIndex);
            String chat = c.getString(chatColIndex);
            String chatType = c.getString(chatTypeColIndex);

            Log.e("ID: ", String.valueOf(id));
            Log.e("Message: ", chat);
            Log.e("isSent: ", chatType);

            c.moveToNext();
        }

    }
}








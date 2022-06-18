package com.cst2335.n041046587;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    Button buttonSend;
    Button buttonReceive;
    ListView lv;
    EditText typeHere;
    List<MessageStorage> messageStorageList2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        lv = (ListView)findViewById(R.id.listView);
        typeHere = (EditText)findViewById(R.id.typeHere);
        buttonSend = (Button)findViewById(R.id.buttonSend);
        buttonReceive = (Button)findViewById(R.id.buttonReceive);

        buttonSend.setOnClickListener(v -> {
            String message = typeHere.getText().toString();

            MessageStorage model = new MessageStorage(message, true);
            messageStorageList2.add(model);
            typeHere.setText("");
            ChatAdapter adt = new ChatAdapter(messageStorageList2, getApplicationContext());
            lv.setAdapter(adt);

        });

        buttonReceive.setOnClickListener(c -> {
            String message = typeHere.getText().toString();
            MessageStorage storage = new MessageStorage(message, false);
            messageStorageList2.add(storage);
            typeHere.setText("");
            ChatAdapter adt = new ChatAdapter(messageStorageList2, getApplicationContext());
            lv.setAdapter(adt);
        });


        Log.e("ChatRoomActivity","onCreate");


    }
}

class MessageStorage {
    public String message;
    public boolean isSend;

    public MessageStorage(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public MessageStorage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}

class ChatAdapter extends BaseAdapter {
    private List<MessageStorage> messageStorageList;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<MessageStorage> messageStorages, Context context) {
        messageStorageList = messageStorages;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return messageStorageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageStorageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            if (messageStorageList.get(position).isSend()){
                view = inflater.inflate(R.layout.activity_main_send, null);

            }else {
                view = inflater.inflate(R.layout.activity_main_receive, null);
            }
            TextView messageText = (TextView)view.findViewById(R.id.messageText);
            messageText.setText(messageStorageList.get(position).message);
        }
        return view;
    }


}

package com.cst2335.n041046587;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class ChatAdapter extends BaseAdapter {
    private List<MessageStorage> messageModelList;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<MessageStorage> messageModels, Context context) {
        messageModelList = messageModels;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return messageModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if (messageModelList.get(position).isSend()){
            view = inflater.inflate(R.layout.activity_main_send, null);

        }else {
            view = inflater.inflate(R.layout.activity_main_receive, null);
        }
        TextView  messageText = (TextView)view.findViewById(R.id.messageText);
        messageText.setText(messageModelList.get(position).message);

        return view;
    }
}

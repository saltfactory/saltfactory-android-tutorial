package net.saltfactory.tutorial.fcmdemo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;

/**
 * Created by saltfactory on 12/11/2016.
 */


public class MessageRowAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Message> mMessages;


    public MessageRowAdapter(Context context, ArrayList<Message> messages) {
        super();
        this.mContext = context;
        this.mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message) this.getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sms_row, parent, false);
            holder.message = (TextView) convertView.findViewById(R.id.message_text);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.message.setText(message.getMessage());

        LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
        //check if it is a status message then remove background, and change text color.
        if (message.isStatusMessage()) {
            holder.message.setBackgroundResource(0);
            lp.gravity = Gravity.LEFT;
            holder.message.setTextColor(ContextCompat.getColor(mContext, R.color.textFieldColor));
        } else {
            //Check whether message is mine to show green background and align to right
            if (message.isMine()) {
                holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
                lp.gravity = Gravity.RIGHT;
            }
            //If not mine then it is from sender to show orange background and align to left
            else {
                holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
                lp.gravity = Gravity.LEFT;
            }
            holder.message.setLayoutParams(lp);
            holder.message.setTextColor(ContextCompat.getColor(mContext, R.color.textColor));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView message;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}


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


/**
 * 채팅메소드를 채팅방에 스크롤 가능하고 말풑선 형태로 보여주기 위한 클래스이다
 * 채팅에 사용한 총 Message 배열을 조작하여 isMine 정보로 내 말풍선, 상대 말풍선을 다르게 표헌한다
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
        if (message.isStatusMessage()) {
            holder.message.setBackgroundResource(0);
            lp.gravity = Gravity.LEFT;
            holder.message.setTextColor(ContextCompat.getColor(mContext, R.color.textFieldColor));
        } else {

            if (message.isMine()) {
                holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
                lp.gravity = Gravity.RIGHT;
            }

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


package ddwucom.mobile.ddwubox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginAdapter extends BaseAdapter {

    static final String TAG = "LoginrAdapter";
    int count;
    private Context context;
    //context 받는 이유는 우리가 작업하는 게 activity 아니기 때문에 물려받아야 함
    private int layout;
    private ArrayList<Login> loginList; //원본데이터 여러개 담을
    private LayoutInflater inflater; //필요에 따라 적당한 위치에서 생성하면 됨

    public LoginAdapter(Context context, int layout, ArrayList<Login> loginList) {
        this.context = context;
        this.layout = layout;
        this.loginList = loginList;
        count=0;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return loginList.size(); //원본데이터가 ArrayList 에 담겨있으니까 이 데이터의 개수를 반환하면 됨
    }

    @Override
    public Object getItem(int position) {
        return loginList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loginList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder viewHolder;


        Log.d(TAG, "getView()!");

        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.userId = (TextView)convertView.findViewById(R.id.userId);
            viewHolder.review = (TextView) convertView.findViewById(R.id.userReview);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

//        viewHolder.button.setFocusable(false); //button 만 클릭할 수 있도록, 다른 곳 클릭 안 됨

//        viewHolder.no.setText(String.valueOf(movieList.get(position).get_id()));
        viewHolder.userId.setText(String.valueOf(loginList.get(position).getUserId()));
        viewHolder.review.setText(String.valueOf(loginList.get(position).getReview()));

        return convertView;
    }

    static class ViewHolder {
        TextView userId;
        TextView review;
    }
}
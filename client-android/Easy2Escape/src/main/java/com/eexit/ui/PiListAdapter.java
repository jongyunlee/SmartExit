package com.eexit.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.eexit.R;
import com.eexit.model.Pi;
import com.eexit.widget.ProgressImageView;
import com.eexit.ApiPaths;
import com.eexit.util.LogUtils;
import java.util.List;

public class PiListAdapter extends RecyclerView.Adapter {

    private List<Pi> pis;
    private Context mContext;
    private PiListListener mPiListListener;

    public void setPiListListener(PiListListener listener) {
	mPiListListener = listener;
    }

    public PiListAdapter(Context context) {
	mContext = context;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
	View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pi_list, viewGroup, false);
	return new ListItemViewHolder(itemView, mPiListListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
	Pi pi = pis.get(position);
	ListItemViewHolder holder = (ListItemViewHolder) viewHolder;
	holder.id.setText("ID : " + pi.getId());
	holder.message.setText("message : " + pi.getMessage());
	holder.temp.setText("temp : " + pi.getTemp());
	holder.humidity.setText("hum : " + pi.getHumidity());
	holder.image.displayImage(ApiPaths.SERVER + "/images/" + pi.getId() + ".jpg");
	holder.direction.setText("dir : " + pi.getDirection());
	holder.location.setText("loc : " + pi.getLocation());
	holder.state.setText("state : " + pi.getState());
	holder.destination.setText("dest : " + pi.getDestination());
    }

    @Override
    public int getItemCount() {
	if (pis == null) return 0;
	return pis.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
	ProgressImageView image;
	TextView id;

	TextView temp;
	TextView humidity;
	TextView direction;
	TextView location;
	TextView state;
	TextView destination;
	TextView message;

	public PiListListener mListener;

	public ListItemViewHolder(View itemView, PiListListener listener) {
	    super(itemView);
	    image = (ProgressImageView) itemView.findViewById(R.id.image);
	    id = (TextView) itemView.findViewById(R.id.txt_id);
	    temp = (TextView) itemView.findViewById(R.id.txt_temp);
	    humidity = (TextView) itemView.findViewById(R.id.txt_humidity);
	    direction = (TextView) itemView.findViewById(R.id.txt_direction);
	    location = (TextView) itemView.findViewById(R.id.txt_location);
	    state = (TextView) itemView.findViewById(R.id.txt_state);
	    destination = (TextView) itemView.findViewById(R.id.txt_destination);
	    message = (TextView) itemView.findViewById(R.id.txt_message);
	    itemView.setOnClickListener(this);
	    mListener = listener;
	}

	@Override
	public void onClick(View v) {
	    if (mListener != null) {
		mListener.onItemClick(pis.get(getLayoutPosition()));
	    }
	}
    }

    public static interface PiListListener {
	public void onItemClick(Pi pi);
    }

    public void swapData(List<Pi> pis) {
	this.pis = pis;
	notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
	return position;
    }
}

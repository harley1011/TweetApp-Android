package ca.qc.johnabbott.cs603.asg2;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class TweetDbListAdapter implements ListAdapter {
	private List<Tweet> items;
	private Context context;
	public TweetDbListAdapter(List<Tweet> list, Context context )
	{
		items = list;
		this.context = context;
	}
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE
				);
				View tweetView = inflater.inflate(R.layout.tweet, null);
				TextView textTweet = (TextView)tweetView.findViewById(R.id.textTweet);
				TextView textTweetUser = (TextView)tweetView.findViewById(R.id.textUser);
				TextView textTweetDate = (TextView)tweetView.findViewById(R.id.textDate);
				//id/textUser"
				textTweet.setText( items.get(position).getTweet());
				textTweetUser.setText(items.get(position).getUser());
				textTweetDate.setText(items.get(position).getCreated().toString());
				return tweetView;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return items.size() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}

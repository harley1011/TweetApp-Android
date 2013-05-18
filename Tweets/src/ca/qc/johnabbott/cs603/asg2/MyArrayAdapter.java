package ca.qc.johnabbott.cs603.asg2;


import java.util.List;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MyArrayAdapter implements ListAdapter {
	//private ObservableArrayList items;
	private TwitterSource list;
	private Context context;
	private DataSetObservable observerables;
	public MyArrayAdapter(Context context )
	{
		this.context = context;
		this.observerables = new DataSetObservable();
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Tweet getItem(int position) {
		if (position < list.size()) 
			return list.getTweet(list.getIds()[position]);
		else
			return null;
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
	public void setSource(TwitterSource list) {
		this.list = list;
		this.list.setObservers(observerables);
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
				textTweet.setText(getItem(position).getTweet());
				textTweetUser.setText(getItem(position).getUser());
				textTweetDate.setText(getItem(position).getCreated().toString());
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
		return list.size() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		this.observerables.registerObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		this.observerables.unregisterObserver(observer);

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

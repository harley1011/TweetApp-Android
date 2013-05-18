/*		Name: Harley McPhee
		Date: 3/15/2013
		Class: Object Oriented Programming
		Assignment: Assignment #2B
*/
package ca.qc.johnabbott.cs603.asg2;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.DataSetObservable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity {

	private MyArrayAdapter adapter;
	private TwitterSource twitter;
	private TweetDbSource savedDb;
	private Context context;
	@Override
	protected void onCreate(Bundle savedDbInstanceState) {
		context = this;
		super.onCreate(savedDbInstanceState);
		setContentView(R.layout.activity_main);
		savedDb = new TweetDbSource(this, "savedDb.db"); 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void switchToTwitter (String input)
	{
		final ListView view = (ListView) this.findViewById(R.id.listView);
		twitter = new TwitterSource (new TweetComparator());	
		twitter.setSearchString(input);
		adapter = new MyArrayAdapter(this);
		adapter.setSource(twitter);
		view.setAdapter(adapter);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
		 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			 Tweet tweet  = (Tweet) view.getItemAtPosition(position);
			    String message;
			     if ( savedDb.checkTweet(tweet) ) // Checks if the tweet already exists in saved db
			    	 message = "Tweet already saved";
			     else
			     {
			    	 savedDb.insert(tweet);
			    	 message = "Saved to database";
			     }
			     Toast toast = Toast.makeText(context,message, Toast.LENGTH_SHORT);
				 toast.show();
		 }
		});

	}
	private void switchToDbSource ()
	{
		final ListView view = (ListView) findViewById(R.id.listView);
		view.setAdapter(new TweetDbListAdapter(savedDb.getAllTweets(), this));
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				  Tweet tweet  = (Tweet) view.getItemAtPosition(position);
				  confirmDeleteTweet(tweet);
			  }
			});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_twitter:
			acquireSearchString("#java");
			return true;
		case R.id.menu_saved:
			switchToDbSource();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * Present the user with a simple dialog to enter search string
	 */
	private void acquireSearchString(final String currentSearch) {
		final EditText input = new EditText(this);
		input.setText(currentSearch);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Search Twitter")
			 .setMessage("Enter search")
			 .setView(input)
			 .setPositiveButton("Ok", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int button) {
					switchToTwitter(input.getText().toString());			
				}
			 })
			 .setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int button) {} // cancel
			 })
			 .show();
	}

	/*
	 * Present the user with a simple dialog to confirm a tweet deletion
	 */
	private void confirmDeleteTweet(final Tweet t) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Delete tweet?")
			 .setMessage(t.getTweet())
			 .setPositiveButton("Ok", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int button) {
					final ListView view = (ListView) findViewById(R.id.listView);
				    savedDb.deleteTweet(t);
					((ListView) view).setAdapter(new TweetDbListAdapter(savedDb.getAllTweets(), context));
				}
			 })
			 .setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int button) {} // cancel
			 })
			 .show();
	}
	
}
class TweetComparator implements Comparator<Tweet> {
	public int compare(Tweet lhs, Tweet rhs) {
		if (lhs.getTweetId() == rhs.getTweetId()) {
			return 0;
		} 
		else 	{
			return lhs.getCreated().compareTo(rhs.getCreated()) > 1 ? -1 : 1; // compares by the date
		}
	}
}

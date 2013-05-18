package ca.qc.johnabbott.cs603.asg2;

/* TweetDbSource
 *   - stores tweets in a sqlite3 database.
 *   - "add" and "retrieve" operations
 
 */

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TweetDbSource extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String TABLE_NAME = "tweet";
	
	private static final String TWEET_ID = "tweet_id";
	private static final String USER_ID = "user_id";
	private static final String USER = "user";
	private static final String NAME = "name";
	private static final String TWEET = "tweet";
	private static final String CREATED = "date_created";
	private static final String RETWEET_COUNT = "retweet_count";

	public TweetDbSource(Context context, String dbName) {	
		super(context, dbName, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TWEETS_TABLE = 
				"CREATE TABLE " + TABLE_NAME + "(" 
				+ TWEET_ID + " LONG PRIMARY KEY," 
				+ USER_ID + " LONG,"
				+ USER + " TEXT," 
				+ NAME + " TEXT," 
				+ TWEET + " TEXT," 
				+ CREATED + " TEXT," 
				+ RETWEET_COUNT + " INTEGER " + ")";
		db.execSQL(CREATE_TWEETS_TABLE);
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);	
    }
	
	public void insert (Tweet tweet)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues(); 
		values.put(TWEET_ID, tweet.getTweetId());
		values.put(USER_ID, tweet.getUserId());
		values.put(USER, tweet.getUser());
		values.put(NAME, tweet.getName());
		values.put(TWEET, tweet.getTweet());
		values.put(CREATED, tweet.getCreatedAsString());
		values.put(RETWEET_COUNT, tweet.getRetweetCount());
		db.insert(TABLE_NAME, null, values);
		
		db.close();
			
	}
	public boolean checkTweet(Tweet tweet) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    String countQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TWEET_ID + " = " + tweet.getTweetId(); // Check if the tweet exists in the database
	    Cursor cursor = db.rawQuery(countQuery, null);
	    int cursorSize = cursor.getCount();
	    if ( cursorSize == 0)
	    	return false;
	    else
	    	return true;  
	}
	 public List<Tweet> getAllTweets() {
		    List<Tweet> tweetList = new ArrayList<Tweet>();
		    // Select All Query
		    String selectQuery = "SELECT * FROM " + TABLE_NAME;
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		    	do {
					Tweet tweet = null;
					try {
						tweet = new Tweet( // create a new tweet to copy the current one the cursor is on
								Long.parseLong(cursor.getString(0)),
								Long.parseLong(cursor.getString(1)),
								cursor.getString(2), 
								cursor.getString(3),
								cursor.getString(4),
								cursor.getString(5));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}

					tweetList.add(tweet); // add the tweet to our list that we will send back
				} while (cursor.moveToNext());
			}
		 
		    // return contact list
		    return tweetList;
		}
		public int size() {
			String countQuery = "SELECT  * FROM " + TABLE_NAME;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			//cursor.close();
			int cursorSize = cursor.getCount();
			return cursorSize;
		}
		public void deleteTweet(Tweet tweet) {
			SQLiteDatabase db = this.getWritableDatabase(); // deletes the tweet sent
		    db.delete(TABLE_NAME, TWEET_ID + " = ?", 
		            new String[] { String.valueOf(tweet.getTweetId()) });
		    db.close();

	
		}

	/* TODO: insert, delete and select commands */

}

package com.example.assignment1;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;


public class DatabaseActivity  extends ListActivity  {
	 private CommentsDataSource datasource;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_data_base);

	    datasource = new CommentsDataSource(this);
	    datasource.open();

	    List<Comment> values = datasource.getAllComments();

	    // Use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	  }

	  // Will be called via the onClick attribute
	  // of the buttons in main.xml only
	  public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
	    Comment comment = null;
	    //switch - which button
	    switch (view.getId()) {
	    //add new comment button
	    case R.id.add:
	    	//new possible comments can be added here in this list.
	    	//because comments.lenght is used in the random function this is the only change needed
	      String[] comments = new String[] { "Go for it", "Run away", "Stay perfectly still", "It doesn't matter", "Rush into it!" };
	      int nextInt = new Random().nextInt(comments.length);
	      // Save the new comment to the database
	      comment = datasource.createComment(comments[nextInt]);
	      //add it to the visible list
	      adapter.add(comment);
	      break;
	    //remove one comment button
	    case R.id.delete:
	      if (getListAdapter().getCount() > 0) {
	        comment = (Comment) getListAdapter().getItem(0);
	        //remove from database
	        datasource.deleteComment(comment);
	        //remove from list
	        adapter.remove(comment);
	      }
	      break;
	    }
	    //update the visible list
	    adapter.notifyDataSetChanged();
	  }

	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }
 
	public void returnToMain(View view) {
	    // Return button to MainActivity
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}

package com.example.assignment1;

import java.io.InputStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

	public class WebActivity extends Activity {
	  private TextView textView;
	  private TextView textViewTwo;
	  String oldNumber = null;
	  String newNumber = null;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_web);
	    
	   
	    
	    textView = (TextView) findViewById(R.id.newnumberview);
	    textViewTwo = (TextView) findViewById(R.id.oldnumberview);
	    try{
	    	SharedPreferences preferences = getPreferences(MODE_PRIVATE);
	    	preferences.getString("oldNum", oldNumber);
	    	preferences.getString("newNum", newNumber);
	    } catch (NullPointerException ex) { 
	        System.out.println("no prefs found"); 
	    }
	    if(oldNumber != null){
	    	textViewTwo.setText(oldNumber);
	    }
	    if(newNumber != null){
	    	textView.setText(newNumber);
	    }
	    
	    
	    final Button button = (Button) findViewById(R.id.returnWebButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	returnToMain();
            }
        });
        final Button button2 = (Button) findViewById(R.id.getWebButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	getWebContent();
            }
        });
        
	    
	    
	    
	    
	  }

	  private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		  @Override
		  protected void onPreExecute() {
		   
		   ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
		   progress.setVisibility(View.VISIBLE);
		  }
		  
		  @Override
	    protected String doInBackground(String... urls) {
	      String response = "";
	      for (String url : urls) {
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	      return response;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	      ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
	      progress.setVisibility(ProgressBar.GONE);
	      oldNumber = newNumber;
	      newNumber = result;
	      textView.setText(result);
	      textViewTwo.setText(oldNumber);
	
	    }
	  }

	  public void getWebContent() {
	    DownloadWebPageTask task = new DownloadWebPageTask();
	    task.execute(new String[] { "http://www.random.org/integers/?num=1&min=1&max=6&col=1&base=10&format=plain&rnd=new" });

	  }
	
	    
	    
	    public void returnToMain() {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	    
	  
	    
	    
	    
	    
	    @Override
	    protected void onPause() 
	    {
	      super.onPause();

	      // Store values between instances here
	      SharedPreferences preferences = getPreferences(MODE_PRIVATE);
	      SharedPreferences.Editor editor = preferences.edit();  
	      editor.putString("newNum",newNumber); // value to store
	      editor.putString("oldNum",oldNumber); // value to store 
	      // Commit to storage
	      editor.commit();
	    }
	    
	    
	    
	    @Override
	    protected void onResume() 
	    {
	    	super.onResume();
	    	textView = (TextView) findViewById(R.id.newnumberview);
	    	textViewTwo = (TextView) findViewById(R.id.oldnumberview);
	    	try{
	    		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
	    		 oldNumber = preferences.getString("oldNum", null);
	    		 newNumber = preferences.getString("newNum", null);
	    	} catch (NullPointerException ex) { 
	    		System.out.println("no prefs found"); 
	    	}
	    	if(oldNumber != null){
	    		textViewTwo.setText(oldNumber);
	    	}
	    	if(newNumber != null){
	    		textView.setText(newNumber);
	    	}
	    }
	    
	    
	    
	    
}




package com.codepath.apps.cravengr;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GoogleImageSearchActivity extends Activity {
	private static final int REQUEST_CODE = 0;
	
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	String imageSize;
	String colorFilter;
	String imageType;
	String siteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_image_search);
		//Toast.makeText(this, getIntent().getStringExtra("craveQuery"), Toast.LENGTH_SHORT).show();
		
		onImageSearch(getIntent().getStringExtra("craveQuery"));
		
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), YelpSearchActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
				//Toast.makeText(this, "Image clicked", Toast.LENGTH_SHORT).show();
			}
		});
	
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_image_search, menu);
		return true;
		
	}
	
	public void onSetting(MenuItem m){
		Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
	
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i){
		//Toast.makeText(this, "back" , Toast.LENGTH_SHORT).show();
		  if (resultCode == 0){
			  if(requestCode == 0) {
				 imageSize = i.getStringExtra("imagesize");
				 colorFilter = i.getStringExtra("colorfilter");
				 imageType = i.getStringExtra("imagetype");
				 siteFilter = i.getStringExtra("sitefilter");
				  
			     Toast.makeText(this, imageSize + colorFilter + imageType + siteFilter , Toast.LENGTH_SHORT).show();
			  }
		  }
	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
	}
	
	public void onImageSearchClickHandler(View v){
		String query = etQuery.getText().toString();
		onImageSearch(query);
	}
	
	public void onImageSearch(String query){
		
		//Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		
		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + 0
				+ "&v=1.0&q=" + Uri.encode(query) + "&as_sitesearch=" + "yelp.com";
		//Toast.makeText(this, "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + 0
		//		+ "&v=1.0&q=" + Uri.encode(query), Toast.LENGTH_SHORT).show();
		client.get(searchUrl, 
			 new JsonHttpResponseHandler() {
			@Override
				public void onSuccess(JSONObject response) {
					JSONArray imageJsonResults = null;
					try{
						imageJsonResults = response.getJSONObject(
								"responseData").getJSONArray("results");
						imageResults.clear();
						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
						Log.d("DEBUG", imageResults.toString());
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
		});
	}
}

package com.codepath.apps.cravengr.flickr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import com.codepath.apps.cravengr.R;
import com.codepath.apps.cravengr.YelpSearchActivity;
import com.codepath.apps.cravengr.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {
	
	FlickrClient client;
	ArrayList<FlickrPhoto> photoItems = new ArrayList<FlickrPhoto>();
	GridView gvPhotos;
	PhotoArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		client = FlickrClientApp.getRestClient();
		//photoItems = new ArrayList<FlickrPhoto>();
		gvPhotos = (GridView) findViewById(R.id.gvPhotos);
		adapter = new PhotoArrayAdapter(this, photoItems);
		gvPhotos.setAdapter(adapter);
		gvPhotos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Log.d("DEBUG", "Item clicked");
//				Intent i = new Intent(getApplicationContext(), YelpSearchActivity.class);
//				FlickrPhoto photoResult = photoItems.get(position);
//				i.putExtra("result", photoResult);
//				startActivity(i);
				//Toast.makeText(this, "Image clicked", Toast.LENGTH_SHORT).show();
			}
		});
		loadPhotos();
	
	}
	
//	public void toYelp(View v){
//		Intent i = new Intent(getApplicationContext(), YelpSearchActivity.class);
//		//FlickrPhoto photoResult = photoItems.get(position);
//		i.putExtra("result", "empty");
//		startActivity(i);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos, menu);
		return true;
	}
	
	public void loadPhotos() {
		client.getInterestingnessList(new JsonHttpResponseHandler() { 
    		public void onSuccess(JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                // Add new photos to SQLite
                try {
					JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
					for (int x = 0; x < photos.length(); x++) {
						String uid  = photos.getJSONObject(x).getString("id");
						FlickrPhoto p = FlickrPhoto.byPhotoUid(uid);
						if (p == null) { p = new FlickrPhoto(photos.getJSONObject(x)); };
						p.save();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.e("debug", e.toString());
				}
                
				// Load into GridView from DB
				for (FlickrPhoto p : FlickrPhoto.recentItems()) {
					adapter.add(p);
				}
				Log.d("DEBUG", "Total: " + photoItems.size());
            }
    	});
	}

}

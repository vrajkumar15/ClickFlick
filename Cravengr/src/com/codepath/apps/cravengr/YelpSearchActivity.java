package com.codepath.apps.cravengr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.cravengr.models.Business;
import com.codepath.apps.cravengr.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

public class YelpSearchActivity extends Activity {

	TextView tvResultHeader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String contentKeywords = new String(); // The description of the image (example: "Spicy Thai Fried Rice | Yelp")
		String refinedKeywords = new String(); // Cleaned up keyword string for the main Yelp search 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yelpsearch);
		
		
		if(getIntent().getStringExtra("craveQuery") != null){
			// I'm feeling Hungry case
			contentKeywords = getIntent().getStringExtra("craveQuery");
			if(contentKeywords.isEmpty()){
				contentKeywords="Biryani"; //Default case
			}
		} else { // call from Yelp Activity
			FlickrPhoto result = (FlickrPhoto) getIntent().getSerializableExtra("result");	
			contentKeywords = result.getName();
		}
		Log.d("DEBUG", contentKeywords);
		
		refinedKeywords = getRefinedKeywordQuery(contentKeywords);
		Log.d("DEBUG", refinedKeywords);
		// TODO: if(looks suspectful junk string) use original search keyword vs image description;
		Toast.makeText(this, contentKeywords, Toast.LENGTH_SHORT).show();
		
		// Rendering the activity screen
		
		tvResultHeader = (TextView) findViewById(R.id.tvYelpResultHeader);
		tvResultHeader.setText("Results for: " + "\""+ refinedKeywords + " \"");
		
		YelpClient client = YelpClientApp.getRestClient();
		client.search(refinedKeywords, "Sunnyvale", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int code, JSONObject body) {
				try {
					JSONArray businessesJson = body.getJSONArray("businesses");
					ArrayList<Business> businesses = Business.fromJson(businessesJson);
					ListView lvResults = (ListView)findViewById(R.id.lvResults);
					BusinessAdapter adapter = new BusinessAdapter(getBaseContext(), businesses);
					lvResults.setAdapter(adapter);
					Log.d("DEBUG", businesses.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(YelpSearchActivity.this, "FAIL", Toast.LENGTH_LONG).show();
			}
		});
	}

	private String getRefinedKeywordQuery(String contentKeywords) {
		/* Algorithm for keyword cleanup:
		 * 
		 * (Order matters as-is, otherwise you end up with junk words)
		 * Chop the "| Yelp" string
		 * Chop html encodings like "&amp;", "&#39;", "&quot;"
		 * Chop all special characters and have only alphabets [a-z,A-Z], but preserve space between words
		 * 
		 */
		
		contentKeywords = contentKeywords.replace("| Yelp", "");
		contentKeywords = contentKeywords.replace("&amp;", "");
		contentKeywords = contentKeywords.replace("&#39;", "");
		contentKeywords = contentKeywords.replace("&quot;", "");
		contentKeywords = contentKeywords.replaceAll("[^a-zA-Z\\s]", "");
		return contentKeywords;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}

package com.codepath.apps.cravengr.flickr;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FlickrClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;
    public static final String REST_URL = "http://www.flickr.com/services";
    public static final String REST_CONSUMER_KEY = "3d1ec89d6f5faa703532c167b77834c0";
    public static final String REST_CONSUMER_SECRET = "7b5e256be7fd506d";
    public static final String REST_CALLBACK_URL = "oauth://cravengrFlickr";
    
    public FlickrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        setBaseUrl("http://api.flickr.com/services/rest");
    }

    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
 //       String apiUrl = getApiUrl("?&format=json&nojsoncallback=1&method=flickr.interestingness.getList");
    //http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=59705bb8b21e0601e5a03bf1aea83105&tags=biryani&format=rest&auth_token=72157637493870426-04173fe571423fa4&api_sig=4e3ceacb14a4c89abba4b458e4bc6415
    //http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=6da5238e9803fc42c2eb8c3b424b2bae&tags=biryani&format=rest
    	 String apiUrl = getApiUrl("?&format=json&nojsoncallback=1&method=flickr.photos.search");
    	//String apiUrl = getApiUrl("/rest/?&method=flickr.photos.getRecent");
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        RequestParams params = new RequestParams();
        params.put("tags", "biryani");
        params.put("content_type", "1");
        params.put("text", "biryani");
        client.get(apiUrl, params, handler);
    }
}
package com.codepath.apps.cravengr.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Business extends BaseModel {
    private Location location;

	private String id;
	private String name;
	private String phone;
	private String imageUrl;
	private String ratingUrl;
	//rating_img_url
	//private String address[];
	//location.display_address

	
    public Location getLocation() {
        return location;
    }
	
	public String getName() {
		return this.name;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public String getRatingUrl() {
		return this.ratingUrl;
	}
	
	//public String[] getAddress() {
	//	return this.address;
	//}
	
	// Decodes business json into business model object
	public static Business fromJson(JSONObject jsonObject) {
		Business b = new Business();
        // Deserialize json into object fields
		try {
            b.jsonObject = jsonObject;
            b.location = Location.fromJson(jsonObject.getJSONObject("location"));
			b.id = jsonObject.getString("id");
        	b.name = jsonObject.getString("name");
        	b.phone = jsonObject.getString("display_phone");
        	b.imageUrl = jsonObject.getString("image_url");
        	b.ratingUrl = jsonObject.getString("rating_img_url");
        	//b.address = jsonObject.getString("location.display_address");
        	
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
		// Return new object
		return b;
	}
	
	// Decodes array of business json results into business model objects
    public static ArrayList<Business> fromJson(JSONArray jsonArray) {
        ArrayList<Business> businesses = new ArrayList<Business>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject businessJson = null;
            try {
            	businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Business business = Business.fromJson(businessJson);
            if (business != null) {
            	businesses.add(business);
            }
        }

        return businesses;
    }
    
    @Override
    public String toString() {
    	return id + " " + name + " " + phone + " " + imageUrl + " " + ratingUrl;
    }
}

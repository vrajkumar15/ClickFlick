package com.codepath.apps.cravengr.models;



import org.json.JSONObject;



public class Location extends BaseModel{

	public String getAddress() {
        return getString("city");
    }



    public static Location fromJson(JSONObject json) {
        Location l = new Location();

        try {
            l.jsonObject = json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return l;
    }


}
package com.codepath.apps.cravengr;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.codepath.apps.cravengr.models.Business;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BusinessAdapter extends ArrayAdapter<Business> {

	public BusinessAdapter(Context context, List<Business> businesses) {
		super(context, 0, businesses);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.business_item, null);
		}
		
		Business business = getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivBusiness);
		ImageLoader.getInstance().displayImage(business.getImageUrl(), imageView);
		
//		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color= '#777777>@ " +
//				tweet.getUser().getScreenName() + "</font></small>";
		
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formatted_name = "<b><small>" + business.getName() + "</small></b>";
		nameView.setText(Html.fromHtml(formatted_name));
		
		TextView phone = (TextView) view.findViewById(R.id.tvPhone);
		String formatted_phone = "<small>" + business.getPhone() + "</small>";
		phone.setText(Html.fromHtml(formatted_phone));
		
		TextView address = (TextView) view.findViewById(R.id.tvAdd);
		String formatted_address = "<small>" + business.getLocation().getAddress() + "</small>";
		address.setText(Html.fromHtml(formatted_address));
		
		ImageView ratingView = (ImageView) view.findViewById(R.id.ivRating);
		ImageLoader.getInstance().displayImage(business.getRatingUrl(), ratingView);

		
		return view;
		
	}

}

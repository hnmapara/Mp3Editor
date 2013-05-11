package com.mapara.mp3editor;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContentAdapter extends ArrayAdapter<String>{
	private String[] objects;
	private File[] listOfFiles;
	public ContentAdapter(Context context, 
			int textViewResourceId, String[] objects, File[] files) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
		this.listOfFiles = files;
	}

	@Override
	public int getCount() {
		return objects.length;
	}

	@Override
	public String getItem(int position) {
		return this.objects[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, parent, false);
			holder.title = (TextView) convertView.findViewById(R.id.titletext);
			holder.albumname = (TextView) convertView.findViewById(R.id.albuminfo);
			holder.filename = (TextView) convertView.findViewById(R.id.filename);			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		File f = listOfFiles[position];
		if(f.exists() && f.isDirectory()) {
			holder.title.setVisibility(View.INVISIBLE);
			holder.albumname.setVisibility(View.INVISIBLE);
		}
		holder.filename.setText(getItem(position));
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}
	
	static class ViewHolder {
		TextView title;
		TextView filename;
		TextView albumname;
	}

}

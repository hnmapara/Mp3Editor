package com.mapara.mp3editor;

import java.io.File;
import java.io.IOException;

import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.NoMP3FrameException;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, parent, false);
			holder.title = (TextView) convertView.findViewById(R.id.titletext);
			holder.albumname = (EditText) convertView.findViewById(R.id.editalbuminfo);
			holder.filename = (TextView) convertView.findViewById(R.id.filename);
			holder.saveButton = (Button) convertView.findViewById(R.id.save);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		File f = listOfFiles[position];
		if(f.exists() && f.isDirectory()) {
			holder.title.setVisibility(View.INVISIBLE);
			holder.albumname.setVisibility(View.INVISIBLE);
			holder.saveButton.setVisibility(View.GONE);
		}
		if(f.exists() && !f.isDirectory()) {
			
				Mp3Info mp3Info;

					mp3Info = Mp3Utility.getMP3FileInfo2(f.getAbsolutePath());
					Log.d(Mp3SearchActivity.TAG, "AlbumName : " + mp3Info.albumName);
					Log.d(Mp3SearchActivity.TAG, "ArtistName : "+ mp3Info.artistName);
					holder.albumname.setText(mp3Info.albumName);
					holder.title.setText(mp3Info.artistName);
                    holder.saveButton.setTag(f.getAbsolutePath());	
				
			
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
		EditText albumname;
		Button saveButton;
	}

}

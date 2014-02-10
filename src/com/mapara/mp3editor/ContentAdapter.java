package com.mapara.mp3editor;

import java.io.File;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, parent, false);
			holder.title = (TextView) convertView.findViewById(R.id.titletext);
            holder.rowIcon = (ImageView) convertView.findViewById(R.id.row_img);
			holder.albumname = (EditText) convertView.findViewById(R.id.editalbuminfo);
			holder.filename = (TextView) convertView.findViewById(R.id.filename);
			holder.saveButton = (Button) convertView.findViewById(R.id.save);
            holder.entryInfo = (TextView) convertView.findViewById(R.id.entryinfo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		File f = listOfFiles[position];
        holder.filePath = f.getAbsolutePath();
		if(f.exists() && f.isDirectory()) {
            convertView.findViewById(R.id.albumLable).setVisibility(View.GONE);
            convertView.findViewById(R.id.titleLable).setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.INVISIBLE);
			holder.albumname.setVisibility(View.INVISIBLE);
			holder.saveButton.setVisibility(View.GONE);
            holder.filename.setText(getItem(position));
            holder.rowIcon.setImageResource(R.drawable.folder_img2);
            holder.rowIcon.setPadding(0, 0, 0, 0);
//            convertView.setBackgroundColor(Color.parseColor("#FFF9F6"));
//            convertView.getBackground().setColorFilter(Color.parseColor("#D1D1E0"), PorterDuff.Mode.DARKEN);
            convertView.setBackgroundResource(R.drawable.list_folder_drawable);
            String[] list = f.list(Mp3Utility.getFileNameFilter(true));
            String info = list == null ? "No Access" : list.length+" entries";
            holder.entryInfo.setText(info);

        }
		else if(f.exists() && !f.isDirectory()) {
				Mp3Info mp3Info;
                convertView.findViewById(R.id.albumLable).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.titleLable).setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.VISIBLE);
                holder.albumname.setVisibility(View.VISIBLE);
                holder.saveButton.setVisibility(View.VISIBLE);
                    try {
					mp3Info = Mp3Utility.getMP3FileInfo2(f.getAbsolutePath());
					Log.d(Mp3SearchActivity.TAG, "AlbumName : " + mp3Info.albumName);
					Log.d(Mp3SearchActivity.TAG, "SongTitle : "+ mp3Info.SongTitle);
					if(mp3Info.albumName!=null)
                        holder.albumname.setText(mp3Info.albumName);
                    if(mp3Info.SongTitle!=null)
                        holder.title.setText(mp3Info.SongTitle);
                    holder.rowIcon.setImageResource(R.drawable.music);
                    int p = getContext().getResources()
                                .getDimensionPixelSize(R.dimen.music_icon_padding);
                     holder.rowIcon.setPadding(p,p,p,p);
                     holder.filename.setText("File name: " + getItem(position));
                     holder.entryInfo.setText(Mp3Utility.formattedFileSize(f.length()));
                    } catch (Exception e){}
		}

		return convertView;
    }

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}
	
	class ViewHolder {
		TextView title;
		TextView filename;
		EditText albumname;
        ImageView rowIcon;
		Button saveButton;
        TextView entryInfo;
        String filePath;
	}

}

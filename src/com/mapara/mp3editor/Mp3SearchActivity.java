package com.mapara.mp3editor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import de.vdheide.mp3.ID3Exception;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2Exception;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.NoMP3FrameException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class Mp3SearchActivity extends Activity {
	private String[] mFilenameList;
	private File[] mFileListfiles;
	private File mPath = new File(Environment.getExternalStorageDirectory() + "/Music");
			
	private String mChosenFile;
	private static final String FTYPE = ".mp3";
	private static final int DIALOG_LOAD_FILE = 1000;
	private static final String TAG = Mp3SearchActivity.class.getSimpleName();

	private void loadFileList() {
		try {
			mPath.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card " + e.toString());
		}
		if (mPath.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					File sel = new File(dir, filename);
					return filename.contains(FTYPE) || sel.isDirectory();
				}
			};
			mFilenameList = mPath.list(null);
			mFileListfiles = mPath.listFiles();
		} else {
			mFilenameList = new String[0];
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showDialog(DIALOG_LOAD_FILE);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new Builder(this);
		
			switch (id) {
			case DIALOG_LOAD_FILE:
				builder.setTitle("Choose your file");
				if (mFilenameList == null) {
					Log.e(TAG, "Showing file picker before loading the file list");
					dialog = builder.create();
					loadFileList();
				}
				builder.setItems(mFilenameList, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						mChosenFile = mFilenameList[which];
						Log.d(TAG, "chosen file name: "+mChosenFile);
						Log.d(TAG, "chosen file path: "+mFileListfiles[which].getAbsolutePath());
						// you can do stuff with the file here too
						try {
							Mp3Info mp3Info = Mp3Utility.getMP3FileInfo(mPath+File.separator+mChosenFile);
							Log.d(TAG, "AlbumName : "+ mp3Info.albumName);
							Log.d(TAG, "ArtistName : "+ mp3Info.artistName);
							
							mp3Info.albumName = "Mumbai";
							Log.d(TAG, "Path of Mp3 :"+ mPath+File.separator+mChosenFile);
//							String newName = Mp3Utility.setMP3FileInfo(mPath+File.separator+mChosenFile, mp3Info);
							String newName = Mp3Utility.setMP3FileInfo(mFileListfiles[which].getAbsolutePath(), mp3Info);
							Log.d(TAG, "New Name : "+ newName);
						} catch (ID3v2WrongCRCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ID3v2DecompressionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ID3v2IllegalVersionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoMP3FrameException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ID3Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ID3v2Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				break;
			}//switch loop end
		
		dialog = builder.show();
		return dialog;
	}
}
package com.mapara.mp3editor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import de.vdheide.mp3.ID3Exception;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2Exception;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.NoMP3FrameException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Mp3SearchActivity extends Activity {
	public static String[] mFilenameList;
    public static File[] mFileListfiles;
//    public static File defaultRootFile = Environment.getExternalStorageDirectory();
    public static File defaultRootFile = new File("/mnt");
    public static File mPath = defaultRootFile;

	private String mChosenFile;
	private static final int DIALOG_LOAD_FILE = 1000;
	public static final String TAG = Mp3SearchActivity.class.getSimpleName();

	protected void loadFileList() {

        try {
			mPath.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card " + e.toString());
		}
		if (mPath.exists()) {
			mFilenameList = mPath.list(Mp3Utility.getFileNameFilter(true));
			mFileListfiles = mPath.listFiles(Mp3Utility.getFileNameFilter(true));
		} else {
			mFilenameList = new String[0];
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		showDialog(DIALOG_LOAD_FILE);
	
	/**** 
	 * The other way is this, query MediaProvider for audio files and load them into cursorloader and listen for changes
	 * 	
		mFilenameList = new String[0];
		Cursor cursor = null;
		try {
		cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {MediaStore.Audio.Media.DATA},
									null, null, null);
			if(cursor!=null && cursor.getCount() >0) {
				mFilenameList = new String[cursor.getCount()];
				int index = 0;
				while(cursor.moveToNext()) {
					mFilenameList[index++] = cursor.getString(0);
				}
			}
		} finally {
			if(cursor!=null) cursor.close();
		}
	*/	
		showDetails();
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG,"Mp3SearchActivity - onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG,"Mp3SearchActivity - onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(TAG,"Mp3SearchActivity - onStart");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(TAG,"Mp3SearchActivity - onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG,"Mp3SearchActivity - On Destroy");
		mPath = defaultRootFile;
		super.onDestroy();
	}

	void showDetails() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Log.d(TAG, "showDetails()... Creating ArrayListFragment");
		
		Fragment prev = getFragmentManager().findFragmentById(android.R.id.content);
        if (prev != null) {        	
            //ft.remove(prev);
        }

        FileListFragment list = new FileListFragment();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.add(android.R.id.content, list).addToBackStack(null).commit();
		
	}

    public void albumInfoClick(View v) {
	    RelativeLayout parentRow = (RelativeLayout) v.getParent();
        EditText child = (EditText) parentRow.findViewById(R.id.editalbuminfo);
        String filePath = ((ContentAdapter.ViewHolder)parentRow.getTag()).filePath;
        Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();

        Mp3Utility.setMP3FileInfo2(filePath, new Mp3Info(child.getText().toString(), ""));
        Mp3Utility.scanSDCardFile(getApplicationContext(), new String[] {filePath});

    }
/*
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
							Log.d(TAG, "ArtistName : "+ mp3Info.SongTitle);
							
							mp3Info.albumName = "Mumbai";
							Log.d(TAG, "Path of Mp3 :"+ mPath+File.separator+mChosenFile);
							String newName = Mp3Utility.setMP3FileInfo(mPath+File.separator+mChosenFile, mp3Info);
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
*/

    //This method is Needed because - when pressing back button, Android pops up the fragment
    //from the back stack and when the last fragment is poped out, there remains only a blank
    //activity. So when there is a back key pressed and the backstack count is 1, just finish
    //the activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_BACK)
            {
                if (getFragmentManager().getBackStackEntryCount() == 1)
                    this.finish();
                else
                    getFragmentManager().popBackStack();
                    //removeCurrentFragment();
                return false;
            }

            return super.onKeyDown(keyCode, event);
    }

}
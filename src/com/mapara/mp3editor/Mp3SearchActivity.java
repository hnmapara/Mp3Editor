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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Mp3SearchActivity extends Activity {
	private static String[] mFilenameList;
	private static File[] mFileListfiles;
	private static File mPath = Environment.getExternalStorageDirectory();// + "/Music");
			
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
					return (!sel.isHidden()) && (filename.contains(FTYPE) || filename.contains(".MP3") || sel.isDirectory());
				}
			};
			mFilenameList = mPath.list(filter);
			mFileListfiles = mPath.listFiles(filter);
		} else {
			mFilenameList = new String[0];
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		mPath = new File(Environment.getExternalStorageDirectory() + "/Music");
		super.onDestroy();
	}

	void showDetails() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Log.d(TAG, "showDetails()... Creating ArrayListFragment");
		
		Fragment prev = getFragmentManager().findFragmentById(android.R.id.content);
        if (prev != null) {        	
            //ft.remove(prev);
        }
        ft.addToBackStack(null);
        ArrayListFragment list = new ArrayListFragment();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.add(android.R.id.content, list,"myfrag").commit();
		
	}
	
	public static class ArrayListFragment extends ListFragment {
		private String[] filenames;
		private File[] files;
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.i(TAG,"ArrayListFragment - In onActivityCreated");
            
        }


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.i(TAG,"ArrayListFragment - In onCreateView..");
			((Mp3SearchActivity)getActivity()).loadFileList();
			filenames = mFilenameList;
			files = mFileListfiles;
			if(filenames==null) {
				View view = inflater.inflate(R.layout.fragment_nodata, container);
				view.setBackgroundColor(0xffffffff);
				return view;
			}
			setListAdapter(new ContentAdapter(getActivity(),R.layout.list_item,filenames,files));
//			setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, filenames));
			View view = inflater.inflate(android.R.layout.list_content, null);
			view.setBackgroundColor(0xffffffff);
			return view;
		}


		@Override
		public void onCreate(Bundle savedInstanceState) {
			Log.i(TAG,"ArrayListFragment - In onCreate");
			super.onCreate(savedInstanceState);
		}


		@Override
		public void onPause() {
			Log.i(TAG,"ArrayListFragment - In onPause");
			super.onPause();
		}


		@Override
		public void onResume() {
			Log.i(TAG,"ArrayListFragment - In onResume");
			super.onResume();
		}


		@Override
		public void onStart() {
			Log.i(TAG,"ArrayListFragment - In onStart");
			super.onStart();
		}


		@Override
		public void onStop() {
			Log.i(TAG,"ArrayListFragment - In onStop");
			super.onStop();
		}


		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			Log.i(TAG,"ArrayListFragment - In onViewCreated");
			super.onViewCreated(view, savedInstanceState);			
		}
		
		@Override
		public void onDestroyView() {
			Log.i(TAG,"ArrayListFragment - onDestroyView");
			super.onDestroy();
		}
		
		@Override
		public void onDestroy() {
			Log.i(TAG,"ArrayListFragment - On Destroy");
			super.onDestroy();
		}

		@Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i(TAG, "Item clicked: " + id + " & "+l.getItemAtPosition(position));
            File file = files[position];
            Log.i(TAG, "Path of the selected file :" + file.getAbsolutePath());
            if(file.exists() && file.isDirectory()) {
            	mPath = file;
            	((Mp3SearchActivity)getActivity()).showDetails();
            }else {
            	Log.i(TAG, "It's not going in..");
            }
        }
		
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
*/
}
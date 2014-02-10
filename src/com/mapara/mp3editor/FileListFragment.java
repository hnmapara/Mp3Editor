package com.mapara.mp3editor;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by harshit on 2/3/14.
 */
public class FileListFragment extends ListFragment {

    private static final String TAG = FileListFragment.class.getSimpleName();
    private String[] filenames;
    private File[] files;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "ArrayListFragment - In onActivityCreated");
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final EditText input = new EditText(getActivity());
                String filePath = ((ContentAdapter.ViewHolder)view.getTag()).filePath;
                final File f = new File(filePath);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Update Album Name for : " + f.getName())
                        .setMessage("Enter album name below and it will applied to all files under this directory")
                        .setView(input)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final Editable value = input.getText();
                                final ProgressBar progressBar = new ProgressBar(getActivity());
                                new AsyncTask<Void, Void,Void>() {

                                    @Override
                                    protected Void doInBackground(Void[] objects) {
                                        File[] files = f.listFiles(Mp3Utility.getFileNameFilter(false));
                                        String[] paths = new String[files.length];
                                        int index=0;
                                        for(File file: files) {
                                            if(!file.isDirectory()) {
                                                Mp3Utility.setMP3FileInfo2(file.getAbsolutePath(), new Mp3Info(value.toString(), ""));
                                                paths[index++] = file.getAbsolutePath();
                                            }
                                        }
                                        Mp3Utility.scanSDCardFile(getActivity(),paths);
                                        return null;
                                    }

                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        input.setVisibility(View.GONE);
                                        builder.setView(progressBar);

                                    }

                                    @Override
                                    protected void onPostExecute(Void v) {
                                        super.onPostExecute(v);
                                    }
                                }.execute();
                            }
                            })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }).show();
                return true;
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"ArrayListFragment - In onCreateView..");
        ((Mp3SearchActivity)getActivity()).loadFileList();
        filenames = Mp3SearchActivity.mFilenameList;
        files = Mp3SearchActivity.mFileListfiles;
        if(filenames==null) {
            View view = inflater.inflate(R.layout.fragment_nodata, container, false);
            view.setBackgroundColor(0xffffffff);
            return view;
        }
        Log.i(TAG,"ArrayListFragment - In onCreateView - files : " + files.length );

        setListAdapter(new ContentAdapter(getActivity(),R.layout.list_item,filenames,files));
//			setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, filenames));
        View view = inflater.inflate(android.R.layout.list_content, container, false);
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
        Log.i(TAG, "Item clicked: " + id + " & "+" position :" + position +" -"+l.getItemAtPosition(position));
        File file = files[position];
        Log.i(TAG, "Path of the selected file :" + file.getAbsolutePath());
        if(file.exists() && file.isDirectory()) {
            Mp3SearchActivity.mPath = file;
            ((Mp3SearchActivity)getActivity()).showDetails();
        }else {

            TextView tv =(TextView)v.findViewById(R.id.filename);
            Log.i(TAG, "It's not going in.." + tv.getText());
        }
    }

}

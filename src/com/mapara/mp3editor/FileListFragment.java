package com.mapara.mp3editor;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

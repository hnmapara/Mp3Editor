package com.mapara.mp3editor;

import java.io.IOException;

import android.util.Log;
import de.vdheide.mp3.FrameDamagedException;
import de.vdheide.mp3.ID3Exception;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2Exception;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.MP3File;
import de.vdheide.mp3.NoMP3FrameException;
import de.vdheide.mp3.TagContent;
import de.vdheide.mp3.TagFormatException;

public class Mp3Utility {
	
	public static final String TAG = "Mp3Utility";

	public static Mp3Info getMP3FileInfo(String mp3FilePath) throws ID3v2WrongCRCException, ID3v2DecompressionException, ID3v2IllegalVersionException, IOException, NoMP3FrameException {
		MP3File mp3File = new MP3File(mp3FilePath);
		TagContent tagContent = null;
		try {
			tagContent = mp3File.getAlbum();
		} catch (FrameDamagedException e) {
			Log.e(TAG, e.toString());
			return null;
		}
		Log.i(TAG, "Tag-Album :" +tagContent.getTextContent());
		
		return new Mp3Info(tagContent.getTextContent(), null);	
	}
	
	public static String setMP3FileInfo(String mp3FilePath, Mp3Info mp3Info) throws ID3Exception, ID3v2Exception, IOException, NoMP3FrameException {
		MP3File mp3File = new MP3File(mp3FilePath);
		TagContent newTag = new TagContent();
		Log.i(TAG,"Album Name to be set :" + mp3Info.albumName);
		newTag.setContent(mp3Info.albumName);
		try {
			mp3File.setAlbum(newTag);
		} catch (TagFormatException e) {
			Log.e(TAG, e.toString());
			return null;
		}
		
		mp3File.update();
		return mp3Info.albumName;
	}
}

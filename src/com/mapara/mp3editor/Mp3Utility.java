package com.mapara.mp3editor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.util.Log;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

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

    public static Mp3Info getMP3FileInfo2(String mp3FilePath) {
        File src = new File(mp3FilePath);
        MusicMetadataSet src_set = null;
        try {
            src_set = new MyID3().read(src);
        } catch (IOException e1) {
            e1.printStackTrace();
        } // read metadata

        if (src_set == null) // perhaps no metadata
        {
            Log.i(TAG, "sournce file is NULL");
        }
        else
        {
            String artist,album = null;
            try{
                IMusicMetadata metadata = src_set.getSimplified();
                 artist = metadata.getArtist();
                 album = metadata.getAlbum();
                String song_title = metadata.getSongTitle();
                Number track_number = metadata.getTrackNumber();
                Log.i(TAG, "artist - " + artist);
                Log.i(TAG, "album - " +album);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return new Mp3Info(album, null);
        }
        return null;
    }

    public static String setMP3FileInfo2(String mp3FilePath, Mp3Info mp3Info)  {
        File src = new File(mp3FilePath);
        MusicMetadata meta = new MusicMetadata("name");
        Log.i(TAG,"Album Name to be set :" + mp3Info.albumName);
        meta.setAlbum(mp3Info.albumName);
//        meta.setArtist("CS");
        try {
            MusicMetadataSet src_set = new MyID3().read(src);
            new MyID3().update(src, src_set, meta);
            return meta.getAlbum();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ID3WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  // write updated metadata
        return null;
    }
}

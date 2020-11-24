package com.facebookvideodownloader.free;



import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.facebookvideodownloader.free.R;

import java.io.File;

/**
 * Created by harishannam on 15/02/15.
 */

public class DownloadDialog extends DialogFragment {

    private static final String TAG_VIDURL = "video_url";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.download_alert, container,
                false);
        getDialog().setTitle("Choose Option");
        Bundle mArgs = getArguments();
        final String vidData = mArgs.getString("vid_data");
        final String vidID = mArgs.getString("vid_id");

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Button download_video = (Button) rootView.findViewById(R.id.download_video);
        download_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	downloadFB(vidData, vidID);
                getDialog().dismiss();
            }
        });
        Button stream_video = (Button) rootView.findViewById(R.id.stream_video);
        stream_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	            	
                streamFB(vidData);
                getDialog().dismiss();
            }
        });
        Button close_dialog = (Button) rootView.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	getDialog().dismiss();
            }
        });
        // Do something else
        return rootView;
    }

    public void downloadFB(String vid_url, String vid_id){
        try {
            String mBaseFolderPath = android.os.Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "FacebookVideos2" + File.separator;
            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }
            String mFilePath = "file://" + mBaseFolderPath + "/" + vid_id + ".mp4";

            Uri downloadUri = Uri.parse(vid_url);
            DownloadManager.Request req = new DownloadManager.Request(downloadUri);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager dm = (DownloadManager) getActivity().getSystemService(getActivity().getApplicationContext().DOWNLOAD_SERVICE);
            dm.enqueue(req);

            Toast toast = Toast.makeText(getActivity(),
                    "Download Started",
                    Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getActivity(),
                    "Download Failed",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void streamFB(String vid_url) {
        try {
            Intent i = new Intent(getActivity().getApplicationContext(), StreamVideo.class);
            i.putExtra(TAG_VIDURL, vid_url);
            startActivity(i);
            Toast toast = Toast.makeText(getActivity(),
                    "Streaming Started",
                    Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getActivity(),
                    "Streaming Failed",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
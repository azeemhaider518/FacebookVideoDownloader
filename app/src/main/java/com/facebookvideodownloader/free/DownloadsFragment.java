package com.facebookvideodownloader.free;



import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.facebookvideodownloader.free.R;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

/**
 * Created by harishannam on 15/02/15.
 */
public class DownloadsFragment extends Fragment {
    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    GridView gridview;
    View rootView;
    DownloadsGridAdapter adapter;
    File file;
    Handler handler1 = new Handler();
    private static final String TAG_VIDURL = "video_url";
    InterstitialAd mInterstitialAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.down_fragment, container, false);

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        gridview = (GridView) rootView.findViewById(R.id.gridview);

        updateDownloads();
       loadAd();
        
        return rootView;
    }

    private void loadAd() {

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);

    }

    public void updateDownloads() {
        final ProgressBar loading = (ProgressBar) rootView.findViewById(R.id.downloads_loading);
        loading.setVisibility(View.VISIBLE);
        gridview.setVisibility(View.GONE);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("UPDATED", "DOWNLOADS UPDATED");
                file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "FacebookVideos2" + File.separator);
                Log.e("FILES", file.toString());

                if (file.isDirectory()) {
                    listFile = file.listFiles();
                    Log.e("FILES", listFile.toString());
                    // Create a String array for FilePathStrings
                    FilePathStrings = new String[listFile.length];
                    // Create a String array for FileNameStrings
                    FileNameStrings = new String[listFile.length];

                    for (int i = 0; i < listFile.length; i++) {
                        // Get the path of the image file
                        FilePathStrings[i] = listFile[i].getAbsolutePath();
                        // Get the name image file
                        FileNameStrings[i] = listFile[i].getName();
                        Log.e("FILESEACH", FileNameStrings[i]);
                    }
                }

                adapter = new DownloadsGridAdapter(getActivity(), FilePathStrings, FileNameStrings);
                gridview.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                gridview.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        if(mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }else {
                            Intent i = new Intent(getActivity(), StreamVideo.class);
                            i.putExtra(TAG_VIDURL, FilePathStrings[position]);
                            startActivity(i);
                        }


                    }

                });
                loading.setVisibility(View.GONE);
                gridview.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }
}

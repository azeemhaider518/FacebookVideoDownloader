package com.facebookvideodownloader.free;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.facebookvideodownloader.free.R;

/**
 * Created by harishannam on 15/02/15.
 */
public class DownloadsGridAdapter extends BaseAdapter{
    // Declare variables
    private Activity activity;
    private String[] filepath;
    private String[] filename;

    private static LayoutInflater inflater = null;

    public DownloadsGridAdapter(Activity a, String[] fpath, String[] fname) {
        activity = a;
        filepath = fpath;
        filename = fname;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

        if(filepath!=null && filepath.length>0){
            return filepath.length;
        }else {
            return 0;
        }


    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.dowload_grid_item, null);
        ImageView image = (ImageView) vi.findViewById(R.id.image);
        Bitmap bmp = ThumbnailUtils.createVideoThumbnail(filepath[position],
                Thumbnails.MICRO_KIND);
        image.setImageBitmap(bmp);
        return vi;
    }
}

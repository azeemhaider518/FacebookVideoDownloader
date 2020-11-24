package com.facebookvideodownloader.free;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by harishannam on 15/02/15.
 */
public class FBTabsAdapter extends FragmentPagerAdapter {
    public FBTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new FBFragment();
            case 1:
                // Games fragment activity
                return new DownloadsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
    
}

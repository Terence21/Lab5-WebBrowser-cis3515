package temple.edu.webBrowserApp;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentStatePagerAdapter {

    ArrayList<PageViewerFragment> pageViewerFragments;

    public PageAdapter(FragmentManager fm, ArrayList<PageViewerFragment> pageViewerFragments){
        super(fm);
        this.pageViewerFragments = pageViewerFragments;


    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pageViewerFragments.get(position);
    }

    @Override
    public int getCount() {
        if (pageViewerFragments != null) {
            return pageViewerFragments.size();
        }else {
            return 0;
        }
    }




}

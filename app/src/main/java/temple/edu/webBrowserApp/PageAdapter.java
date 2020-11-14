package temple.edu.webBrowserApp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentStatePagerAdapter {

    ArrayList<PageViewerFragment> pageViewerFragments;

    public PageAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public int getCount() {
        return pageViewerFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}

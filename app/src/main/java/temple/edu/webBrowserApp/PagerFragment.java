package temple.edu.webBrowserApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PagerFragment extends Fragment {

    PageViewerFragment fragment;
    PageAdapter pageAdapter;
    ViewPager pager;
    FragmentStatePagerAdapter fragmentStatePagerAdapter;

    public PagerFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_page_control, container, false);
        pageAdapter = new PageAdapter(getChildFragmentManager());
        //pager = view.findViewById(R.id.);
        pager.setAdapter(pageAdapter);
        return view;
    }


    public void displayFragment(Fragment fragment) {
        try {
            this.fragment = (PageViewerFragment) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getLocalizedMessage());
        }
    }


}

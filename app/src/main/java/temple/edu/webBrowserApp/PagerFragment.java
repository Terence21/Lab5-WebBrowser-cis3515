package temple.edu.webBrowserApp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class PagerFragment extends Fragment {


    PageAdapter pageAdapter;
    ViewPager pager;
    ViewerListener listener;

    public PagerFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public static PagerFragment newInstance(ArrayList<PageViewerFragment> pageViewerFragments){
        return new PagerFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyDataSetChange();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_pager, container, false);
        pageAdapter = new PageAdapter(getChildFragmentManager(), listener.getViewer());
        setRetainInstance(true);
        pager = view.findViewById(R.id._viewPager);
        pager.setAdapter(pageAdapter);

        return view;
    }

    public int currnetItem(){
        return pager.getCurrentItem();
    }

    public void changePage(int num){
        pager.setCurrentItem(num);
    }

    public void notifyDataSetChange(){
        pageAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        try{
            listener = (ViewerListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(e.getLocalizedMessage());
        }
    }

    public interface ViewerListener{
        public ArrayList<PageViewerFragment> getViewer();
    }






}

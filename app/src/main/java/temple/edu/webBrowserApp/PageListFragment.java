package temple.edu.webBrowserApp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageListFragment extends Fragment {


    public PageListFragment() {
        // Required empty public constructor
    }

    public static PageListFragment newInstance(String param1, String param2) {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_list, container, false);
    }
}
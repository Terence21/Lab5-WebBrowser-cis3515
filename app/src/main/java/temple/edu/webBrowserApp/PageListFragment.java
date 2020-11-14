package temple.edu.webBrowserApp;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
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


    ListView listView;
    private PageListAdapter adapter;
    private changePageListener listener;

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
        View view  = inflater.inflate(R.layout.fragment_page_list, container, false);
        listView = view.findViewById(R.id._listView);
        adapter = new PageListAdapter(getContext(), ((PagerFragment.ViewerListener) getActivity()).getViewer());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.changePage(position);
            }
        });
        return  view;
    }

    public void notifyDataSetChange(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        try {
            listener = (changePageListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(e.getLocalizedMessage());
        }
    }

    public interface changePageListener{
        public void changePage(int pageNum);
    }
}
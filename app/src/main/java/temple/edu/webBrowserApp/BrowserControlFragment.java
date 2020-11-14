package temple.edu.webBrowserApp;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BrowserControlFragment extends Fragment {

    ImageButton pageButton;
    TabListener listener;
    public BrowserControlFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_browser_control, container, false);
        pageButton = (ImageButton) view.findViewById(R.id._tabButton);

        pageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.createNewTab();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (TabListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(e.getLocalizedMessage());
        }
    }

    public interface TabListener{
        public void createNewTab();
    }
}
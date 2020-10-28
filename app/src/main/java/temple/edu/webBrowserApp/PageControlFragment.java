package temple.edu.webBrowserApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;


public class PageControlFragment extends Fragment {

    private WebMenuListener listener;
    private String url;
    private TextView urlView;
    private ImageButton searchButton;
    private boolean isSearched = false;

    Handler content = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            urlView.setText((String) msg.obj);
            return false;
        }
    });
    public PageControlFragment() {
        // Required empty public constructor
    }

    public static PageControlFragment newInstance(String url) {
        PageControlFragment fragment = new PageControlFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.url = getArguments().getString("key");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page_control, container, false);
        urlView = (EditText) view.findViewById(R.id._urlTextView);
        searchButton = (ImageButton) view.findViewById(R.id._searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = urlView.getText().toString(); //format url here?
                listener.buttonPressed(url);
                Log.i("url", "url: " + url);
                isSearched();
            }
        });

        url = urlView.getText().toString();

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (WebMenuListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(e.getLocalizedMessage());
        }
    }

    public boolean isSearched(){
        this.isSearched = true;
        return true;
    }

    public String getUrl(){
        return url;
    }

    public boolean validateNetwork(){
        ConnectivityManager conn = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network networkInfo = conn.getActiveNetwork();
        return conn.isActiveNetworkMetered(); // check that is correct
    }

    public interface WebMenuListener{
        public void buttonPressed(String url);
    }
}
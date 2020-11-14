package temple.edu.webBrowserApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
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

import java.util.ArrayList;
import java.util.Stack;


public class PageControlFragment extends Fragment {

    private WebMenuListener listener;
    private String url;
    private TextView urlView;
    private ImageButton searchButton;
    private ImageButton backButton;
    private ImageButton forwardButton;

    private int position;
    private ArrayList<String> urls;



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

    public static PageControlFragment newInstance(Context context, int position, ArrayList <String> urls) {
        PageControlFragment fragment = new PageControlFragment();

        Bundle args = new Bundle();
        args.putInt(context.getResources().getString(R.string.position), position);
        args.putStringArrayList(context.getResources().getString(R.string.urls), urls);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.urls = getArguments().getStringArrayList(getResources().getString(R.string.urls));
            this.position = getArguments().getInt(getResources().getString(R.string.position));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_page_control, container, false);
        urlView = (EditText) view.findViewById(R.id._urlTextView);
        searchButton = (ImageButton) view.findViewById(R.id._searchButton);
        backButton = (ImageButton) view.findViewById(R.id._backButton);
        forwardButton = (ImageButton) view.findViewById(R.id._forwardButton);

        View.OnClickListener controlOCL = (v) -> {
            if (v.equals(searchButton)){
                listener.searchPressed(correctURL(urlView.getText().toString()));
            } else if (v.equals(backButton)){
                listener.backPressed();
            }else if (v.equals(forwardButton)){
                listener.forwardPressed();
            }
        };

        searchButton.setOnClickListener(controlOCL);
        backButton.setOnClickListener(controlOCL);
        forwardButton.setOnClickListener(controlOCL);

        return view;
    }

    public static String correctURL(String badURL){
        String correctURL = "";
        if (!badURL.startsWith("http://") && !badURL.startsWith("https://")){
            correctURL = "https://";
        }
        if (!badURL.contains("www.")){
            if (badURL.contains("https://")) {
                badURL = badURL.replace("https://", "");
            } else if (badURL.contains("http://")){
                badURL = badURL.replace("http://", "");
            }

            correctURL = "https://www.";
        }
        correctURL += badURL;

        if (!badURL.endsWith(".com") && !badURL.endsWith(".org") && !badURL.endsWith(".edu") && !badURL.endsWith(".gov")){
            correctURL += ".com";
        }
        return correctURL;
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


    public void updateMembers(String url){
        this.urlView.setText(url);
    }


    public interface WebMenuListener{
        public void searchPressed(String url);
        public void backPressed();
        public void forwardPressed();


    }
}
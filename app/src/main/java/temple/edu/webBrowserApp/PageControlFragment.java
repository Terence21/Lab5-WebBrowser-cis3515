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


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = BrowserActivity.correctURL(urlView.getText().toString());
                while (position != urls.size()-1){
                    urls.remove(urls.size()-1);
                }
                urls.add(url);
                position++;
                listener.searchPressed(position, urls);
                Log.i("url", "url: " + url);
                urlView.setText(urls.get(position));

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("back", "backPressed: true");
                if (position > 0 ) {
                    position--;
                    listener.backPressed(position, urls);
                    urlView.setText(urls.get(position));
                }


            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("forward", "forwardPressed: true");
                if (position < urls.size()-1 ) {
                    position++;
                    listener.forwardPressed(position, urls);
                    urlView.setText(urls.get(position));
                }


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


    public void updateMembers(ArrayList<String> urls, int postion){
        this.urls = urls;
        this.position = postion;
    }

    public boolean validateNetwork(){
        ConnectivityManager conn = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network networkInfo = conn.getActiveNetwork();
        return conn.isActiveNetworkMetered(); // check that is correct
    }

    public interface WebMenuListener{
        public void searchPressed(int position, ArrayList <String> urls);
        public void backPressed(int position, ArrayList <String> urls);
        public void forwardPressed(int position, ArrayList <String> urls);


    }
}
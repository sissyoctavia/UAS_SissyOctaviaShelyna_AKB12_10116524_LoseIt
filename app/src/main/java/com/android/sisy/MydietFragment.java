package com.android.sisy;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ceritamahasiswaa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MydietFragment extends Fragment {
    //fetch data init
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private KontenDetailFragment kontenDetailFragment;
    private static String url = "http://sissy.yumayusuf.web.id/api/konten_mydiet.php";
    ArrayList<HashMap<String, String>> tipsList;

    public MydietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        tipsList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.list);

        new GetContacts().execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);
            }
        });
        return view;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Mengunduh konten...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray tips = jsonObj.getJSONArray("tips");

                    // looping through All Contacts
                    for (int i = 0; i < tips.length(); i++) {
                        JSONObject c = tips.getJSONObject(i);

                        String content = c.getString("content");
                        String title = c.getString("title");
                        String image = c.getString("image");

                        // tmp hash map for single contact
                        HashMap<String, String> tip = new HashMap<>();

                        // adding each child node to HashMap key => value
                        tip.put("title", title);
                        tip.put("content", content);
                        tip.put("image", image);

                        // adding tip to tip list
                        tipsList.add(tip);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Tidak dapat menghubungi server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Tidak dapat menghubungi server, silahkan cek koneksi internet anda",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new myAdapter(
                    getActivity(), tipsList,
                    R.layout.list_item_view, new String[]{"title", "content", "image"}, new int[]{R.id.title,
                    R.id.content});

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, String> clickedItem = (HashMap<String, String>) tipsList.get(position);
                    String title = clickedItem.get("title");
                    String content = clickedItem.get("content");
                    String image = clickedItem.get("image");
//
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("content", content);
                    bundle.putString("image", image);

                    kontenDetailFragment = new KontenDetailFragment();
                    kontenDetailFragment.setArguments(bundle);
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main_frame, kontenDetailFragment);
                    ft.commit();
                }
            });
        }

    }

    private class myAdapter extends SimpleAdapter {

        private Context mContext;

        public myAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            mContext = context;

        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            // assuming you have an instance of ImageDownloader here
            //
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_view,
                        null);
            }

            HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

            TextView titleTextView = (TextView) convertView
                    .findViewById(R.id.title);
//            TextView contentTextView = (TextView) convertView
//                    .findViewById(R.id.content);
            ImageView iconImageView = (ImageView) convertView
                    .findViewById(R.id.image);

            String titleString = (String) data.get("title");
            String contentString = (String) data.get("content");
            String imageUri = (String) data.get("image");
            titleTextView.setText(titleString);
//            contentTextView.setText(contentString);
            Picasso.get().load(imageUri).into(iconImageView);

            return convertView;
        }

    }

}



package com.android.sisy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.ceritamahasiswaa.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class KontenDetailFragment extends Fragment {


    private TipsFragment dailyFragment;
    public KontenDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tips_detail, container, false);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView contentView = (TextView) view.findViewById(R.id.content);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                dailyFragment = new TipsFragment();
                ft.replace(R.id.main_frame, dailyFragment);
                ft.commit();
            }
        });

        titleView.setText(getArguments().getString("title"));
        contentView.setText(getArguments().getString("content"));
        Picasso.get().load(getArguments().getString("image")).into(imageView);
        return view;
    }

}

package com.murach.newsreader;

/**
 * Created by wilso49 on 5/23/17.
 */

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

public class itemFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view1 = (inflater).inflate(R.layout.fragment_item, container, false);

        // get references to widgets
        TextView titleTextView = (TextView) view1.findViewById(R.id.titleTextView);
        TextView pubDateTextView = (TextView) view1.findViewById(R.id.pubDateTextView);
        TextView descriptionTextView = (TextView) view1.findViewById(R.id.descriptionTextView);
        TextView linkTextView = (TextView) view1.findViewById(R.id.linkTextView);

        // get the intent
        Intent intent = getActivity().getIntent();

        // get data from the intent
        String pubDate = intent.getStringExtra("pubdate");
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description").replace('\n', ' ');

        // display data on the widgets
        pubDateTextView.setText(pubDate);
        titleTextView.setText(title);
        descriptionTextView.setText(description);

        // set listener
        linkTextView.setOnClickListener(this);

        return  view1;
    }

    @Override
    public void onClick(View v) {
        // get the intent
        Intent intent = getActivity().getIntent();

        // get the Uri for the link
        String link = intent.getStringExtra("link");
        Uri viewUri = Uri.parse(link);

        // create the intent and start it
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, viewUri);
        startActivity(viewIntent);
    }
}

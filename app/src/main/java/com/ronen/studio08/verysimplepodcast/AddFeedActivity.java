package com.ronen.studio08.verysimplepodcast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ronen.studio08.verysimplepodcast.retrofitCloud.SampleFeed;

import java.util.List;


/**
 * Created by studio08 on 5/10/2016.
 */
public class AddFeedActivity extends AppCompatActivity implements View.OnClickListener,
        RetrofitCaller.OnRetrofitCalledListener {

    List<SampleFeed> sampleFeedList;
    RecyclerView recyclerView;
    RetrofitCaller retrofitCaller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        startViews();

        retrofitCaller = new RetrofitCaller(this);
        retrofitCaller.sampleFeedRetrofitCaller();
    }



    private void startViews() {
        recyclerView = (RecyclerView) findViewById(R.id.sample_recyclerView);
        recyclerView.setHasFixedSize(true);
        Button button = (Button) findViewById(R.id.add_button1);
        button.setOnClickListener(this);
    }

    @Override
    public void retrofitCalled(List<SampleFeed> sampleFeedList) {
        if (sampleFeedList != null) {
            this.sampleFeedList = sampleFeedList;

            GridLayoutManager gridLayoutManager = new GridLayoutManager(AddFeedActivity.this,3);
            recyclerView.setLayoutManager(gridLayoutManager);
            AddFeedRVAdapter rvAdapter = new AddFeedRVAdapter(AddFeedActivity.this, sampleFeedList);
            recyclerView.setAdapter(rvAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.feed_editText1);
//        addFeedToSQLite(editText.getText().toString());

//        if (retrofitCaller == null)
//            new RetrofitCaller(this).addFeedToSQLite(editText.getText().toString());
//        else
            retrofitCaller.addFeedToSQLite(editText.getText().toString());
        finish();
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        addFeedToSQLite(sampleFeedList.get(position).getFeedUrl());
//        finish();
////        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
//    }
}

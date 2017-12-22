package com.junple.refresh;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final JunpleRefreshLayout layout = (JunpleRefreshLayout)findViewById( R.id.refresh);

        RecyclerView rv = (RecyclerView)findViewById( R.id.recycler);
        MyAdapter ad = new MyAdapter();
        rv.setAdapter( ad);
        rv.setLayoutManager( new LinearLayoutManager( this));
        layout.setOnRefreshLitener(new JunpleRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final JunpleRefreshLayout view) {

                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.finishRefresh( 600);
                            }
                        });
                    }
                },2000);
            }
        });
    }
}

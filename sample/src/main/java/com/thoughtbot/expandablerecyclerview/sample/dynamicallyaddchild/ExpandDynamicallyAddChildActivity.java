package com.thoughtbot.expandablerecyclerview.sample.dynamicallyaddchild;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.sample.Artist;
import com.thoughtbot.expandablerecyclerview.sample.Genre;
import com.thoughtbot.expandablerecyclerview.sample.R;

import java.util.Collections;
import java.util.List;

import static com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory.makeBluegrassArtists;
import static com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory.makeClassicArtists;
import static com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory.makeGenresWithoutArtists;
import static com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory.makeJazzArtists;
import static com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory.makeRockArtists;
import static com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory.makeSalsaArtists;

public class ExpandDynamicallyAddChildActivity extends AppCompatActivity {

    public GenreAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getClass().getSimpleName());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        adapter = new GenreAdapter(makeGenresWithoutArtists());
        adapter.setOnGroupClickListener(new OnGroupClickListener<Genre>() {
            @Override
            public boolean onGroupClick(int flatPos) {
                return false;
            }

            @Override
            public void onGroupItemClick(Genre group, int flatPos) {
                fetchChildByGroupTitle(group.getTitle(), flatPos);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button clear = (Button) findViewById(R.id.toggle_button);
        clear.setVisibility(View.GONE);
    }

    // ---------------------------
    // just simulating an api call
    // ---------------------------
    private void fetchChildByGroupTitle(final String title, final int position) {
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                List<Artist> artists = Collections.emptyList();

                switch (title) {
                    case "Rock":
                        artists = makeRockArtists();
                        break;
                    case "Jazz":
                        artists = makeJazzArtists();
                        break;
                    case "Classic":
                        artists = makeClassicArtists();
                        break;
                    case "Salsa":
                        artists = makeSalsaArtists();
                        break;
                    case "Bluegrass":
                        artists = makeBluegrassArtists();
                        break;
                }
                adapter.onResult(artists, position);
            }
        };


        adapter.onFetchingArtists(position);
        Handler mHandler = new Handler();
        mHandler.postDelayed(
                mRunnable,
                2000
        );
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

}

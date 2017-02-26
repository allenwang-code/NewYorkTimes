package allenwang.newyorktimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import allenwang.newyorktimes.model.Doc;
import allenwang.newyorktimes.model.News;
import allenwang.newyorktimes.network.NewYorkTimes;
import allenwang.newyorktimes.network.Retrofit;
import allenwang.newyorktimes.recyclerView.NewsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity {


    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private NewsAdapter adapter;
    private EditText searchEditText;
    private Button  doneBtn;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        searchEditText = (EditText) findViewById(R.id.editText);
        doneBtn = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new NewsAdapter(this, new ArrayList<Doc>());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        recyclerView.setLayoutManager(gridLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);


        getDataFromRemote();

    }

    private void getDataFromRemote() {
        NewYorkTimes ny = Retrofit.getInstance().createService(NewYorkTimes.class);
        Call<News> news = ny.getNews();
        news.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News n = response.body();
                if (n == null) { return; }
                allenwang.newyorktimes.model.Response r =  n.getResponse();
                if (r == null) { return; }
                adapter.updateData(r.getDocs());

                int curSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(curSize, r.getDocs().size());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

        Toast.makeText(this, "MAX!!!", Toast.LENGTH_SHORT).show();
    }



    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_sort:
                    Intent i = new Intent();
                    i.setClass(MainActivity.this, SettingActivity.class);
                    startActivity(i);
                    break;
            }

            return true;
        }
    };

}
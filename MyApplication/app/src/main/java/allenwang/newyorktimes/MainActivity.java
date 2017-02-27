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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import allenwang.newyorktimes.model.Doc;
import allenwang.newyorktimes.model.News;
import allenwang.newyorktimes.network.NewYorkTimes;
import allenwang.newyorktimes.network.Retrofit;
import allenwang.newyorktimes.recyclerView.NewsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    final static int RESULT_CODE = 100;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private NewsAdapter adapter;
    private EditText searchEditText;
    private Button  doneBtn;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    String qString;
    String qBeginDay;
    String qSort;
    String qNewDesk;

    private int page = 0;
    private List<Doc> docs = new ArrayList<Doc>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        searchEditText = (EditText) findViewById(R.id.editText);
        doneBtn = (Button) findViewById(R.id.button);
        doneBtn.setOnClickListener(listener);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new NewsAdapter(this, docs);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CODE) {
            if (resultCode != RESULT_OK) { return; }

            Bundle b = data.getExtras();
            qBeginDay = b.getString(Constant.BEGIN_DATE);
            qSort = b.getString(Constant.SORT);
            qNewDesk = b.getString(Constant.FQ);

            resetState();
            getNewsByPage();
        }
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        getNewsByPage();
        Toast.makeText(this, "load new content...", Toast.LENGTH_SHORT).show();
    }

    private void getNewsByPage() {
        String s = searchEditText.getText().toString();
        qString = s.isEmpty()?  null : s; // must be null

        NewYorkTimes ny = Retrofit.getInstance().createService(NewYorkTimes.class);
        Call<News> news = ny.getNews(String.valueOf(page),
                qString, qBeginDay, qSort, qNewDesk
        );
        news.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News n = response.body();
                if (n == null) { return; }
                allenwang.newyorktimes.model.Response r =  n.getResponse();
                if (r == null) { return; }
                docs.addAll(r.getDocs());
                adapter.updateData(docs);
                notifyAdapter(r.getDocs().size());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

        page = page + 1;
    }

    private void notifyAdapter(int newArraySize) {
        int curSize = adapter.getItemCount();
        adapter.notifyItemRangeInserted(curSize, newArraySize);

        adapter.notifyDataSetChanged();
    }




    private void resetState() {
        page = 0;
        scrollListener.resetState();
        docs.clear();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button:
                    resetState();
                    getNewsByPage();
                    break;
            }
        }
    };

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_sort:
                    Intent i = new Intent();
                    i.setClass(MainActivity.this, SettingActivity.class);
                    startActivityForResult(i, RESULT_CODE);
                    break;
            }

            return true;
        }
    };

}
package com.example.kenny.myapplication;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.wang.avi.AVLoadingIndicatorView;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements TaskComplete {
    private RecyclerAdapter madapter;
    private RecyclerView recyclerView;
    public Activity activity;
    private SimpleCursorAdapter mSAdapter;
    private String myQuery;
    private  AVLoadingIndicatorView avi;
    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        avi = (AVLoadingIndicatorView)findViewById(R.id.avi);
        recyclerView = (RecyclerView)findViewById(R.id.rvNumbers);
        activity = this;
        int numberOfColumns = 2;
        page = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
        getRecycler.save_View(recyclerView);
        madapter = new RecyclerAdapter(getApplicationContext(), new ArrayList<>());
        madapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                avi.setVisibility(View.VISIBLE);
                String tourl = "https://api.unsplash.com/photos/?per_page=30&page="+page+"&client_id=a6c0389a37254f023d0c1a63b813fd63fafafb" +
                        "2f10d87341c63fecafd0776851";
                MyTaskParams params = new MyTaskParams(false,tourl,madapter);
                new ImageLoadTask(MainActivity.this).execute(params);
                page++;
            }
        });
        recyclerView.setAdapter(madapter);
        String tourl = "https://api.unsplash.com/photos/?per_page=30&page=1&client_id=a6c0389a37254f023d0c1a63b813fd63fafafb" +
                "2f10d87341c63fecafd0776851";
        MyTaskParams params = new MyTaskParams(false,tourl,madapter);
        avi.setVisibility(View.VISIBLE);
        new ImageLoadTask(MainActivity.this).execute(params);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        final String[] from = new String[] {"words"};
        final int[] to = new int[] {android.R.id.text1};
        mSAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(mSAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                MatrixCursor temp = (MatrixCursor) mSAdapter.getItem(position);
                myQuery = temp.getString(temp.getColumnIndex("words"));
                searchView.setQuery(myQuery,true);
                String url = "https://api.unsplash.com/search/?client_id=a6c0389a37254f023d0c1a63b813fd63fafafb2f10d87341c63fecafd0776851&per_page=30&page=1&query="+myQuery;
                madapter = new RecyclerAdapter(getApplicationContext(), new ArrayList<>());
                MyTaskParams params = new MyTaskParams(true,url,madapter);
                madapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        avi.setVisibility(View.VISIBLE);
                        String url = "https://api.unsplash.com/search/?client_id=a6c0389a37254f023d0c1a63b813fd63fafafb2f10d87341c63fecafd0776851&per_page=30&page="+page+"&query="+myQuery;
                        MyTaskParams params = new MyTaskParams(true,url,madapter);
                        new ImageLoadTask(MainActivity.this).execute(params);
                        page++;
                    }
                });
                recyclerView.setAdapter(madapter);
                avi.setVisibility(View.VISIBLE);
                new ImageLoadTask(MainActivity.this).execute(params);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String queryText) {
                searchView.clearFocus();
                String url = "https://api.unsplash.com/search/?client_id=a6c0389a37254f023d0c1a63b813fd63fafafb2f10d87341c63fecafd0776851&per_page=30&page=1&query="+queryText;
                madapter = new RecyclerAdapter(getApplicationContext(), new ArrayList<>());
                MyTaskParams params = new MyTaskParams(true,url,madapter);
                madapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        avi.setVisibility(View.VISIBLE);
                        String url = "https://api.unsplash.com/search/?client_id=a6c0389a37254f023d0c1a63b813fd63fafafb2f10d87341c63fecafd0776851&per_page=30&page="+page+"&query="+myQuery;
                        MyTaskParams params = new MyTaskParams(true,url,madapter);
                        new ImageLoadTask(MainActivity.this).execute(params);
                        page++;
                    }
                });
                recyclerView.setAdapter(madapter);
                avi.setVisibility(View.VISIBLE);
                new ImageLoadTask(MainActivity.this).execute(params);
                return true;
            }
            private StringBuilder sb = new StringBuilder();
            @Override
            public boolean onQueryTextChange(String newText) {
                myQuery = newText;
                getWordsTask task;
                sb.delete(0,sb.length());
                sb.append("https://api.datamuse.com/sug?s=");
                sb.append(newText);
                sb.append("&max=5");
                task = new getWordsTask();
                task.execute(sb.toString());
                return true;
            }
        });


        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //madapter = new RecyclerAdapter(getApplicationContext(), picList);
                recyclerView.setAdapter(madapter);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTaskComplete(RecyclerAdapter madapter, boolean isSearch) {
        avi.setVisibility(View.INVISIBLE);
        if(madapter!=null){
            this.madapter = madapter;
        }
        if(madapter==null || madapter.mData==null){
            Toast.makeText(getApplicationContext(),"No Results",Toast.LENGTH_SHORT).show();
        }

    }
    public class getWordsTask extends AsyncTask<String,ArrayList<String>,ArrayList<String>> {

        public getWordsTask(){

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            HttpURLConnection connection = null;
            JsonReader reader = null;
            URL urlConnection;
            try {
                urlConnection = new URL(params[0]);
                connection = (HttpURLConnection)urlConnection.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new JsonReader(new InputStreamReader(stream));
                return getWords.readWordArray(reader);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(connection!=null){
                    connection.disconnect();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            String [] words = result.toArray(new String[result.size()]);
            final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "words" });
            for (int i=0; i<words.length; i++) {
                if (words[i].toLowerCase().startsWith(myQuery.toLowerCase())){
                    c.addRow(new Object[] {i, words[i]});
                }
            }
            mSAdapter.changeCursor(c);
            mSAdapter.notifyDataSetChanged();
        }
    }
}

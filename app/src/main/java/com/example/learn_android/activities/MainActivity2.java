package com.example.learn_android.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.learn_android.R;
import com.example.learn_android.fragments.HomeFragment;
import com.example.learn_android.fragments.MovieFragment;
import com.example.learn_android.fragments.SportFragment;

import java.util.ArrayList;

/**
 * 1. Android TabLayout
 *      Tab: TabLayout + TabItem
 *      Content: FrameLayout
 * 2. Search view
 *
 * https://www.javatpoint.com/android-tablayout-with-framelayout
 * https://www.javatpoint.com/android-searchview-on-toolbar
 */
public class MainActivity2 extends AppCompatActivity {
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Search view
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Search view
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Pineapple");
        list.add("Orange");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setVisibility(View.GONE);

        // TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        fragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new MovieFragment();
                        break;
                    case 2:
                        fragment = new SportFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
               /*if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView.setVisibility(View.GONE);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

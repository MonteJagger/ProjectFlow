package com.example.flow.projectflow;

/**
 * Created by THOMAS on 5/23/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class MainInterfaceActivty extends AppCompatActivity implements android.support.v7.app.ActionBar.TabListener {

    private ViewPager tabsviewPager;
    private Tabsadapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);

        //Get preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, ApplicationContextProvider.getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        Intent intentInfo = getIntent();
        //Set up view pager
        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);
        mTabsAdapter = new Tabsadapter(getSupportFragmentManager());
        tabsviewPager.setAdapter(mTabsAdapter);

        //Set up action Bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Tab courseTab = getSupportActionBar().newTab().setText("Courses").setTabListener(this);
        Tab browseTab = getSupportActionBar().newTab().setText("Browse").setTabListener(this);

        getSupportActionBar().addTab(courseTab);
        getSupportActionBar().addTab(browseTab);


        //Change views
        tabsviewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,ApplicationContextProvider.getContext().MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainInterfaceActivty.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed () {
        logout();
    }

    //=======================================================================================
    @Override
    public void onTabSelected(Tab selectedtab, FragmentTransaction arg1) {
        tabsviewPager.setCurrentItem(selectedtab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab selectedtab, FragmentTransaction arg1) {
        tabsviewPager.setCurrentItem(selectedtab.getPosition());
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction arg1) {

    }
}

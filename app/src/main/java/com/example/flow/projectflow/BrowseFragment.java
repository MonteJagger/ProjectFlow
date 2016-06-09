package com.example.flow.projectflow;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.widget.Button;
import android.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import 	android.widget.AutoCompleteTextView;

/**
 * Created by Thomas on 6/2/2016.
 */

public class BrowseFragment extends Fragment {
    //implements SearchView.OnQueryTextListener,SearchView.OnCloseListener
    private ListView mListView;
    private SearchView searchView;
    private DegreePlanAdapter mDbHelper;
    private Spinner degree_year;
    private String degree_Years[] = {"2014-2015","2013-2014","2012-2013"};
    ArrayAdapter<String> adapterDegreeYear;

    private TextView degreeplanText;
    private TextView yearText;
    private TextView majorText;
    private AutoCompleteTextView searchView2;

    class ItemAutoTextAdapter extends CursorAdapter
            implements android.widget.AdapterView.OnItemClickListener  {

        private DegreePlanAdapter mDbHelper;

        public ItemAutoTextAdapter(DegreePlanAdapter dbHelper) {
            // Call the CursorAdapter constructor with a null Cursor.
            super(ApplicationContextProvider.getContext(), null);
            mDbHelper = dbHelper;
        }

        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (getFilterQueryProvider() != null) {
                return getFilterQueryProvider().runQuery(constraint);
            }
            Cursor cursor = mDbHelper.searchDegree(
                    (constraint != null ? constraint.toString() : "@@@@"));

            return cursor;
        }

        @Override
        public String convertToString(Cursor cursor) {
            final int columnIndex = cursor.getColumnIndexOrThrow("major");
            final String str = cursor.getString(columnIndex);
            return str;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final String text = convertToString(cursor);
            ((TextView) view).setText(text);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view =
                    inflater.inflate(android.R.layout.simple_dropdown_item_1line,
                            parent, false);
            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            // Get the cursor, positioned to the corresponding row in the result set
            Cursor cursor = (Cursor) listView.getItemAtPosition(position);

            // Get the state's capital from this row in the database.
            String major = cursor.getString(cursor.getColumnIndexOrThrow("major"));
            //majorText = (TextView) getActivity().findViewById(R.id.major);
            // Update the parent class's TextView
            //majorText.setText(major);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse,container,false);
        degree_year = (Spinner) view.findViewById(R.id.ddDegree);
        Button submitButton = (Button) view.findViewById(R.id.btnSubmit);
    //====== Initialize and set Adapter
        adapterDegreeYear = new ArrayAdapter<String>(ApplicationContextProvider.getContext(),R.layout.spinner_item, degree_Years);
        adapterDegreeYear.setDropDownViewResource(R.layout.spinner_dropdown_item);
        degree_year.setAdapter(adapterDegreeYear);

    // ======= Search degree Plans
        searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        //searchView.setOnQueryTextListener(this);
        //searchView.setOnCloseListener(this);

        mDbHelper = new DegreePlanAdapter(ApplicationContextProvider.getContext());
        mDbHelper.open();

        //Create degree Plans
        mListView = (ListView) view.findViewById(R.id.listView);
        mDbHelper.deleteDegree();
        mDbHelper.createDegree("Engineering","2014-2015","Computer Science");
        mDbHelper.createDegree("Engineering","2014-2015","Computer Engineering");
        mDbHelper.createDegree("Engineering","2013-2014","Software Engineering");
        mDbHelper.createDegree("Engineering","2014-2015","Aerospace Engineering");
        mDbHelper.createDegree("Engineering","2014-2015","Mechanical  Engineering");
        mDbHelper.createDegree("Engineering","2014-2015","Bio Engineering");
        mDbHelper.createDegree("Engineering","2014-2015","Petroleum Engineering");

        searchView2 = (AutoCompleteTextView) view.findViewById(R.id.searchView2);
        ItemAutoTextAdapter adapter = this.new ItemAutoTextAdapter(mDbHelper);
        searchView2.setAdapter(adapter);
        searchView2.setOnItemClickListener(adapter);
        searchView2.setThreshold(1);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }
/*
    private void showResults (String query) {
        Cursor cursor = mDbHelper.searchDegree((query != null ? query.toString() : "@@@@"));
        if (cursor == null) {
            //Toast.makeText(ApplicationContextProvider.getContext(), "No Results", Toast.LENGTH_SHORT).show();
        } else {
            // Specify the columns we want to display in the result
            String[] from = new String[] {
                    DegreePlanAdapter.KEY_DEPARTMENT,
                    DegreePlanAdapter.KEY_YEAR,
                    DegreePlanAdapter.KEY_MAJOR};

            // Specify the Corresponding layout elements where we want the columns to go
            int[] to = new int[] {
                    R.id.sdepartment,
                    R.id.syear,
                    R.id.smajor};

            // Create a simple cursor adapter for the definitions and apply them to the ListView
            SimpleCursorAdapter degrees = new SimpleCursorAdapter(ApplicationContextProvider.getContext(),R.layout.searchresults, cursor, from, to);
            mListView.setAdapter(degrees);

            // Define the on-click listener for the list items
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) mListView.getItemAtPosition(position);

                    String department = cursor.getString(cursor.getColumnIndexOrThrow("department"));
                    String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
                    String major = cursor.getString(cursor.getColumnIndexOrThrow("major"));

                    searchView.setQuery(major,false);
                    searchView.clearFocus();
                    //Check if the Layout already exists

                    LinearLayout degreeLayout = (LinearLayout) getActivity().findViewById(R.id.degreeLayout);
                    if(degreeLayout == null){
                        //Inflate the Degree Information View
                        LinearLayout leftLayout = (LinearLayout)getActivity().findViewById(R.id.leftLayout);
                        View DegreeInfo;
                        LayoutInflater inflater = (LayoutInflater)ApplicationContextProvider.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        DegreeInfo = inflater.inflate(R.layout.degreeplaninfo, leftLayout, false);
                        leftLayout.addView(DegreeInfo);
                    }

                 //Get References to the TextViews
                   // degreeplanText = (TextView) getActivity().findViewById(R.id.department);
                    //yearText = (TextView) getActivity().findViewById(R.id.year);
                   // majorText = (TextView) getActivity().findViewById(R.id.major);

                 // Update the parent class's TextView
                    //degreeplanText.setText(department);
                    //yearText.setText(year);
                    //majorText.setText(major);

                    //searchView.setQuery("",true);
                }
            });
        }

    }
*/
}

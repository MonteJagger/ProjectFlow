package com.example.flow.projectflow;

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.CursorWrapper;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.widget.SimpleCursorAdapter;
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
import android.widget.TextView;
import 	android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.flow.projectflow.R.xml.listview_item;

/**
 * Created by Thomas on 6/2/2016.
 */

public class BrowseFragment extends Fragment {

    private ListView mListView;
    private SearchView searchView;
    private DegreePlanAdapter mDbHelper;
    private Spinner year_Spinner;
    private ArrayList<String> degree_Years = new ArrayList<>();
    ArrayAdapter<String> adapterDegreeYear;



    private AutoCompleteTextView autoComplete;
    private String empty = " ---------------------------------------------------- ";


    class SimpleCursorAdapter extends android.widget.SimpleCursorAdapter implements AdapterView.OnItemClickListener {
        private DegreePlanAdapter mDbHelper;

        public SimpleCursorAdapter(Context context, int layout, MatrixCursor c, String[] from, int[] to, int flag  ) {
            // Call the CursorAdapter constructor with a null Cursor.
            super( context, layout,  c,  from, to,  flag );
            //mDbHelper = dbHelper;
        }

        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (getFilterQueryProvider() != null) {
                return getFilterQueryProvider().runQuery(constraint);
            }
            String link;
            String[] columnNames = {BaseColumns._ID, "department", "year", "major"};
            MatrixCursor c = new MatrixCursor(columnNames);
            MatrixCursor cYear = new MatrixCursor(columnNames);

            if (degree_Years.isEmpty()) {
                degree_Years.clear();
                degree_Years.add(empty);
            }
            if (autoComplete == null) {
                degree_Years.clear();
              //  degree_Years.add(empty);
            }
            if (constraint != null) {
                try {
                    String search = "?search=" + constraint;
                    link = "http://flowbyvoyu.com/getDegree.php" + search;
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String jsonStr = "";
                    // output ["query", ["n0", "n1", ..], ["d0", "d1", ..]]
                    jsonStr = bufferedReader.readLine();
                    JSONArray json = new JSONArray(jsonStr);


                    //Toast.makeText(ApplicationContextProvider.getContext(), json.toString(), Toast.LENGTH_SHORT).show();
                    JSONObject degree = json.getJSONObject(0);

                    String departments = degree.getString("department");
                    String years = degree.getString("year");
                    String majors = degree.getString("major");


                    c.newRow().add(0).add(departments).add(years).add(majors);
                    for (int i = 1; i < json.length(); i++) {
                        JSONObject degreeYears = json.getJSONObject(i);
                        String dYears = degreeYears.getString("year");
                        degree_Years.add(dYears);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ApplicationContextProvider.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

                c.moveToFirst();

            }
            return c;
        }

        @Override
        public String convertToString(Cursor cursor) {
            final int columnIndex = cursor.getColumnIndexOrThrow("major");
            final String str = cursor.getString(columnIndex);
            final int columnIndex2 = cursor.getColumnIndexOrThrow("department");
            final String str2 = cursor.getString(columnIndex2);

            final String cStr = str+ "//" + str2;

            return cStr;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final String text = convertToString(cursor);
            String[] parts = text.split("//");
            ((TwoLineListItem) view).getText1().setText(parts[0]);
            ((TwoLineListItem) view).getText2().setText(parts[1]);
        }

       // @Override
        public View newViewBinder(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(+R.xml.listview_item, parent, false);
            return view;
        }

        //@Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            // Get the cursor, positioned to the corresponding row in the result set
            //Toast.makeText(ApplicationContextProvider.getContext(), "!", Toast.LENGTH_SHORT).show();
            Cursor cursor = (Cursor) listView.getItemAtPosition(position);
            String text = convertToString(cursor);

            String[] parts = text.split("//");
            autoComplete.setText(parts[0]);

            year_Spinner.setAdapter(adapterDegreeYear);
            degree_Years.clear();
            //set constraint to major
            CharSequence constraint = cursor.getString(cursor.getColumnIndexOrThrow("major"));


            if (degree_Years.isEmpty()) {
                degree_Years.clear();
               // degree_Years.add(empty);
            }

            /*
            //search Year using major as constraint
            Cursor mCursor = mDbHelper.searchYear((constraint != null ? constraint.toString() : "@@@@"));

            //get number of year plans for major
            int numYears = mCursor.getColumnCount();
            for (int i = 0; i < numYears; i++)
            {

            }
            for (int i = 0; i < json.length(); i++) {
                    JSONObject degree = json.getJSONObject(i);
                    String years = degree.getString("year");
                    c.newRow().add(i).add(years);
                    String year = c.getString(c.getColumnIndexOrThrow("year"));
                    degree_Years.add(year);

                    //c.moveToNext();
                }

            */
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse,container,false);
        year_Spinner = (Spinner) view.findViewById(R.id.ddDegree);
        Button submitButton = (Button) view.findViewById(R.id.btnSubmit);
    //====== Initialize and set Adapter
        adapterDegreeYear = new ArrayAdapter<String>(ApplicationContextProvider.getContext(), +R.xml.spinner_item, degree_Years);
        adapterDegreeYear.setDropDownViewResource(+R.xml.spinner_dropdown_item);
        year_Spinner.setAdapter(adapterDegreeYear);
    // ======= Search degree Plans

        AutoCompleteTextView actv = new AutoCompleteTextView(ApplicationContextProvider.getContext());
        actv.setThreshold(1);
        String[] from = { "department", "year", "major" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter a = new SimpleCursorAdapter(ApplicationContextProvider.getContext(), +R.xml.twolinelist_item, null, from, to, 0);
        a.setStringConversionColumn(3);
        actv.setAdapter(a);

        degree_Years.add(empty);

        autoComplete = (AutoCompleteTextView) view.findViewById(R.id.autoComplete);
        autoComplete.setAdapter(a);
        autoComplete.setOnItemClickListener(a);
        autoComplete.setThreshold(1);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }




}

package com.alifesoftware.httpautocompletetextviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.alifesoftware.httpautocompletetextview.DelayAutoCompleteTextView;
import com.alifesoftware.httpautocompletetextview.GenericAutoCompleteAdapter;
import com.alifesoftware.httpautocompletetextview.ISearchResult;

/**
 * Activity to demonstrate the use of HttpAutoCompleteTextView
 * Library
 */
public class MainActivity extends AppCompatActivity {
    // Threshold - Minimum number of characters
    // after which we can start auto completion process
    private final int THRESHOLD = 2;

    // StockSearchConstraintObject
    private StockSearchConstraintObject searchConstraintObject = new StockSearchConstraintObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a DelayAutoCompleteTextView
        final DelayAutoCompleteTextView searchTermTextView =
                (DelayAutoCompleteTextView) findViewById(R.id.et_book_title);

        // Set the Threshold (optional)
        searchTermTextView.setThreshold(THRESHOLD);

        // Set a Adapter for displaying AutoComplete Results
        searchTermTextView.setAdapter(new GenericAutoCompleteAdapter(this, searchConstraintObject));

        // Set the indicator to show progress spinner
        searchTermTextView.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));

        // Click Listener for result items
        searchTermTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ISearchResult dataObject = (ISearchResult) adapterView.getItemAtPosition(position);
                searchTermTextView.setText(dataObject.getTextOne());
            }
        });
    }
}

package com.alifesoftware.httpautocompletetextview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anuj Saluja on 8/6/16.
 *
 * This class implements an adapter that shows results
 * for AutoCompleteTextView (DelayAutoCompleteTextView)
 */

public class GenericAutoCompleteAdapter extends BaseAdapter
                                        implements Filterable {
    // Default value for Max Result
    private static final int MAX_RESULTS = 20;

    // Value for max result used to override MAX_RESULT
    private int mMaxResults = MAX_RESULTS;

    // Context for the Adapter
    private Context mContext;

    // List of Results for the Search Constraint
    private List<ISearchResult> mResultList = new ArrayList<>();

    // ISearchConstraint Object
    private ISearchConstraint mSearchConstraintObject;

    /**
     * Default constructor for the Adapter
     *
     * @param context
     */
    public GenericAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    /**
     * Constructor for the Adapter
     *
     * @param context
     * @param searchConstraintObject
     */
    public GenericAutoCompleteAdapter(Context context, ISearchConstraint searchConstraintObject) {
        mContext = context;
        mSearchConstraintObject = searchConstraintObject;
    }

    /**
     * Method to set the search constraint object
     * for the adapter
     *
     * @param searchConstraintObject
     */
    public void setSearchConstraintObject(ISearchConstraint searchConstraintObject) {
        mSearchConstraintObject = searchConstraintObject;
    }

    /**
     * Method to set the maximum number od results to
     * be displayed
     *
     * @param maxResults
     */
    public void setMaxResults(final int maxResults) {
        mMaxResults = maxResults;
    }

    /**
     * Overridden method to get the count of items
     * for the adapter
     *
     * @return
     */
    @Override
    public int getCount() {
        return mResultList.size();
    }

    /**
     * Overridden method to get item at selected
     * index in the adapter
     *
     * @param index
     * @return
     */
    @Override
    public Object getItem(int index) {
        return mResultList.get(index);
    }

    /**
     * Overridden method to get item ID at
     * specified position in the adapter
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Overridden getView to show the results in the
     * adapter
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the drop down item for the view
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        // Populate the data for the view
        ((TextView) convertView.findViewById(R.id.text1))
                .setText(((ISearchResult)getItem(position)).getTextOne());
        ((TextView) convertView.findViewById(R.id.text2))
                .setText(((ISearchResult)getItem(position)).getTextTwo());
        return convertView;
    }

    /**
     * Overridden method to get the filter required
     * for implementation of Filterable
     *
     * @return
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            // Note: performFiltering is invoked in a worker thread
            // to filter the data according to the constraint.
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<ISearchResult> dataObject = findDataForConstraint(mContext, constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = dataObject;
                    filterResults.count = dataObject.size();
                }
                return filterResults;
            }

            // Update the list with results
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResultList = (List<ISearchResult>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    /**
     * Method to find data for the searc constrint.
     *
     * Note: This method is called on a worker thread
     * by the framework, so no need to spawn another
     * thread here to make the network call
     *
     * @param context
     * @param searchTerm
     * @return
     */
    private List<ISearchResult> findDataForConstraint(Context context, String searchTerm) {
        if(mSearchConstraintObject == null) {
            throw new NullPointerException("ISearchConstraint implementation object cannot be null");
        }

        // Get the result list
        List<ISearchResult> resultList = mSearchConstraintObject.performSearch(searchTerm);

        // Apply the Max number of results
        if(resultList != null &&
                resultList.size() > mMaxResults) {
            return resultList.subList(0, mMaxResults);
        }

        return resultList;
    }
}

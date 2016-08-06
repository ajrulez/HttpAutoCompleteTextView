package com.alifesoftware.httpautocompletetextview;

import java.util.List;

/**
 * Created by Anuj Saluja on 8/6/16.
 *
 * This interface must be implemented to
 * perform the search for the search
 * constraint
 *
 */

public interface ISearchConstraint {
    /**
     * Method to be implemented to perform search
     * for the constraint.
     *
     * Note: This method is always called on a worker
     * thread, so a network call can be made here
     * synchronously
     *
     * @param constraint
     * @return
     */
    List<ISearchResult> performSearch(final String constraint);
}

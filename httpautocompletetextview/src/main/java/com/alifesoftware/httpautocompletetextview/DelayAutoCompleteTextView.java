package com.alifesoftware.httpautocompletetextview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

/**
 * Created by Anuj Saluja on 8/6/16.
 *
 * This class extends AutoCompleteTextView class and adds
 * some additional properties to override the default
 * behavior
 *
 */

public class DelayAutoCompleteTextView extends AutoCompleteTextView {
    // Message ID for Constraint (Search) Text Changed
    private static final int MESSAGE_TEXT_CHANGED = 100;

    // Auto Complete Delay - When user is typing, we don't want
    // to spam them with results, so wait for this time before
    // searching
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

    // Auto Complete Delay
    private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;

    // Progress Bar to indicate results are being searched
    private ProgressBar mLoadingIndicator;

    /**
     * Handler for performing Filer on the
     * AutoCompleteTextView
     *
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    /**
     * Constructor with Context and Attributes
     *
     * @param context
     * @param attrs
     */
    public DelayAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Method to set progress spinner that will be
     * displayed while we are waiting for search results
     *
     * @param progressBar
     */
    public void setLoadingIndicator(ProgressBar progressBar) {
        mLoadingIndicator = progressBar;
    }

    /**
     * Method to set and override the auto
     * complete delay. If this method is not called,
     * we use the default value for delay
     *
     * @param autoCompleteDelay
     */
    public void setAutoCompleteDelay(int autoCompleteDelay) {
        mAutoCompleteDelay = autoCompleteDelay;
    }

    /**
     * Overridden method to handle the filtering
     * process
     *
     * @param text
     * @param keyCode
     */
    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        // Show progress spinner
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), mAutoCompleteDelay);
    }

    /**
     * Overridden method that is called when
     * filtering is complete
     *
     * @param count
     */
    @Override
    public void onFilterComplete(int count) {
        // Hide the progress spinner
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.GONE);
        }

        // Update the count of items found
        super.onFilterComplete(count);
    }
}

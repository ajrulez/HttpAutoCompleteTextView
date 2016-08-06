package com.alifesoftware.httpautocompletetextviewapp;

import com.alifesoftware.httpautocompletetextview.ISearchResult;

/**
 * Created by Anuj Saluja on 8/6/16.
 *
 * This class implements search result interface
 * for AutoCompleteTextView
 *
 */

public class StockResultObject implements ISearchResult {
    // Company Name
    private String companyName;

    // Ticker and Exchange
    private String tickerAndExchange;

    /**
     * Method to get the first text from this object
     * @return
     */
    @Override
    public String getTextOne() {
        return companyName;
    }

    /**
     * Method to get the second text for this object
     * @return
     */
    @Override
    public String getTextTwo() {
        return tickerAndExchange;
    }

    /**
     * Method to set company name
     * @param name
     */
    public void setCompanyName(final String name) {
        companyName = name;
    }

    /**
     * Method to set ticker and exchange
     * @param ticker
     * @param exchange
     */
    public void setTickerAndExchange(final String ticker, final String exchange) {
        tickerAndExchange = ticker + " (" + exchange + ")";
    }
}

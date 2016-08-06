package com.alifesoftware.httpautocompletetextviewapp;

import com.alifesoftware.httpautocompletetextview.ISearchConstraint;
import com.alifesoftware.httpautocompletetextview.ISearchResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Anuj Saluja on 8/6/16.
 *
 * This class implements ISearchConstraint to
 * search for stocks, parse the result and
 * return a list
 *
 */

public class StockSearchConstraintObject implements ISearchConstraint {
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
     public List<ISearchResult> performSearch(final String constraint) {
         String url = "http://d.yimg.com/aq/autoc?query=replaceme&region=US&lang=en-US&callback=YAHOO.util.ScriptNodeDataSource.callbacks";
         url = url.replace("replaceme", constraint);

         List<ISearchResult> datalist = new ArrayList<>();

         OkHttpClient client = OkHttpFactory.getOkHttpClient();

         Request request = new Request.Builder()
                 .url(url)
                 .build();

         try {
             Response response = client.newCall(request).execute();
             if(response != null &&
                     response.body() != null) {
                 final String responseString = response.body().string();
                 if(responseString.startsWith("YAHOO.util.ScriptNodeDataSource.callbacks(")) {
                     final String jsonStringBegin = responseString.replace("YAHOO.util.ScriptNodeDataSource.callbacks(", "");
                     if(jsonStringBegin.endsWith("}]}});")) {
                         final String jsonString = jsonStringBegin.replace("}]}});", "}]}}");

                         final JSONObject jsonObject = new JSONObject(jsonString);
                         final JSONObject resultSetObject = jsonObject.optJSONObject("ResultSet");

                         if(resultSetObject != null) {
                             final JSONArray resultArray = resultSetObject.optJSONArray("Result");

                             if(resultArray != null &&
                                     resultArray.length() > 0) {
                                 for(int ndx = 0; ndx < resultArray.length(); ndx++) {
                                     JSONObject stock = resultArray.getJSONObject(ndx);

                                     if(stock != null) {
                                         final String symbol = stock.optString("symbol", "");
                                         final String name = stock.optString("name", "");
                                         final String exchange = stock.optString("exchDisp", "");

                                         if(! symbol.isEmpty() &&
                                                 ! name.isEmpty() &&
                                                 ! exchange.isEmpty()) {
                                             StockResultObject stockObj = new StockResultObject();
                                             stockObj.setCompanyName(name);
                                             stockObj.setTickerAndExchange(symbol, exchange);

                                             datalist.add(stockObj);
                                         }
                                     }
                                 }
                             }
                         }
                     }
                 }
             }
         }
         catch (Exception e) {
             e.printStackTrace();
         }

         return datalist;
     }
}

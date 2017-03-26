package com.unicornheight.duressrecodenigeria;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by deboajagbe on 3/4/17.
 */

public class NetworkAccess {


    final static String BASE_URL =
            "https://pwcstaging.herokuapp.com/";

    final static String DURESS_BASE_URL =
            "http://pwcdevcenter.herokuapp.com/";


    final static String PARAM_QUERY = "q";

    final static String PARAM_SORT = "sort";

    final static String sortBy = "stars";



    public static URL buildBaseUrl(String jointQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
               // .appendQueryParameter(PARAM_QUERY, jointQuery)
               // .appendQueryParameter(PARAM_SORT, sortBy)
               // .appendQueryParameter(BuildConfig.TOKEN_API_KEY, null)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildDuressUrl(String jointQuery) {
        Uri builtUri = Uri.parse(DURESS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, jointQuery)
              //  .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


//    public static String getResponseFromHttpUrl(URL accessUrl) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) accessUrl.openConnection();
//        try {
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
}

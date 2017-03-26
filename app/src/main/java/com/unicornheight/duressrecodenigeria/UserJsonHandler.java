package com.unicornheight.duressrecodenigeria;


import android.content.ContentValues;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deboajagbe on 3/5/17.
 */

class UserJsonHandler {



    public static String[] getUserStringsFromJson(Context context, String userJsonStr)
            throws JSONException {


        final String OWM_LIST = "items";

        final String OWM_NAME = "login";

        final String OWM_PROFILE_URL = "url";

        final String OWM_ID = "id";

        final String OWM_PROFILE_PICTURE = "avatar_url";

        final String OWM_MESSAGE_CODE = "cod";

         String user_name;
         String user_url;
         String user_images;
         int user_id;


        String[] parsedUserData = null;

        JSONObject userJson = new JSONObject(userJsonStr);



        if (userJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = userJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray userArray = userJson.getJSONArray(OWM_LIST);

        parsedUserData = new String[userArray.length()];


        for (int i = 0; i < userArray.length(); i++) {

            JSONObject gitRecord = userArray.getJSONObject(i);


            user_name = gitRecord.getString(OWM_NAME);

            user_images = gitRecord.getString(OWM_PROFILE_PICTURE);

            user_url = gitRecord.getString(OWM_PROFILE_URL);

            user_id = gitRecord.getInt(OWM_ID);

            parsedUserData[i] = user_name + "-" + user_url + "-" +  user_images ;

        }

        return parsedUserData;

    }

}

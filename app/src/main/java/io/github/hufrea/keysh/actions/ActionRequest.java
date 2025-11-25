package io.github.hufrea.keysh.actions;

import android.content.Context;
import android.util.Log;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;

public class ActionRequest {
    public static void httpRequest(Context context, String[] args) {
        try {
            URL url = new URL(args[2]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(args[1]);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            // Consume the response so the request can fully close
            try (InputStream is = connection.getInputStream()) { }

            if (responseCode >= 200 && responseCode < 300) {
                Log.d("httpRequest", "Request successful: " + responseCode);
            } else {
                Log.e("httpRequest", "Request failed: " + responseCode);
            }

        } catch (Exception e) {
            Log.e("httpRequest", "Error: " + e.getMessage(), e);
        }
    }
}

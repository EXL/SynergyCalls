package ru.exlmoto.synergycalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallReceiver extends PhonecallReceiver {

    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        private final String a_url;
        private final String value;
        private final Date time;
        private final Date timeEnd;
        private final String time_s;
        private final String timeEnd_s;

        public PostClass(Context c,
                         final String a_url,
                         final String value,
                         final Date time,
                         final Date timeEnd) {
            this.context = c;
            this.a_url = a_url;
            this.value = value;
            this.time = time;
            this.timeEnd = timeEnd;

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            time_s = df.format(time);
            if (timeEnd != null) {
                timeEnd_s = df.format(timeEnd);
            } else {
                timeEnd_s = "";
            }


            Log.d(APP_TAG, "POST: " + this.a_url + " " + this.value + " " + this.time + " " + this.timeEnd);
        }
        protected void onPreExecute() { }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(a_url);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = "phone=" + value + ";";
                urlParameters += "time=" + time_s + ";";
                if (timeEnd != null) {
                    urlParameters += "time_end=" + timeEnd_s;
                }
                connection.setRequestMethod("POST");
                // connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                // connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator")  + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                br.close();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    public static final String APP_TAG = "CallReceiver";

    public static String inUrl = "http://exlmoto.ru";
    public static String inEUrl = "http://exlmoto.ru";
    public static String outUrl = "http://exlmoto.ru";
    public static String outEUrl = "http://exlmoto.ru";
    public static String missUrl = "http://exlmoto.ru";

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Log.d(APP_TAG, "Incoming: " + number);

        new PostClass(ctx, inUrl, number, start, null).execute();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d(APP_TAG, "Outgoing: " + number);

        new PostClass(ctx, outUrl, number, start, null).execute();
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(APP_TAG, "Incoming end: " + number);

        new PostClass(ctx, inEUrl, number, start, end).execute();
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(APP_TAG, "Outgoing end: " + number);

        new PostClass(ctx, outEUrl, number, start, end).execute();
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d(APP_TAG, "Missed: " + number);

        new PostClass(ctx, missUrl, number, start, null).execute();
    }
}
package ru.exlmoto.synergycalls;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallReceiver extends PhonecallReceiver {

    private URL url;
    private  HttpURLConnection conn;

    public static final String APP_TAG = "CallReceiver";

    public static String inUrl = "http://exlmoto.ru";
    public static String inEUrl = "http://exlmoto.ru";
    public static String outUrl = "http://exlmoto.ru";
    public static String outEUrl = "http://exlmoto.ru";
    public static String missUrl = "http://exlmoto.ru";
    
    private void sendPostToServer(String a_url,
                                  String value,
                                  Date time,
                                  Date timeEnd) {
        try {
            url=new URL(a_url);

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            String time_s = df.format(time);
            String timeEnd_s = "";

            if (timeEnd != null) {
                timeEnd_s = df.format(timeEnd);
            }

            String param = "phone" + "=" + URLEncoder.encode(value, "UTF-8");
            param += "&time" + "=" + URLEncoder.encode(time_s, "UTF-8");

            if (timeEnd != null) {
                param += "&timeEnd" + "=" + URLEncoder.encode(timeEnd_s, "UTF-8");
            }

            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
        }

        catch(MalformedURLException ex){
            Log.d(APP_TAG, "MalformedURLException: " + ex.toString());
        }

        catch(IOException ex){
            Log.d(APP_TAG, "IOException: " + ex.toString());
        }
    }
    
    private void sendPostToServerInThread(final String a_url,
                                          final String value,
                                          final Date time,
                                          final Date timeEnd) {

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sendPostToServer(a_url, value, time, timeEnd);
            }
        });

        myThread.start();
    }

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Log.d(APP_TAG, "Incoming: " + number);

        sendPostToServerInThread(inUrl, number, start, null);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d(APP_TAG, "Outgoing: " + number);

        sendPostToServerInThread(outUrl, number, start, null);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(APP_TAG, "Incoming end: " + number);

        sendPostToServerInThread(inEUrl, number, start, end);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(APP_TAG, "Outgoing end: " + number);

        sendPostToServerInThread(outEUrl, number, start, end);
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d(APP_TAG, "Missed: " + number);

        sendPostToServerInThread(missUrl, number, start, null);
    }
}
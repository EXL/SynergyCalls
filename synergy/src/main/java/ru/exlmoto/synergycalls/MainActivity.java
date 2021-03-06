/************************************************************************************
** The MIT License (MIT)
**
** Copyright (c) 2016 S. "EXL" KLS
**
** Permission is hereby granted, free of charge, to any person obtaining a copy
** of this software and associated documentation files (the "Software"), to deal
** in the Software without restriction, including without limitation the rights
** to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
** copies of the Software, and to permit persons to whom the Software is
** furnished to do so, subject to the following conditions:
**
** The above copyright notice and this permission notice shall be included in all
** copies or substantial portions of the Software.
**
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
** SOFTWARE.
************************************************************************************/

package ru.exlmoto.synergycalls;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final String LOG_TAG = "SynergyActivity";

    public static class SynergySettings {

        public static String in = "http://exlmoto.ru";
        public static String out = "http://exlmoto.ru";
        public static String inE = "http://exlmoto.ru";
        public static String outE = "http://exlmoto.ru";
        public static String miss = "http://exlmoto.ru";
    }

    private EditText eIn = null;
    private EditText eInEnd = null;
    private EditText eOut = null;
    private EditText eOutEnd = null;
    private EditText eMiss = null;

    private Button button;

    public static SharedPreferences settingStorage = null;

    public void fillSettingsByLayout() {
        SynergySettings.in = eIn.getText().toString();
        SynergySettings.inE = eInEnd.getText().toString();
        SynergySettings.out = eOut.getText().toString();
        SynergySettings.outE = eOutEnd.getText().toString();
        SynergySettings.miss = eMiss.getText().toString();
    }

    public void fillLayoutBySettings() {
        eIn.setText(SynergySettings.in);
        eInEnd.setText(SynergySettings.inE);
        eOut.setText(SynergySettings.out);
        eOutEnd.setText(SynergySettings.outE);
        eMiss.setText(SynergySettings.miss);
    }

    private void readSettings() {
        SynergySettings.in = settingStorage.getString("in", "http://exlmoto.ru");
        SynergySettings.inE = settingStorage.getString("inE", "http://exlmoto.ru");
        SynergySettings.out = settingStorage.getString("out", "http://exlmoto.ru");
        SynergySettings.outE = settingStorage.getString("outE", "http://exlmoto.ru");
        SynergySettings.miss = settingStorage.getString("miss", "http://exlmoto.ru");
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void writeSettings() {
        Log.d(LOG_TAG, "Write Settings!");

        fillSettingsByLayout();

        SharedPreferences.Editor editor = settingStorage.edit();

        editor.putString("in", (SynergySettings.in != null && !SynergySettings.in.isEmpty()) ?
                SynergySettings.in : "http://exlmoto.ru");
        editor.putString("inE", (SynergySettings.inE != null && !SynergySettings.inE.isEmpty()) ?
                SynergySettings.inE : "http://exlmoto.ru");
        editor.putString("out", (SynergySettings.out != null && !SynergySettings.out.isEmpty()) ?
                SynergySettings.out : "http://exlmoto.ru");
        editor.putString("outE", (SynergySettings.outE != null && !SynergySettings.outE.isEmpty()) ?
                SynergySettings.outE : "http://exlmoto.ru");
        editor.putString("miss", (SynergySettings.miss != null && !SynergySettings.miss.isEmpty()) ?
                SynergySettings.miss : "http://exlmoto.ru");

        editor.commit();

        CallReceiver.updateValues(settingStorage);
    }

    private void initWidgets() {
        eIn = (EditText) findViewById(R.id.editTextIn);
        eInEnd = (EditText) findViewById(R.id.editTextInEnd);
        eOut = (EditText) findViewById(R.id.editTextOut);
        eOutEnd = (EditText) findViewById(R.id.editTextOutEnd);
        eMiss = (EditText) findViewById(R.id.editTextMissing);

        button = (Button) findViewById(R.id.button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, SynergyService.class));

        settingStorage = getSharedPreferences("ru.exlmoto.synergycall", MODE_PRIVATE);
        // Check the first run
        // boolean firstRun = false;
        if (settingStorage.getBoolean("firstRun", true)) {
            // firstRun = true;
            settingStorage.edit().putBoolean("firstRun", false).commit();
        } else {
            readSettings();
        }

        initWidgets();

        fillLayoutBySettings();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeSettings();
                Toast.makeText(v.getContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        writeSettings();

        super.onDestroy();
    }
}

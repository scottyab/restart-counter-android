package com.scottyab.android.restartcounter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

/*
    feature graphic
    //http://www.norio.be/android-feature-graphic-generator/?config=%7B%22background%22%3A%7B%22color%22%3A%22%23933a7b%22%2C%22gradient%22%3A%7B%22type%22%3A%22radial%22%2C%22radius%22%3A%22600%22%2C%22angle%22%3A%22vertical%22%2C%22color%22%3A%22%23000000%22%7D%7D%2C%22title%22%3A%7B%22text%22%3A%22Restart%20Counter%22%2C%22color%22%3A%22%23ffffff%22%2C%22size%22%3A200%2C%22font%22%3A%7B%22family%22%3A%22Lato%22%2C%22effect%22%3A%22bold%22%7D%7D%2C%22subtitle%22%3A%7B%22text%22%3A%22Simple!%22%2C%22color%22%3A%22%23ffffff%22%2C%22size%22%3A100%2C%22font%22%3A%7B%22family%22%3A%22Lato%22%2C%22effect%22%3A%22italic%22%7D%7D%2C%22image%22%3A%7B%22position%22%3A%22222%22%2C%22file%22%3A%7B%22webkitRelativePath%22%3A%22%22%2C%22lastModifiedDate%22%3A%222014-10-01T15%3A42%3A22.000Z%22%2C%22name%22%3A%22web_hi_res_512.png%22%2C%22type%22%3A%22image%2Fpng%22%2C%22size%22%3A92691%7D%7D%7D
 */
public class MainActivity extends Activity {

    private TextView countTV;
    private TextView introTV;
    private Button resetButton;

    public static final String PREF_NO_OF_RESTARTS = "PREF_NO_OF_RESTARTS";
    public static final String PREF_LAST_RESTART = "PREF_LAST_RESTART";

    private EventBus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus = EventBus.getDefault();
        mBus.register(this);
        setContentView(R.layout.activity_main);

        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    private void initView() {
        countTV = (TextView)findViewById(R.id.countTV);
        introTV = (TextView)findViewById(R.id.introTV);
        resetButton = (Button)findViewById(R.id.reset);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            String formattedInstallDate = UITextFormatter.formatDateTime(info.firstInstallTime);

            long lastRestart = PreferenceManager.getDefaultSharedPreferences(this).getLong(PREF_LAST_RESTART, 0);
            String formattedlastRestart = "N/A";
            if (lastRestart>0){
                formattedlastRestart =UITextFormatter.formatDateTime(lastRestart);
            }

            introTV.setText(getString(R.string.intro, formattedInstallDate, formattedlastRestart));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        updateNoRestarts();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset
                updateCount(getApplicationContext(), 0, 0);
                Toast.makeText(getApplicationContext(), "Counter reset", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateCount(Context context, int newCount, long timestamp) {
        SharedPreferences.Editor editor =PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(MainActivity.PREF_NO_OF_RESTARTS, newCount);

        editor.putLong(MainActivity.PREF_LAST_RESTART,timestamp);
        editor.apply();
        EventBus.getDefault().post(new EventCountUpdated());

    }

    private void updateNoRestarts(){
        int count = PreferenceManager.getDefaultSharedPreferences(this).getInt(PREF_NO_OF_RESTARTS, 0);
        countTV.setText(count+"");
    }


    public void onEventMainThread(final EventCountUpdated eventCountUpdated) {
        updateNoRestarts();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

}

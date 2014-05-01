package dk.homestead.sw803f14.testapp1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dk.homestead.sw803f14.service.ISunWukongService;
import dk.homestead.sw803f14.service.SunWukongService;


public class MainActivity extends Activity {

    private ISunWukongService swService;

    private Button _getGlobalMessageBtn;
    private Button _setGlobalMessageBtn;
    private EditText _globalMsgEditText;
    private EditText _appLog;

    private final ServiceConnection swConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            swService = ISunWukongService.Stub.asInterface(iBinder);
            Toast.makeText(getApplicationContext(), "Connected to SunWukongService!", Toast.LENGTH_SHORT).show();
            _setGlobalMessageBtn.setEnabled(true);
            _getGlobalMessageBtn.setEnabled(true);
            _globalMsgEditText.setEnabled(true);
            try {
                _globalMsgEditText.setText(swService.getGlobalMessage());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity:swConnection:onServiceDisconnected", "SunWukongService disconnected unexpectedly.");
            _setGlobalMessageBtn.setEnabled(false);
            _getGlobalMessageBtn.setEnabled(false);
            _globalMsgEditText.setEnabled(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _getGlobalMessageBtn = (Button)findViewById(R.id.getGlobalMessageButton);
        _setGlobalMessageBtn = (Button)findViewById(R.id.setGlobalMessageButton);
        _globalMsgEditText = (EditText)findViewById(R.id.globalMessageEditText);
        _appLog = (EditText)findViewById(R.id.logEditText);

        _setGlobalMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    swService.setGlobalMessage(_globalMsgEditText.getText().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        _getGlobalMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    _globalMsgEditText.setText(swService.getGlobalMessage());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        _appLog.append("Loading...");

        // Bind to service.
        Log.d("TestApp:MainActivity:onCreate", "Connecting to service.");
        Toast.makeText(getApplicationContext(), "Connecting to service...", Toast.LENGTH_SHORT).show();
        bindService(new Intent(getApplicationContext(), SunWukongService.class), swConnection, Context.BIND_AUTO_CREATE);

        _appLog.append("onCreate done. May be waiting for async tasks.");
    }


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
}

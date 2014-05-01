package dk.homestead.sw803f14.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class SunWukongService extends Service {

    private String _globalMessage = "NONE";

    private final ISunWukongService.Stub mBinder = new ISunWukongService.Stub() {
        @Override
        public void setGlobalMessage(String msg) throws RemoteException {
            Log.d("SunWukongService:setGlobalMessage", "Changing global message to " + msg);
            _globalMessage = msg;
        }

        @Override
        public String getGlobalMessage() throws RemoteException {
            return _globalMessage;
        }
    };

    public SunWukongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

package dk.homestead.sw803f14.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SunWukongService extends Service {

    private String _globalMessage = "NONE";

    private List<String> clients = new ArrayList<>();

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

        @Override
        public void retrieveBlock(int blockId, final ISunWukongTransferListener listener) throws RemoteException {
            Log.d("SunWukongService:retrieveBlock", "Received request for block " + blockId + ".");
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    try {
                        listener.onComplete("/var/lib/fuck-off");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            };

            Timer t = new Timer();
            t.schedule(tt, 10000);
        }

        @Override
        public void registerClient(String name) throws RemoteException {
            clients.add(name);
        }
    };

    public SunWukongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

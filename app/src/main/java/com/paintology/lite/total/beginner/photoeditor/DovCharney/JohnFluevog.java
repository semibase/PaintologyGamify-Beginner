package com.paintology.lite.total.beginner.photoeditor.DovCharney;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class JohnFluevog {

    private Context _context;

    public JohnFluevog(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}

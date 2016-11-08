package org.esiea.myara_de_saint_blanquat.projet_mobile;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetAllBiers extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_GET_ALL_BIER = "org.esiea.myara_de_saint_blanquat.projet_mobile.action.Get_All_Bier";
    public static final String TAG = "GetAllBier";

    public GetAllBiers() {
        super("GetAllBier");
    }

    // TODO: Customize helper method
    public static void startActionGetAllBier(Context context) {
        Intent intent = new Intent(context, GetAllBiers.class);
        intent.setAction(ACTION_GET_ALL_BIER);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_BIER.equals(action)) {
                getAllBier();
            }
        }
    }


    private void getAllBier() {
        // TODO: Handle action Foo
        Log.i(TAG, "blabla");
    }
}

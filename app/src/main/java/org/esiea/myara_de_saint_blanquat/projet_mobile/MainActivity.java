package org.esiea.myara_de_saint_blanquat.projet_mobile;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private DatePickerDialog dpd_dialog;

    private  RecyclerView rv_biere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
        setContentView(R.layout.activity_main);
        Button btn_button = (Button) findViewById(R.id.btn_button);
        rv_biere = (RecyclerView) findViewById(R.id.rv_biere);
        rv_biere.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_biere.setAdapter(new BiersAdapter(getBiersFromFile()));

        btn_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Run", Toast.LENGTH_LONG).show();
            }
        });

        final TextView  tv_Hello_World = (TextView) findViewById(R.id.tv_Hello_World);
        tv_Hello_World.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hello_World", Toast.LENGTH_LONG).show();
                dpd_dialog.show();
            }
        });

        dpd_dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tv_Hello_World.setText("Date: " + day+ "/"+month+"/"+year);
            }
        },1985,11,30);

        GetAllBiers.startActionGetAllBier(this);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.search)
            Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
        else if(item.getItemId()==R.id.test)
            Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_2, menu);
        return true;
    }



    public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    public class BierUpdate extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent){
            Log.d(TAG, intent.getAction());
        }
    }

    class BierReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ((BiersAdapter)rv_biere.getAdapter()).setNewBiers(getBiersFromFile());
        }
    }

    class BiersAdapter extends RecyclerView.Adapter<BierHolder> {

        private JSONArray biers;

        public BiersAdapter(JSONArray biers) {
            this.biers = biers;
        }

        public void setNewBiers(JSONArray biers){
            this.biers=biers;
        }
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.rv_bier_element, parent, false);
            return new BierHolder(itemView);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                holder.name.setText(biers.getJSONObject(position).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biers.length();
        }
    }
    public JSONArray getBiersFromFile(){
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        } catch(IOException e){
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }
    public class BierHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public BierHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
        }
    }

}

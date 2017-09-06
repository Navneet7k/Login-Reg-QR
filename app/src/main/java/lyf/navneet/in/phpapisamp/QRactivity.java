package lyf.navneet.in.phpapisamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lyf.navneet.in.phpapisamp.adapter.DataAdapter;

//implementing onclicklistener
public class QRactivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    private Button buttonScan,buttonGen;
    private TextView textViewName, textViewAddress;
    private ImageView imageView;
    private ArrayList<JSONObject> jsARR=new ArrayList<>();
    private DataAdapter adapter;
    private RecyclerView recyclerView;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_main);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonGen = (Button) findViewById(R.id.buttonGen);
//        textViewName = (TextView) findViewById(R.id.textViewName);
//        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
//        imageView=(ImageView) findViewById(R.id.qr_image);

        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
//        buttonGen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(QRactivity.this,GenerateQR.class));
//            }
//        });
        buttonScan.setOnClickListener(this);
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    JSONArray contacts = obj.getJSONArray("abcd");
                    for (int i=0;i<contacts.length();i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        jsARR.add(c);
                    }
                    adapter=new DataAdapter(jsARR,this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
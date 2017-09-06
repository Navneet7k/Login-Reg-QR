package lyf.navneet.in.phpapisamp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lyf.navneet.in.phpapisamp.model.InnerModel;
import lyf.navneet.in.phpapisamp.model.OuterModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Gson gson;
    private String json;
    private Button button,login;
    private EditText ed_name,ed_pw,ed_email;
    private ProgressDialog pd;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        login=(Button)findViewById(R.id.login);
        ed_name=(EditText) findViewById(R.id.name);
        ed_pw=(EditText) findViewById(R.id.password);
        ed_email=(EditText) findViewById(R.id.email);
        db = new SQLiteHandler(getApplicationContext());

        boolean isLogged=PreferencesHelper.getInstance(MainActivity.this).isLoggedIn();
        if (isLogged){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                Pair<View, String> p1 = Pair.create((View)login, "login");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this,p1);
                startActivity(intent,options.toBundle());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Please Wait..");
                pd.show();
                String na,pw,em;
                na=ed_name.getText().toString();
                pw=ed_pw.getText().toString();
                em=ed_email.getText().toString();
                if (!na.isEmpty() && !pw.isEmpty() && !em.isEmpty()) {
                    loadJSON(na, pw, em);
                }
            }
        });
    }

    private void loadJSON(String na,String pw,String em){

        Map<String, String> jsonObject = new HashMap<>();
            jsonObject.put("name",na);
            jsonObject.put("email",em);
            jsonObject.put("password",pw);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102/android_login_api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<OuterModel> call1=request.getPOJO(jsonObject);
        call1.enqueue(new Callback<OuterModel>() {
            @Override
            public void onResponse(Call<OuterModel> call, Response<OuterModel> response) {
                pd.dismiss();
                String name_sq,email_sq,uid_sq,created_sq;
                OuterModel outerModel=response.body();
                gson = new Gson();
                json = gson.toJson(outerModel);
                if (outerModel!=null) {
                    name_sq = outerModel.getInnerModel().getName();
                    email_sq = outerModel.getInnerModel().getEmail();
                    uid_sq = outerModel.getUid();
                    created_sq = outerModel.getInnerModel().getCreatedAt();
                    db.addUser(name_sq, email_sq, uid_sq, created_sq);

                    Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(
                            MainActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<OuterModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }


        });

    }

}

package lyf.navneet.in.phpapisamp;

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

import java.util.HashMap;
import java.util.Map;

import lyf.navneet.in.phpapisamp.model.OuterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button login,register;
    EditText name,password;
    private SQLiteHandler db;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        name=(EditText) findViewById(R.id.name);
        password=(EditText) findViewById(R.id.password);
        db = new SQLiteHandler(getApplicationContext());

        boolean isLogged=PreferencesHelper.getInstance(LoginActivity.this).isLoggedIn();
        if (isLogged){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                Pair<View, String> p1 = Pair.create((View)register, "register");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(LoginActivity.this,p1);
                startActivity(intent,options.toBundle());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String na,pw;
                na=name.getText().toString();
                pw=password.getText().toString();
                if (!na.isEmpty() && !pw.isEmpty()){
                    loadJson(na,pw);
                    pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Please Wait..");
                    pd.show();
                }
            }
        });
    }

    private void loadJson(String na, String pw) {
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("email",na);
        jsonObject.put("password",pw);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102/android_login_api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<OuterModel> call1=request.getPOJOLogin(jsonObject);
        call1.enqueue(new Callback<OuterModel>() {
            @Override
            public void onResponse(Call<OuterModel> call, Response<OuterModel> response) {
                pd.dismiss();
                String name_sq,email_sq,uid_sq,created_sq;
                OuterModel outerModel=response.body();
                if (outerModel!=null) {
                    name_sq = outerModel.getInnerModel().getName();
                    email_sq = outerModel.getInnerModel().getEmail();
                    uid_sq = outerModel.getUid();
                    created_sq = outerModel.getInnerModel().getCreatedAt();
                    PreferencesHelper.getInstance(LoginActivity.this).setLogin(true);
                    db.addUser(name_sq, email_sq, uid_sq, created_sq);
                    Intent intent = new Intent(
                            LoginActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<OuterModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }


        });

    }

}


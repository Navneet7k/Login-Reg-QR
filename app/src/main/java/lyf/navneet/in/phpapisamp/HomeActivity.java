package lyf.navneet.in.phpapisamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private TextView name,email;
    private SQLiteHandler db;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name=(TextView) findViewById(R.id.name);
        email=(TextView) findViewById(R.id.email);
        logout=(Button) findViewById(R.id.button);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        String name_sq = user.get("name");
        String email_sq = user.get("email");
        name.setText(name_sq);
        email.setText(email_sq);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        PreferencesHelper.getInstance(HomeActivity.this).setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

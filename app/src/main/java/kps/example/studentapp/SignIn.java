package kps.example.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {
private Button loginButton;
private EditText userEditText,passwordEditText;
private TextView signUpTextView,forgotPasswordTextView;
sqliteDB dbObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        dbObj=new sqliteDB(this);
        loginButton=findViewById(R.id.loginBtn);
        userEditText=findViewById(R.id.userNameEt);
        passwordEditText=findViewById(R.id.passwordEt);
        forgotPasswordTextView=findViewById(R.id.forGotPassTv);
        signUpTextView=findViewById(R.id.signUpTv);

        loginBtn();
        signUpTv();
        forgotPasswordTv();
    }
    public void loginBtn(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (userEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                        Toast.makeText(SignIn.this, "Enter username/password", Toast.LENGTH_SHORT).show();
                    } else if (dbObj.checkUserData(userEditText.getText().toString(), passwordEditText.getText().toString())) {
                        startActivity(new Intent(SignIn.this, Main.class));
                    } else {
                        Toast.makeText(SignIn.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignIn.this,Login.class));
    }
    public void signUpTv(){
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
    }
    public void forgotPasswordTv(){
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this, "Function Not implemented", Toast.LENGTH_SHORT).show();
             }
        });
    }
}
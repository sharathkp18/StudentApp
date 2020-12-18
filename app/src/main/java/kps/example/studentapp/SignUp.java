package kps.example.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private Button signUpButton;
    private EditText userEditText,phoneEditText,passwordEditText,confirmPassEditText;
    private TextView signInTextView;
    sqliteDB dbObj;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp.this,SignIn.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbObj=new sqliteDB(this);
        signUpButton=findViewById(R.id.signUpBtn);
        userEditText=findViewById(R.id.userNameEt);
        phoneEditText=findViewById(R.id.phoneNoEt);
        confirmPassEditText=findViewById(R.id.confPasswordEt);
        passwordEditText=findViewById(R.id.passwordEt);
        signInTextView=findViewById(R.id.signInTv);

        signInTv();
        signUpBtn();

    }
    public void signUpBtn(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEditText.getText().toString().isEmpty()||phoneEditText.getText().toString().isEmpty()
                ||passwordEditText.getText().toString().isEmpty()||confirmPassEditText.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else if(!passwordEditText.getText().toString().equals(confirmPassEditText.getText().toString())){
                    Toast.makeText(SignUp.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }else if(dbObj.addUser(userEditText.getText().toString(),phoneEditText.getText().toString()
                        ,passwordEditText.getText().toString())){
                    Toast.makeText(SignUp.this, "SignUP successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this,SignIn.class));
                }else{
                    Toast.makeText(SignUp.this, "SignUP failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void signInTv(){
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
    }
}
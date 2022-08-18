package com.example.q_mark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class SignUp extends AppCompatActivity {

    private boolean pass_show1=false;
    private boolean pass_show2=false;

    private String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText name,email,mobile,pass1,pass2;

    private AppCompatButton signup_button;
    private TextView login;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.sup_name);
        email= findViewById(R.id.sup_email);
        mobile= findViewById(R.id.sup_mobile);
        pass1= findViewById(R.id.sup_pass);
        pass2= findViewById(R.id.sup_pass_cnfrm);

        signup_button= findViewById(R.id.sup_button);
        login=findViewById(R.id.login_page_back);
        progressDialog=new ProgressDialog(this);
        final ImageView pass1_show_img = findViewById(R.id.show_sup_pass);
        final ImageView pass2_show_img = findViewById(R.id.show_sup_pass2);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        pass1_show_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass_show1==true)
                {
                    pass_show1=false;
                    pass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass1_show_img.setImageResource(R.drawable.eye_show);
                }
                else
                {
                    pass_show1=true;
                    pass1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass1_show_img.setImageResource(R.drawable.eye_hide);
                }
                pass1.setSelection(pass1.length());

            }
        });
        pass2_show_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass_show2==true)
                {
                    pass_show2=false;
                    pass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass2_show_img.setImageResource(R.drawable.eye_show);
                }
                else
                {
                    pass_show2=true;
                    pass2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass2_show_img.setImageResource(R.drawable.eye_hide);
                }
                pass2.setSelection(pass2.length());

            }
        });

        //register account database link
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getEmail=email.getText().toString();
                final String getMobile=mobile.getText().toString();
                signupauthentication();
                //redirect to otp verifying
//                Intent intent=new Intent(SignUp.this,Otp_verify.class);
//                intent.putExtra("mobile",getMobile);
//                intent.putExtra("email",getEmail);
//                startActivity(intent);

            }
        });


        //login page back
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Login.class));
            }
        });


    }

    private void signupauthentication()
    {
        String s_email=email.getText().toString();
        String s_phn=mobile.getText().toString();
        String s_pass1=pass1.getText().toString();
        String s_pass2=pass2.getText().toString();
        String s_name=name.getText().toString();

        if(s_name.isEmpty()) name.setError("Name field can't be empty");
        else if(!s_email.matches(emailpattern)) email.setError("Enter correct e-mail");
        else if(s_phn.isEmpty() || s_phn.length()!=11) mobile.setError("Enter correct mobile no");
        else if(s_pass1.isEmpty()) pass1.setError("Password field can't be empty.");
        else if(s_pass1.length()<6) pass1.setError("Password length must be at least 6");
        else if(!s_pass1.equals(s_pass2)) pass2.setError("Password didn't match");
        else
        {
            progressDialog.setMessage("Registration in progress");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

           mAuth.createUserWithEmailAndPassword(s_email,s_pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task)
               {

                   if(task.isSuccessful())
                   {
                       progressDialog.dismiss();
                       Toast.makeText(SignUp.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       progressDialog.dismiss();
                       try
                       {
                           throw task.getException();
                       } catch(Exception e)
                       {
                           Toast.makeText(getApplicationContext(), "Email already taken! Try login", Toast.LENGTH_SHORT).show();
                       }
                   }
                   Intent intent=new Intent(SignUp.this,Login.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
               }
           });
        }
    }


}
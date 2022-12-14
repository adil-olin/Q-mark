package com.example.q_mark.StartActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

import com.example.q_mark.Otp_verify;
import com.example.q_mark.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SignUp extends AppCompatActivity {

    private boolean pass_show1 = false;
    private boolean pass_show2 = false;

    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final String[] nameOfUniversity = new String[]{"University of Dhaka","University of Rajshahi","University of Chittagong","Jahangirnagar University",
            "Islamic University, Bangladesh","Khulna University","Jagannath University", "Comilla University","Jatiya Kabi Kazi Nazrul Islam University",
            "Bangladesh University of Professionals","Begum Rokeya University","University of Barisal", "Rabindra University, Bangladesh","Sheikh Hasina University",
            "Bangabandhu Sheikh Mujibur Rahman University","Bangabandhu Sheikh Mujib Medical University","Chittagong Medical University","Rajshahi Medical University",
            "Sylhet Medical University","Sheikh Hasina Medical University","Shahjalal University of Science and Technology","Hajee Mohammad Danesh Science & Technology University",
            "Mawlana Bhashani Science and Technology University", "Patuakhali Science and Technology University","Noakhali Science and Technology University","Jashore University of Science and Technology",
            "Pabna University of Science and Technology","Bangabandhu Sheikh Mujibur Rahman Science and Technology University", "Rangamati Science and Technology University",
            "Bangamata Sheikh Fojilatunnesa Mujib Science & Technology University","Chandpur Science and Technology University", "Sunamganj Science and Technology University",
            "Bogura Science and Technology University","Lakshmipur Science and Technology University", "Chittagong Veterinary and Animal Sciences University","Bangladesh University of Textiles",
            "Bangabandhu Sheikh Mujibur Rahman Maritime University", "Bangabandhu Sheikh Mujibur Rahman Digital University", "Bangabandhu Sheikh Mujibur Rahman Aviation and Aerospace University",
            "Bangladesh Agricultural University","Bangabandhu Sheikh Mujibur Rahman Agricultural University","Sher-E-Bangla Agricultural University","Sylhet Agricultural University","Khulna Agricultural University",
            "Habiganj Agricultural University","Kurigram Agricultural University","Bangladesh University of Engineering & Technology","Khulna University of Engineering & Technology","Chittagong University of Engineering & Technology",
            "Rajshahi University of Engineering & Technology","Dhaka University of Engineering & Technology","Open University of Bangladesh","National University of Bangladesh","Islamic Arabic University","Islamic University of Technology",
            "Asian University for Women","Military Institute of Science and Technology","International University of Business Agriculture and Technology","North South University","Independent University, Bangladesh","American International University-Bangladesh",
            "Dhaka International University","International Islamic University, Chittagong","Asian University of Bangladesh","East West University","Gono Bishwabidyalay","People's University of Bangladesh"
    };
    private EditText name, email, mobile, pass1, pass2,univ;

    private AppCompatButton signup_button;
    private TextView login;
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        AutoCompleteTextView univname = findViewById(R.id.autouniv);
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,
                R.layout.list_items,R.id.text_view_list_item, nameOfUniversity);
        univname.setAdapter(adapter);
        univname.setAdapter(adapter);
        name = findViewById(R.id.sup_name);
        email = findViewById(R.id.sup_email);
        mobile = findViewById(R.id.sup_mobile);
        pass1 = findViewById(R.id.sup_pass);
        pass2 = findViewById(R.id.sup_pass_cnfrm);
        progressBar = findViewById(R.id.progressBar);
        signup_button = findViewById(R.id.sup_button);
        login = findViewById(R.id.login_page_back);
        progressDialog = new ProgressDialog(this);
        final ImageView pass1_show_img = findViewById(R.id.show_sup_pass);
        final ImageView pass2_show_img = findViewById(R.id.show_sup_pass2);

        mAuth = FirebaseAuth.getInstance();
        //mUser=mAuth.getCurrentUser();

        pass1_show_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass_show1 == true) {
                    pass_show1 = false;
                    pass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass1_show_img.setImageResource(R.drawable.eye_show);
                } else {
                    pass_show1 = true;
                    pass1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass1_show_img.setImageResource(R.drawable.eye_hide);
                }
                pass1.setSelection(pass1.length());

            }
        });
        pass2_show_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass_show2 == true) {
                    pass_show2 = false;
                    pass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass2_show_img.setImageResource(R.drawable.eye_show);
                } else {
                    pass_show2 = true;
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
                progressBar.setVisibility(view.VISIBLE);
                final String getEmail = email.getText().toString();
                final String getMobile = mobile.getText().toString();
                //signupauthentication(view);
                String s_email = email.getText().toString();
                String s_phn = mobile.getText().toString();
                String s_pass1 = pass1.getText().toString();
                String s_pass2 = pass2.getText().toString();
                String s_name = name.getText().toString();
                String u_name=univname.getText().toString();
                System.out.println(u_name);


                if (s_name.isEmpty()) name.setError("Name field can't be empty");
                else if (!s_email.matches(emailpattern)) email.setError("Enter correct e-mail");
                else if (s_phn.isEmpty() || s_phn.length() != 11)
                    mobile.setError("Enter correct mobile no");
                else if (s_pass1.isEmpty()) pass1.setError("Password field can't be empty.");
                else if (s_pass1.length() < 6) pass1.setError("Password length must be at least 6");
                else if (!s_pass1.equals(s_pass2)) pass2.setError("Password didn't match");
                else {
                    progressDialog.setMessage("Please wait for the next step");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+88" + s_phn)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(SignUp.this)                 // Activity (for callback binding)
                            .setCallbacks
                                    (
                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    progressDialog.dismiss();
                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                                    Intent intent = new Intent(SignUp.this, Otp_verify.class);

                                                    intent.putExtra("mobile", s_phn);
                                                    intent.putExtra("email", s_email);
                                                    intent.putExtra("name", s_name);
                                                    intent.putExtra("password", s_pass1);
                                                    intent.putExtra("otp", s);
                                                    intent.putExtra("unv",u_name);
                                                    Toast.makeText(SignUp.this, "enter otp", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                            }
                                    )          // OnVerificationStateChangedCallbacks
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
                progressBar.setVisibility(view.GONE);
            }
        });


        //login page back
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });


    }

    private void signupauthentication(View view) {


        String s_email = email.getText().toString();
        String s_phn = mobile.getText().toString();
        String s_pass1 = pass1.getText().toString();
        String s_pass2 = pass2.getText().toString();
        String s_name = name.getText().toString();


        if (s_name.isEmpty()) name.setError("Name field can't be empty");
        else if (!s_email.matches(emailpattern)) email.setError("Enter correct e-mail");
        else if (s_phn.isEmpty() || s_phn.length() != 11)
            mobile.setError("Enter correct mobile no");
        else if (s_pass1.isEmpty()) pass1.setError("Password field can't be empty.");
        else if (s_pass1.length() < 6) pass1.setError("Password length must be at least 6");
        else if (!s_pass1.equals(s_pass2)) pass2.setError("Password didn't match");
        else {


            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber("+88" + s_phn)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks
                            (
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            progressBar.setVisibility(view.GONE);
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            progressBar.setVisibility(view.GONE);
                                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                            Intent intent = new Intent(SignUp.this, Otp_verify.class);
                                            intent.putExtra("mobile", s_phn);
                                            intent.putExtra("email", s_email);
                                            intent.putExtra("name", s_name);
                                            intent.putExtra("password", s_pass1);
                                            intent.putExtra("otp", s);
                                            Toast.makeText(SignUp.this, "enter otp", Toast.LENGTH_SHORT).show();
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            progressBar.setVisibility(view.GONE);
                                            startActivity(intent);
                                        }
                                    }
                            )          // OnVerificationStateChangedCallbacks
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                    "+88"+s_phn,
//                    60,
//                    TimeUnit.SECONDS,
//                    SignUp.this,
//                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
//                        @Override
//                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//                        }
//
//                        @Override
//                        public void onVerificationFailed(@NonNull FirebaseException e) {
//                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                            Intent intent=new Intent(SignUp.this,Otp_verify.class);
//                            intent.putExtra("mobile",s_phn);
//                            intent.putExtra("email",s_email);
//                            intent.putExtra("otp",s);
//                            Toast.makeText(SignUp.this, "enter otp", Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                        }
//                    }
//            );
//            Intent intent=new Intent(SignUp.this,Otp_verify.class);
//            intent.putExtra("mobile",s_phn);
//            intent.putExtra("email",s_email);
//            intent.putExtra("password",s_pass1);
//            intent.putExtra("name",s_name);
//
//            startActivity(intent);
//            progressDialog.setMessage("Registration in progress");
//            progressDialog.setTitle("Registration");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//           mAuth.createUserWithEmailAndPassword(s_email,s_pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//               @Override
//               public void onComplete(@NonNull Task<AuthResult> task)
//               {
//
//                   if(task.isSuccessful())
//                   {
//                       progressDialog.dismiss();
//                       Toast.makeText(SignUp.this, "Registration successfull", Toast.LENGTH_SHORT).show();
//                   }
//                   else
//                   {
//                       progressDialog.dismiss();
//                       try
//                       {
//                           throw task.getException();
//                       } catch(Exception e)
//                       {
//                           Toast.makeText(getApplicationContext(), "Email already taken! Try login", Toast.LENGTH_SHORT).show();
//                       }
//                   }
//                   Intent intent=new Intent(SignUp.this,Login.class);
//                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                   startActivity(intent);
//               }
//           });
        }
        progressBar.setVisibility(view.GONE);
    }


}
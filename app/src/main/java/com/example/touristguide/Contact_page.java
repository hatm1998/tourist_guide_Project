package com.example.touristguide;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.touristguide.Utilis.Material_Chip_View;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;


public class Contact_page extends AppCompatActivity {

    private Dialog dialog;
    private FirebaseAuth mAuth;
    private Context context;
    private Button btn_facebook, btn_google;
    private FirebaseFirestore firebaseFirestore;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private ProgressBar LoginProgress;

    private static final int GoogleReq = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        LoginProgress = findViewById(R.id.pr_Login_main);
        FacebookSdk.sdkInitialize(this);


        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getApplication());

        btn_google = findViewById(R.id.btn_Google_login);
        btn_facebook = findViewById(R.id.btn_Facebook_login);


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginProgress.setVisibility(View.VISIBLE);
                SignInGoogle();

            }
        });


        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginProgress.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logInWithReadPermissions(Contact_page.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handelFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                        Toast.makeText(getBaseContext(), "User Was Cancel The Process", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {

                        Toast.makeText(getBaseContext(), "Error : " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void SignInGoogle() {
        Intent signIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GoogleReq);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GoogleReq) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    FireBaseAutWithGoogle(account);
                }
            } catch (ApiException e) {
                Log.d("ErorrAPI", e.getMessage());
            }
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void FireBaseAutWithGoogle(GoogleSignInAccount account) {

        final AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult().exists()) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Navigation_Drawer.class);
                                startActivity(intent);

                            }
                            else {



                                HashMap<String, Object> hashMap = new HashMap<>();
                                mAuth = FirebaseAuth.getInstance();
                                String[] parts = mAuth.getCurrentUser().getEmail().split("@");
                                hashMap.put("Username", String.valueOf(parts[0]));
                                mAuth = FirebaseAuth.getInstance();
                                hashMap.put("Image_User", String.valueOf(mAuth.getCurrentUser().getPhotoUrl()));





                                firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                                        .set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getBaseContext(), "User InfoUpdate", Toast.LENGTH_LONG).show();
                                            finish();
                                            Intent intent = new Intent(Contact_page.this, Material_Chip_View.class);
                                            startActivity(intent);
                                        } else
                                            Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();


                                    }
                                });


                            }

                            LoginProgress.setVisibility(View.GONE);
                        }
                    });


                } else
                    Toast.makeText(getBaseContext(), "User can't Registered on server ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handelFacebookToken(final AccessToken accessToken) {


        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult().exists()) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Navigation_Drawer.class);
                                startActivity(intent);
                            } else {
                                final GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {


                                        try {
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            String name = object.getString("first_name") + " " + object.getString("last_name");
                                            String id = object.getString("id");

                                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                                            Log.d("Facebook_User", name);
                                            hashMap.put("Username", name);
                                            hashMap.put("Image_User", image_url);


                                            firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                                                    .set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getBaseContext(), "User InfoUpdate", Toast.LENGTH_LONG).show();
                                                        finish();
                                                        Intent intent = new Intent(Contact_page.this, Material_Chip_View.class);
                                                        startActivity(intent);
                                                    } else
                                                        Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();


                                                }
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });

                                Bundle bundle = new Bundle();
                                bundle.putString("fields", "first_name, last_name, id");
                                graphRequest.setParameters(bundle);
                                graphRequest.executeAsync();
                            }
                            LoginProgress.setVisibility(View.GONE);
                        }
                    });


                } else
                    Toast.makeText(getBaseContext(), "User can't Registered on server ", Toast.LENGTH_LONG).show();
            }
        });

    }


    // Action -> Sign Up Button
    public void btn_signUp(View view) {
        //finish();

        Intent intent = new Intent(Contact_page.this, signup.class);
        startActivity(intent);
        Animatoo.animateFade(context);
    }

    // Action -> Login Button
    public void btn_login(View view) {
        dialog = new Dialog(this, R.style.PauseDialog);
        dialog.setContentView(R.layout.activity_login);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView txt_email = dialog.findViewById(R.id.txt_email);
        final TextView txt_pass = dialog.findViewById(R.id.txt_pass);
        Button btn_login = dialog.findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check__For_Login(txt_email, txt_pass);
            }
        });
        ImageView close = dialog.findViewById(R.id.btn_close_loginpage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void check__For_Login(TextView txt_email, TextView txt_pass) {
        String Email = txt_email.getText().toString().trim();
        String password = txt_pass.getText().toString().trim();

        if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(password)) {
            txt_email.setError(getResources().getString(R.string.Field_empty));
            txt_pass.setError(getResources().getString(R.string.Field_empty));
        } else if (TextUtils.isEmpty(Email)) {
            txt_email.setError(getResources().getString(R.string.Field_empty));

        } else if (TextUtils.isEmpty(password)) {
            txt_pass.setError(getResources().getString(R.string.Field_empty));
        } else {

            // Success Login
            mAuth.signInWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Navigation_Drawer.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        }

    }

}

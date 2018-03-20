package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 6/16/2016.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Login extends AppCompatActivity {


    private AlertDialog.Builder infoDialog;
    private AlertDialog.Builder errorDialog;
    private AlertDialog.Builder confirmDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final EditText username = (EditText) findViewById(R.id.username2), password = (EditText) findViewById(R.id.password2);
        final Button login = (Button) findViewById(R.id.Login2), signup = (Button) findViewById(R.id.signup2);
        final databaseManager db = new databaseManager(this);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String PASSWORD = password.getText().toString();
                String InvalidUsername = "User name doesn't exist";
                if (!((uname.equals("") || uname.length() <= 1) || (PASSWORD.equals("") || PASSWORD.length() <= 1))) {
                    if (db.getPassword(uname).equals(InvalidUsername)) {
                        errorDialog = new AlertDialog.Builder(Login.this);
                        errorDialog.setTitle("ERROR MESSAGE");
                        errorDialog.setIcon(R.mipmap.errorwhite);
                        errorDialog.setMessage(InvalidUsername);
                        errorDialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                        {
                           @Override
                            public void onClick(DialogInterface d,int which)
                           {

                           }
                        });
                        errorDialog.show();
                    }
                    if (PASSWORD.equals(db.getPassword(uname))) {
                        infoDialog = new AlertDialog.Builder(Login.this);
                        infoDialog.setTitle("MESSAGE");
                        infoDialog.setIcon(R.mipmap.infowhite);
                        infoDialog.setMessage("Logging in ...");
                        infoDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which)
                            {


                            if (db.getCountOfProductsInCatalog(username.getText().toString())>0)
                            {
                                Intent gotoProductCatalog = new Intent(Login.this, productCatalogview.class);
                                gotoProductCatalog.putExtra("TABLENAME",username.getText().toString());
                                startActivity(gotoProductCatalog);
                            }
                            else
                            {
                                AlertDialog.Builder exceptionBox=new AlertDialog.Builder(Login.this);
                                exceptionBox.setTitle("ERROR MESSAGE");
                                exceptionBox.setIcon(R.mipmap.errorwhite);
                                exceptionBox.setMessage("No Product Catalog available!");
                                exceptionBox.setPositiveButton("Ok",new DialogInterface.OnClickListener()
                                {
                                    @Override public void onClick(DialogInterface d,int which)
                                    {
                                        AlertDialog.Builder confirmBox=new AlertDialog.Builder(Login.this);
                                        confirmBox.setTitle("CONFIRMATION MESSAGE");
                                        confirmBox.setIcon(R.mipmap.confirm2);
                                        confirmBox.setMessage("Do you wish to create a product catalog now");
                                        confirmBox.setPositiveButton("Yes",new DialogInterface.OnClickListener()
                                        {
                                            @Override public void onClick(DialogInterface d, int which)
                                            {
                                                Intent gotoCreateProductCatalog=new Intent(Login.this,createProductCatalog.class);
                                                gotoCreateProductCatalog.putExtra("USERNAME",username.getText().toString());
                                                startActivity(gotoCreateProductCatalog);
                                            }
                                        });
                                        confirmBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                               dialog.dismiss();
                                            }
                                        });confirmBox.show();
                                    }
                                });exceptionBox.show();

                            }

                            }
                        });
                        infoDialog.show();
                    }
                    else
                    {
                        errorDialog = new AlertDialog.Builder(Login.this);
                        errorDialog.setTitle("ERROR MESSAGE");
                        errorDialog.setIcon(R.mipmap.errorwhite);
                        errorDialog.setMessage("Wrong password!");
                        errorDialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface d,int which)
                            {

                            }
                        });
                        errorDialog.show();
                    }
                }
                else
                    {
                        errorDialog = new AlertDialog.Builder(Login.this);
                        errorDialog.setTitle("ERROR MESSAGE");
                        errorDialog.setIcon(R.mipmap.errorwhite);
                        errorDialog.setMessage("Invalid or no Username and Password specified");
                        errorDialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface d,int which)
                            {

                            }
                        });
                        errorDialog.show();

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog = new AlertDialog.Builder(Login.this);
                confirmDialog.setTitle("CONFIRMATION MESSAGE");
                confirmDialog.setIcon(R.mipmap.confirm2);
                confirmDialog.setMessage("Are you sure?");
                confirmDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface d,int which)
                    {
                        Intent gotoSignup = new Intent(Login.this, signUp.class);
                        startActivity(gotoSignup);

                    }
                });
                confirmDialog.setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override public void onClick(DialogInterface d, int which)
                    {

                    }
                });
                confirmDialog.show();

            }
        });
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
    }

}

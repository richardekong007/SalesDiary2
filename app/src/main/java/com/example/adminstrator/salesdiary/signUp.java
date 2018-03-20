package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 6/16/2016.
 */

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class signUp extends AppCompatActivity
{
    private AlertDialog.Builder Infodialog;
    private AlertDialog.Builder errorDialog;
    private AlertDialog.Builder confirmDialog;
    public String userTableName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        final databaseManager db=new databaseManager(this);
       final ImageButton ok=new ImageButton(this);
        final EditText username=(EditText)findViewById(R.id.username),
        Email=(EditText)findViewById(R.id.email),
        Password=(EditText)findViewById(R.id.password);

        final Button login=(Button)findViewById(R.id.Login),
                signup=(Button)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String message="Do you possess a valid user account?";
                confirmDialog =new AlertDialog.Builder(signUp.this);
                confirmDialog.setTitle("CONFIRMATION MESSAGE");
                confirmDialog.setIcon(R.mipmap.confirm2);
                confirmDialog.setMessage(message);
                confirmDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface d,int i)
                    {
                        Intent goToLogin=new Intent(signUp.this,Login.class);
                        startActivity(goToLogin);

                    }
                });
                confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                confirmDialog.show();

            }
        });
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Infodialog=new AlertDialog.Builder(signUp.this);
                Infodialog.setTitle("MESSAGE");
                Infodialog.setIcon(R.mipmap.infowhite);
                Infodialog.setMessage("Capturing User's credentials ...");
                Infodialog.setPositiveButton("Ok",new DialogInterface.OnClickListener()
               {
                   @Override
                   public void onClick(DialogInterface d,int i)
                   {
                       String name, email, password;

                       name = username.getText().toString();
                       email=Email.getText().toString();
                       password=Password.getText().toString();
                       if(!((name.equals("") || name.length()<=1)||
                               (email.equals("") || email.length()<=1 )
                               || (password.equals("")
                               || password.length()<=1) || db.verifyUsername(name)==true ))
                       {
                           db.addUser(new User(name,email,password));
                           Toast.makeText(getBaseContext(),name+" has been added",Toast.LENGTH_LONG).show();
                           db.createUserProductCatalog(username.getText().toString());
                           db.close();
                           Intent createProductCatalogView=new Intent(signUp.this,createProductCatalog.class);
                           createProductCatalogView.putExtra("USERNAME",username.getText().toString());
                           startActivity(createProductCatalogView);
                       }

                       else
                       {

                           errorDialog=new AlertDialog.Builder(signUp.this);
                           errorDialog.setTitle("ERROR MESSAGE");
                           errorDialog.setIcon(R.mipmap.errorwhite);
                           String namePrompt=db.verifyUsername(name)?name:"username";
                           errorDialog.setMessage("Invalid or no input provided in one or all of the fields.\n" +
                                   "Or perhaps, "+namePrompt+" already exist");
                           errorDialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                           {
                               @Override public void onClick(DialogInterface d,int i)
                               {
                                   d.dismiss();
                               }
                           });
                           errorDialog.show();

                       }

                   }
               });
                Infodialog.show();
                   }
        });
    }
    public void onActivityResult1(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==0 && resultCode==RESULT_OK)
        {
            userTableName=data.getStringExtra("USERNAME");
        }
    }
    @Override public void onConfigurationChanged(final Configuration newConfiguration)
    {
        super.onConfigurationChanged(newConfiguration);
    }
}
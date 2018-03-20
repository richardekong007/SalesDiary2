package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 6/30/2016.
 */

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class barcodeScanner
{

    private AppCompatActivity activity;
    private Context ctx;

    private static final String ACTION_SCAN="com.google.zxing.client.android.SCAN";
    //scan barcode
    public Intent scanBarcode(AppCompatActivity act)
    {
        Intent intent=new Intent();
        try
        {
            intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE","PRODUCT_MODE");
            act.startActivityForResult(intent,0);

        }
        catch(ActivityNotFoundException e)
        {
            int id= R.mipmap.ic_launcher;
            showDialog(activity,"No Scanner Found", id,"Download a scanner code activity","yes","No").show();
        }
        return intent;
    }
    //Alert dialog box for downloadDialog
    private static AlertDialog showDialog(final AppCompatActivity act, String title , int IconId , CharSequence message, CharSequence buttonYes, CharSequence buttonNo)
    {
        AlertDialog.Builder downloadDialog=new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setIcon(IconId);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Uri uri = Uri.parse("market://search?q=pname:"+"com.google.zxing.client.android");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                try
                {
                    act.startActivity(intent);
                }
                catch(ActivityNotFoundException e){}
            }

        });
        downloadDialog.setNegativeButton(buttonNo,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode,int resultCode, Intent intent,TextView tv, AppCompatActivity act)
    {
        if (requestCode==0)
        {
            if (resultCode==act.RESULT_OK)
            {
                //get result from scanning activity
                String content=intent.getStringExtra("SCAN_RESULT");
                String format=intent.getStringExtra("SCAN_RESULT_FORMAT");
                tv.setText(content);
            }
        }
    }
}

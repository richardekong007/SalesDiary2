package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 7/10/2016.
 */
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.MediaStore;
import android.net.Uri;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class createProductCatalog extends AppCompatActivity
{
    //instance variable declaration
    int TAKE_PHOTO_CODE=0;
    EditText imgpath;
    Uri pictureUri;
    userProductCatalog product;
    signUp signup;
    productCatalogview productList;
    CustomCursorAdapter cca;
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.create_product_catalog);
        //create UI controls

        final EditText productDesc=(EditText)findViewById(R.id.productdesc);
        final EditText costPrice=(EditText)findViewById(R.id.costprice);
        imgpath=(EditText)findViewById(R.id.imgpath);
        final EditText stockValue=(EditText)findViewById(R.id.stockValue);
        final ImageButton cameraBtn=(ImageButton)findViewById(R.id.capturebtn);
        final Button storeBtn=(Button)findViewById(R.id.store);
        final databaseManager db=new databaseManager(this);
        Bundle extras = getIntent().getExtras();
        //get username from sign up activity
        final String tableName=extras.getString("USERNAME");
        //set event listener for camera button
        cameraBtn.setOnClickListener(new View.OnClickListener()
        {
           @Override public void onClick(View v)
           {
            captureImage();
               /**ContentValues values=new ContentValues();
               values.put(MediaStore.Images.Media.TITLE,"Image File name");
               pictureUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
               Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
               startActivityForResult(cameraIntent,TAKE_PHOTO_CODE);**/
           }
        });
        //set event listener for add button
       storeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v) {
                try {
                    String description = productDesc.getText().toString(), imagePath = imgpath.getText().toString();

                    Double price = Double.parseDouble(costPrice.getText().toString());
                    Integer stock = Integer.parseInt(stockValue.getText().toString());


                    if (!((productDesc.getText().toString().equals("") || costPrice.getText().toString().equals("") ||
                            imgpath.getText().toString().equals("") || stockValue.getText().toString().equals("")) ||
                            (description.length() <= 1 || imagePath.length() <= 1 || !(stock instanceof Integer) ||
                                    !(price instanceof Double)))) {
                        product = new userProductCatalog(description, price, imagePath, stock);
                        db.addToProductCatalog(product, tableName);
                        AlertDialog.Builder infoDialog = new AlertDialog.Builder(createProductCatalog.this);
                        infoDialog.setTitle("MESSAGE");
                        infoDialog.setIcon(R.mipmap.infowhite);
                        infoDialog.setMessage("Product details stored successfully!");
                        infoDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {

                                productDesc.setText("");
                                costPrice.setText("");
                                imgpath.setText("");
                                stockValue.setText("");

                            }
                        });
                        infoDialog.show();
                    }
                    else
                    {
                        final AlertDialog.Builder errorDialog = new AlertDialog.Builder(createProductCatalog.this);
                        errorDialog.setTitle("ERROR MESSAGE");
                        errorDialog.setIcon(R.mipmap.errorwhite);
                        errorDialog.setMessage("Invalid product details provided!");
                        errorDialog.setPositiveButton("hints", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {
                                String hint1 = "1.Ensure all product details are provided",
                                        hint2 = "2.Ensure numeric values are provided for cost price and stock",
                                        hint3 = "3.Ensure all values have length size greater than 1";
                                AlertDialog.Builder errorBox=new AlertDialog.Builder(createProductCatalog.this);
                                errorBox.setTitle("ERROR MESSAGE");
                                errorBox.setIcon(R.mipmap.errorwhite);
                                errorBox.setMessage(hint1 + "\n" + hint2 + "\n" + hint3 + "\n");
                                errorBox.setPositiveButton("Close",new DialogInterface.OnClickListener()
                                {
                                    @Override public void onClick(DialogInterface d,int which)
                                    {
                                        d.dismiss();
                                    }
                                });
                                errorBox.show();
                            }
                        });
                        errorDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {
                            }
                        });
                        errorDialog.show();

                    }
                }
                catch(NumberFormatException e)
                {
                    AlertDialog.Builder errorBox=new AlertDialog.Builder(createProductCatalog.this);
                    errorBox.setTitle("ERROR MESSAGE");
                    errorBox.setIcon(R.mipmap.errorwhite);
                    errorBox.setMessage("Invalid number format ");
                    errorBox.setPositiveButton("Close",new DialogInterface.OnClickListener()
                    {
                        @Override public void onClick(DialogInterface d,int which)
                        {
                            d.dismiss();
                        }
                    });
                    errorBox.show();

                }

            }});
             final Button ViewCatalog=(Button)findViewById(R.id.cat);
            ViewCatalog.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View v)
                {
                    //pass tablename to productCatalogView
                    Intent productCatalogViewIntent=new Intent(createProductCatalog.this,productCatalogview.class);
                    productCatalogViewIntent.putExtra("TABLENAME",tableName);
                    startActivity(productCatalogViewIntent);
                }
            });
    }
    public void captureImage()
    {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image File name");
        pictureUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
        startActivityForResult(cameraIntent,TAKE_PHOTO_CODE);
    }
    @Override public void onActivityResult(int requestCode,int ResultCode,Intent data)
    {
        super.onActivityResult(requestCode,ResultCode,data);
        if (requestCode==TAKE_PHOTO_CODE && ResultCode==RESULT_OK)
        {
            final String pictureLocationValue=getRealPathFromUri(pictureUri);
            final AlertDialog.Builder infoDialog=new AlertDialog.Builder(this);
            if (!(pictureLocationValue==null||pictureLocationValue=="")){
                infoDialog.setTitle("MESSAGE");
                infoDialog.setIcon(R.mipmap.infowhite);
                infoDialog.setMessage("image path captured successfully!");
                imgpath.setText(String.valueOf(pictureLocationValue));
            }
            else{
                infoDialog.setTitle("ERROR MESSAGE");
                infoDialog.setIcon(R.mipmap.errorwhite);
                infoDialog.setMessage("Failure capturing image path, try again.");
            }
            infoDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener()
            {
                @Override public void onClick(DialogInterface d,int which)
                {

                }
            });
            infoDialog.show();
        }
    }
    public String getRealPathFromUri(Uri uri)
    {
        try
        {
            String [] imageProjections={MediaStore.Images.Media.DATA};
            Cursor cursor=managedQuery(uri,imageProjections,null,null,null);
            int columnIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        }
        catch(Exception e)
        {
            return uri.getPath();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfiguration)
    {
        super.onConfigurationChanged(newConfiguration);
    }

}

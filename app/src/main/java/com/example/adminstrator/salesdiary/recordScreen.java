package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 6/19/2016.
 */

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class recordScreen extends AppCompatActivity
{

    productCatalog productCatalog;
    static final String ACTION_SCAN="com.google.zxing.client.android.SCAN";
    private String tableName;
    private String sales_record_table;
    private static String [] productNames;
    databaseManager db;
    EditText pcode;
    ArrayAdapter arrayAdapter;
    private static int selectedItem;
    private String selectedProduct;
    private int displayedStock=0;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.recordsales);
        Bundle extra=getIntent().getExtras();
        tableName=extra.getString("TABLENAME");
        sales_record_table=extra.getString("SALES RECORD TABLE");
        productCatalog =new productCatalog();
        db=new databaseManager(this);
        String sqliteQuery="SELECT DESCRIPTION FROM "+tableName;
        SQLiteDatabase sqliteDb=db.createWritableDb();
        Cursor c=sqliteDb.rawQuery(sqliteQuery,null);
        //initialize productNames Array
        productNames=new String[c.getCount()];
        //populate productNames Array with product descriptions from database table
        int index=0;
        if (c.moveToFirst())
        {
            do
            {
             productNames[index]=c.getString(c.getColumnIndexOrThrow("DESCRIPTION"));
             //increment index
              index++;
            }
            while(c.moveToNext());
        }
        //get views by id
        final ImageView productImg=(ImageView)findViewById(R.id.productpic);
        final TextView stockLeft=(TextView)findViewById(R.id.stockleft),
                stockflag=(TextView)findViewById(R.id.stockFlag),
                costPriceValue=(TextView)findViewById(R.id.costpricetext);
        pcode=(EditText)findViewById(R.id.pcodeedit);
        final EditText salesPriceValue=(EditText)findViewById(R.id.salespriceedit),
                stockValue=(EditText)findViewById(R.id.stockValue);
        Switch scanSwitch=(Switch)findViewById(R.id.recordscanswitch);
        Spinner spinner=(Spinner)findViewById(R.id.descSpinner);
        Button recordBtn=(Button)findViewById(R.id.record),
                viewReport=(Button)findViewById(R.id.ViewReport);
        //create arrayadapter for spinner
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_selectable_list_item,productNames);
        //set event Listener for spinner
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = parent.getSelectedItemPosition();
                selectedProduct = parent.getSelectedItem().toString();
                selectedItem = index;
                //setSelectedProductImage(selectedItem);
                //get selected Product stock values
               String sqliteQuery = "SELECT IMAGEPATH,COSTPRICE,STOCK FROM " + tableName + " WHERE DESCRIPTION=?";
                SQLiteDatabase sqliteDb = db.createWritableDb();
                Cursor c = sqliteDb.rawQuery(sqliteQuery,new String[]{selectedProduct});
                if (c.moveToFirst())
                {
                    try
                    {
                        //load image bitmap through image path
                        String imagepath=c.getString(c.getColumnIndexOrThrow("IMAGEPATH"));
                        productImg.setImageBitmap(BitmapLoader.loadBitmap(imagepath,150,150));
                        displayedStock=c.getInt(c.getColumnIndexOrThrow("STOCK"));
                        stockLeft.setText(String.valueOf(c.getInt(c.getColumnIndexOrThrow("STOCK"))));
                        costPriceValue.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("COSTPRICE"))));
                    }
                    catch (NullPointerException npe) {}

                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        //setting event Listener for stockvalue
        stockValue.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override public void onFocusChange(View v, boolean hasFocus)
            {

                stockLeft.setText(String.valueOf(displayedStock));
                stockflag.setText("left");//ensure stock phrase remain unchanged
                int recordedStock=0;
                int actualStock=Integer.parseInt(stockLeft.getText().toString());
                int finalStock=0;

                    try
                    {

                    recordedStock=Integer.parseInt(stockValue.getText().toString());
                    finalStock=actualStock-recordedStock;
                    if (finalStock<1)
                    {
                        stockLeft.setText("");
                        stockflag.setText("Out of Stock!");
                    }
                    else
                    {
                        stockLeft.setText(String.valueOf(finalStock));
                    }
                }
                    catch (NumberFormatException nfe){}

            }
        });
        //setting event listener for scan switch
        scanSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Intent intent=new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE","PRODUCT_MODE");
                    startActivityForResult(intent,0);
                }
                catch(ActivityNotFoundException e) {
                    //on catch, show the download dialog
                    showDialog(recordScreen.this, "No Scanner Found", "Download a scanner code activity", "Yes", "No").show();
                }
            }
        });
        //setting event listener for record button
        recordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                if (!(stockflag.getText().toString().equals("Out of Stock!")))
                {

                    try
                {

                    //get sales detail to store in the sales record table

                    int id=new salesRecord().getId();
                    String productcode=pcode.getText().toString();
                    String description=selectedProduct;
                    String imagePath=getImagePath(tableName);
                    int initialStock=displayedStock;
                    int recordedStock=Integer.parseInt(stockValue.getText().toString());
                    int remainingStock=initialStock-recordedStock;
                    Double costprice=Double.parseDouble(costPriceValue.getText().toString());
                    Double salesprice=Double.parseDouble(salesPriceValue.getText().toString());
                    Double profit=recordedStock*(salesprice-costprice);
                    Double loss=0.0;//set loss to profit if profit is less than 1, else set loss to zero
                    loss=profit<1?profit:loss;

                    String Time=new salesRecord().getTimeStamp();
                    String date=new salesRecord().getDateTime();
                    //create an SQLiteDatabase object
                    databaseManager db=new databaseManager(recordScreen.this);
                    SQLiteDatabase sqliteDb=db.createWritableDb();
                    ContentValues values=new ContentValues();
                    values.put("RID",id);
                    values.put("PRODUCTCODE",productcode);
                    values.put("DESCRIPTION",description);
                    values.put("IMAGEPATH",imagePath);
                    values.put("INITIAL_STOCK",initialStock);
                    values.put("RECORDED_STOCK",recordedStock);
                    values.put("STOCK_LEFT",remainingStock);
                    values.put("COSTPRICE",costprice);
                    values.put("SALESPRICE",salesprice);
                    values.put("PROFIT",profit);
                    values.put("LOSS",loss);
                    values.put("DATETIME",date);
                    sqliteDb.insert(sales_record_table,null,values);

                    //update product catalog table with remaining stock
                    ContentValues value=new ContentValues();
                    value.put("STOCK",remainingStock);
                   sqliteDb.update(tableName,value,"DESCRIPTION=?",new String[]{description});
                   AlertDialog.Builder dialog=new AlertDialog.Builder(recordScreen.this);
                    dialog.setTitle("INFORMATION MESSAGE");
                    dialog.setIcon(R.mipmap.infowhite);
                    dialog.setMessage("Sales recorded successful with record ID of\n"+id);
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener()
                    {
                        @Override public void onClick(DialogInterface d, int which)
                        {
                            d.dismiss();
                        }
                    });
                    dialog.show();
                }
                catch (SQLiteException sqle)
                {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(recordScreen.this);
                    dialog.setTitle("ERROR MESSAGE");
                    dialog.setIcon(R.mipmap.errorwhite);
                    dialog.setMessage(sqle.getMessage());
                    dialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                    {
                        @Override public void onClick(DialogInterface d, int which)
                        {
                            d.dismiss();
                        }
                    });
                    dialog.show();
                }
                catch (NumberFormatException nfe)
                {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(recordScreen.this);
                    dialog.setTitle("ERROR MESSAGE");
                    dialog.setIcon(R.mipmap.errorwhite);
                    dialog.setMessage("You tried to record incorrect detail.\nEnsure you provide all details\n"+nfe.getMessage());
                    dialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                    {
                        @Override public void onClick(DialogInterface d, int which)
                        {
                            d.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
                else
                {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(recordScreen.this);
                    dialog.setTitle("ERROR MESSAGE");
                    dialog.setIcon(R.mipmap.errorwhite);
                    dialog.setMessage("Out of Stock!");
                    dialog.setPositiveButton("Close",new DialogInterface.OnClickListener()
                    {
                        @Override public void onClick(DialogInterface d, int which)
                        {
                            salesPriceValue.setText("");
                        }
                    });
                    dialog.show();
                }
        }
        });
            viewReport.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String sales_record_table=tableName+"_Sales_Records";
                    Intent viewReport=new Intent(recordScreen.this,ReportMenu.class);
                    //viewReport.putExtra("TABLENAME",sales_record_table);
                    viewReport.putExtra("SALES RECORD TABLE",sales_record_table);
                    startActivity(viewReport);
                }
            });
    }
    public String getImagePath(String table_name)
    {
        String imagePath="";
        databaseManager db=new databaseManager(this);
        SQLiteDatabase sqliteDb=db.createWritableDb();
        Cursor c;
        String query="SELECT IMAGEPATH FROM "+table_name+" WHERE DESCRIPTION=?";
        c=sqliteDb.rawQuery(query,new String []{selectedProduct});
        if (c.moveToFirst())
        {
            imagePath=c.getString(c.getColumnIndexOrThrow("IMAGEPATH"));
        }
        return imagePath;
    }

    private static AlertDialog showDialog(final AppCompatActivity act, String title , CharSequence message, CharSequence buttonYes, CharSequence buttonNo)
    {
        AlertDialog.Builder downloadDialog=new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
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
    public void onActivityResult(int requestCode,int resultCode, Intent intent)
    {
        if (requestCode==0)
        {
            if (resultCode==RESULT_OK)
            {
                //get result from scanning activity
                String content=intent.getStringExtra("SCAN_RESULT");
                String format=intent.getStringExtra("SCAN_RESULT_FORMAT");
                pcode.setText(content);
            }
        }
    }
    @Override public void onConfigurationChanged(final Configuration newConfiguration)
    {
        super.onConfigurationChanged(newConfiguration);
    }
}

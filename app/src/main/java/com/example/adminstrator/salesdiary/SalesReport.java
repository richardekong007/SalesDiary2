package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 8/31/2016.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.database.Cursor;
import android.os.Handler;
import android.widget.TextView;


public class SalesReport extends AppCompatActivity {

    private String tableName;
    private String queryString;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.reportview);
        //get sales record table name
        Bundle Extra = getIntent().getExtras();
        tableName = Extra.getString("SALES RECORD TABLE");
        //get query string
        final Bundle extra = getIntent().getExtras();
        //instantiating listview for list of products sold
        final ListView salesList = (ListView) findViewById(R.id.reportlistLayout);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                databaseManager db = new databaseManager(SalesReport.this);
                SQLiteDatabase sqLiteDatabase = db.createWritableDb();
                //get queryString Bundle
                queryString = extra.getString("SALES_QUERY");
                Cursor c = sqLiteDatabase.rawQuery(queryString, null);
                //create customed cursor adapter and associate with a listView
                customAdapter2 customCursorAdapter = new customAdapter2(SalesReport.this, c);
                salesList.setAdapter(customCursorAdapter);

            }
        });
        //create summary Button
        Button sumary = (Button) findViewById(R.id.summary);
        sumary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //link to Report summary class
            }
        });
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
    }

    class customAdapter2 extends CursorAdapter {

        public customAdapter2(Context ctx, Cursor cursor) {
            super(ctx, cursor);
        }

        @Override
        public View newView(Context ctx, Cursor cursor, ViewGroup vroot) {
            LayoutInflater layoutInflater = LayoutInflater.from(vroot.getContext());
            View v = layoutInflater.inflate(R.layout.report_field, vroot, false);
            return v;
        }

        @Override
        public void bindView(View v, Context ctx, Cursor cursor) {
            ImageView productImage = (ImageView) v.findViewById(R.id.product_report_pictext);
            TextView RidData = (TextView) v.findViewById(R.id.Riddata),
                    descdata = (TextView) v.findViewById(R.id.desc_reportdata),
                    productcodedata = (TextView) v.findViewById(R.id.productCodedata),
                    Stockleftdata = (TextView) v.findViewById(R.id.stockleftdata),
                    StockSolddata = (TextView) v.findViewById(R.id.stocksoldtextdata),
                   InitialStock=(TextView)v.findViewById(R.id.initialstockdata),
                    costdata = (TextView) v.findViewById(R.id.costpricedata),
                    salesdata = (TextView) v.findViewById(R.id.salespricedata),
                    profitdata = (TextView) v.findViewById(R.id.profitdata),
                    lossdata = (TextView) v.findViewById(R.id.lossdata),
                    dateTime = (TextView) v.findViewById(R.id.Datetime);
            //Associate the views with the cursor
            String imgPath = cursor.getString(cursor.getColumnIndexOrThrow("IMAGEPATH"));
            productImage.setImageBitmap(BitmapLoader.loadBitmap(imgPath, 100, 100));
            RidData.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("RID"))));
            descdata.setText((cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"))));
            productcodedata.setText(cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTCODE")));
            StockSolddata.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("RECORDED_STOCK"))));
            InitialStock.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("INITIAL_STOCK"))));
            Stockleftdata.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("STOCK_LEFT"))));

            costdata.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("COSTPRICE"))));
            salesdata.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("SALESPRICE"))));
            profitdata.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("PROFIT"))));
            lossdata.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("LOSS"))));
            dateTime.setText(cursor.getString(cursor.getColumnIndexOrThrow("DATETIME")));
        }
    }
}
package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 9/2/2016.
 */
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class ReportMenu extends AppCompatActivity
{
    private String tableName;
    @Override public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.sale_report_menu);
        //get sales record table name
        final Bundle Extra=getIntent().getExtras();
        tableName=Extra.getString("SALES RECORD TABLE");
        //get views by id
        ImageButton dailyOption=(ImageButton)findViewById(R.id.daily),
                weeklyOption=(ImageButton)findViewById(R.id.weekly),
                monthlyOption=(ImageButton)findViewById(R.id.monthly),
                quarteroption=(ImageButton)findViewById(R.id.quarterly),
                semesterOption=(ImageButton)findViewById(R.id.semester),
                yearlyOption=(ImageButton)findViewById(R.id.yearly);
        Button  generalOtpion=(Button)findViewById(R.id.generalsales);
        //set onclick listeners for every button
        dailyOption.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String queryString="SELECT * FROM "+tableName+" WHERE DATETIME > datetime('now','-1 days')";
                Intent generateDailyReport=new Intent(ReportMenu.this,SalesReport.class);
                generateDailyReport.putExtra("SALES_QUERY",queryString);
                startActivity(generateDailyReport);
            }
        });
        weeklyOption.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String queryString="SELECT * FROM "+tableName+" WHERE DATETIME > datetime('now','-7 days')";
                Intent generateWeeklyReport=new Intent(ReportMenu.this,SalesReport.class);
                generateWeeklyReport.putExtra("SALES_QUERY",queryString);
                startActivity(generateWeeklyReport);
            }
        });
        monthlyOption.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
        {
            String queryString="SELECT * FROM "+tableName+" WHERE DATETIME > datetime('now','-1 months')";
            Intent generateMonthly=new Intent(ReportMenu.this,SalesReport.class);
            generateMonthly.putExtra("SALES_QUERY",queryString);
             startActivity(generateMonthly);
        }
        });
        quarteroption.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String queryString="SELECT * FROM "+tableName+" WHERE DATETIME > datetime('now','-3 months')";
                Intent generateQuarter=new Intent(ReportMenu.this,SalesReport.class);
                generateQuarter.putExtra("SALES_QUERY",queryString);
                startActivity(generateQuarter);
            }

        });
        semesterOption.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String queryString="SELECT * FROM "+tableName+" WHERE DATETIME > datetime('now','-6 months')";
                Intent generateSemester=new Intent(ReportMenu.this,SalesReport.class);
                generateSemester.putExtra("SALES_QUERY",queryString);
                startActivity(generateSemester);
            }
        });
        yearlyOption.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String queryString="SELECT * FROM "+tableName+" WHERE DATETIME > datetime('now','-1 years')";
                Intent generateYear=new Intent(ReportMenu.this,SalesReport.class);
                generateYear.putExtra("SALES_QUERY",queryString);
                 startActivity(generateYear);
            }
        });
        generalOtpion.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View V)
            {
                String queryString="SELECT * FROM "+tableName;
                Intent generateReport=new Intent(ReportMenu.this,SalesReport.class);
                generateReport.putExtra("SALES_QUERY",queryString);
                startActivity(generateReport);
            }
        });
    }
    @Override public void onConfigurationChanged(final Configuration newConfiguration)
    {
        super.onConfigurationChanged(newConfiguration);
    }
}

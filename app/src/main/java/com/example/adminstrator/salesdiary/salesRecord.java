package com.example.adminstrator.salesdiary;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
/**
 * Created by Adminstrator on 7/9/2016.
 */

public class salesRecord
{

        //declaring instance variable
        private int id;
        private String code;
        private String description;
        private Double quantity;
        private Double quantityLeft;
        private Double profit;
        private Double salesPrice;
        private String imagePath;
        private String timeStamp;
        private String date;
        private Random RandInt=new Random();
        //salesRecord default constructor
        public salesRecord()
        {

        }
        public salesRecord(String code, String description, Double quantity, Double salesPrice)
        {
            this.code=code;
            this.description =description;
            this.quantity=quantity;
            this.salesPrice = salesPrice;

        }
        //implement setter and getter methods
        public String getCode()
        {
            return this.code;
        }
        public void setCode(String code){
            this.code=code;
        }
        public String getDescription()
        {
            return this.description;
        }
        public void setDescription(String description)
        {
            this.description = description;
        }
        public Double getQuantity()
        {
            return this.quantity;
        }
        public void setQuantity(Double quantity)
        {
            this.quantity=quantity;
        }
        public int getId()
        {
            int min=1000000, max=10000000;
            id=RandInt.nextInt((max-min)+1)+min;
            return id;
        }
        public Double getQuantityLeft(Double quantitySold)
        {
            quantityLeft=getQuantity()-quantitySold;
            return quantityLeft;
        }
        public Double getProfit(Double costPrice)
        {
            Double sellingPrice=getSalesPrice();
            profit=sellingPrice-costPrice;
            return profit;
        }
        public Double getSalesPrice()
        {
            return this.salesPrice;
        }
        public void setSalesPrice(Double salesPrice)
        {
            this.salesPrice = salesPrice;

        }
        public String getImagePath()
        {
            return this.imagePath;
        }
        public void setImagePath(String imagePath)
        {
            this.imagePath=imagePath;

        }

        public String  getTimeStamp()
        {
            //get Calendar instance
            Calendar time=Calendar.getInstance();
            int HH=time.get(Calendar.HOUR_OF_DAY);
            int MM=time.get(Calendar.MINUTE);
            int SS=time.get(Calendar.SECOND);
            //format time in HH:MM:SS
            if (HH<10)
            {
                timeStamp="0"+HH+":"+MM+":"+SS;
            }
            else if (MM<10)
            {
                timeStamp=HH+":"+"0"+MM+":"+SS;
            }
            else if (SS<10)
            {
                timeStamp=HH+":"+MM+":"+"0"+SS;
            }
            else
            {
                timeStamp=HH+":"+MM+":"+SS;
            }
            return timeStamp;
        }
        public void setTimeStamp(int HH, int MM, int SS)
        {
            try {
                //format time in HH:MM:SS
                String time;
                time = HH + ":" + MM + ":" + SS;
                timeStamp = time;
            }
            catch(NumberFormatException e)
            {
                
            }
        }
        public String getDate()
        {
            // get calendar instance
            Calendar DATE=Calendar.getInstance();
            int day=DATE.get(Calendar.DAY_OF_MONTH);
            int month=DATE.get(Calendar.MONTH)+1;
            int year=DATE.get(Calendar.YEAR);
            //format date in YYYY-MM-DD
            if (year<10)
            {
                date="000"+year+"-"+month+"-"+day;
            }
            else if ((year>10) && (year<100))
            {
                date="00"+year+"-"+month+"-"+day;
            }
            else if ((year>100)&&(year<1000))
            {
                date="0"+year+"-"+month+"-"+day;
            }
            else if (month<10)
            {
                date=year+"-"+"0"+month+"-"+day;
            }
            else if (day<10)
            {
                date=year+"-"+month+"-"+"0"+day;
            }
            else
            {
               date=year+"-"+month+"-"+day;
            }
            return date;
        }
        public void setDate(int year, int month, int day)
        {
            try
            {
            String d;
            d=year+"-"+month+"-"+day;
            date=d;
            }
            catch(NumberFormatException e)
            {

            }
        }
        public String getDateTime()
        {
            // return Datetime value in YYYY-MM-DD HH:MM:SS
            return getDate()+" "+getTimeStamp();
        }
    }




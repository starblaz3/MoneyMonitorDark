package com.example.moneymoniter;

import java.util.Date;

public class datas {
    public String sender;
    public double amount;
    public Date date;
    public String item;
    public boolean isperfect=false;
    public boolean debited;// this is true when debited and false when credited
    public void setSender(String sender)
    {
        this.sender=sender;
    }
    public void setAmount(double amount)
    {
        this.amount=amount;
    }
    public void setItem(String item)
    {
        this.item=item;
    }
    public void setDate(Date date)
    {
        this.date=date;
    }
    public String getSender()
    {
        return this.sender;
    }
    public double getAmount()
    {
        return this.amount;
    }
    public String getItem()
    {
        return this.item;
    }
    public Date getDate()
    {
        return this.date;
    }
}

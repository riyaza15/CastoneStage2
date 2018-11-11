package riyaza.grocerystore.myaccount;


public class orderhistory_model {
    
    private String orderid, shippingaddress, price, date;
    
    public orderhistory_model( String orderid, String shipping, String price, String date){
        
        this.orderid = orderid;
        this.shippingaddress = shipping;
        this.price = price;
        this.date = date;
    }

    public String getorder_id(){ return orderid;}
    public void setorder_id(String id){ this.orderid = id;}

    public String getshippingaddress(){ return shippingaddress;}
    public void setshippingaddress(String name){ this.shippingaddress = name;}


    public String getprice(){ return price;}
    public void setprice(String price){ this.price = price;}

    public String getdate(){ return date;}
    public void setdate(String date){ this.date = date;}

}

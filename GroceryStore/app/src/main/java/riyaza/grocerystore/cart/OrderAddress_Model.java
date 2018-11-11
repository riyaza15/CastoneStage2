package riyaza.grocerystore.cart;


public class OrderAddress_Model  {
    private String address_id, addrs_fullname, fulladdress,addrs_phone;

    public OrderAddress_Model(String address_id, String addrs_fullname, String fulladdress, String addrs_phone){

        this.address_id = address_id;
        this.addrs_fullname = addrs_fullname;
        this.fulladdress = fulladdress;
        this.addrs_phone = addrs_phone;

    }

    public String getaddress_id(){ return address_id;}
    public void setaddress_id(String id){ this.address_id = id;}

    public String getaddrs_fullname(){ return addrs_fullname;}
    public void setaddrs_fullname(String name){ this.addrs_fullname = name;}


    public String getfulladdress(){ return fulladdress;}
    public void setfulladdress(String fulladdress){ this.fulladdress = fulladdress;}

    public String getaddrs_phone(){ return addrs_phone;}
    public void setaddrs_phone(String addrs_phone){ this.addrs_phone = addrs_phone;}



    
}

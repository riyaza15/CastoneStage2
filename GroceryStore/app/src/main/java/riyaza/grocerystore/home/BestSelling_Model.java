package riyaza.grocerystore.home;



public class BestSelling_Model {

    private String prod_id, prod_name, img_ulr,old_price, price,rating;

    public BestSelling_Model(String prod_id, String prod_name, String img_url, String old_price, String price, String rating){

        this.prod_id = prod_id;
       this.prod_name = prod_name;
       this.img_ulr = img_url;
       this.old_price = old_price;
       this.price = price;
       this.rating = rating;
    }

    public String getProd_id(){ return prod_id;}
    public void setProd_id(String id){ this.prod_id = id;}

    public String getProd_name(){ return prod_name;}
    public void setProd_name(String name){ this.prod_name = name;}


    public String getImg_ulr(){ return img_ulr;}
    public void setImg_ulr(String img_ulr){ this.img_ulr = img_ulr;}

    public String getOld_price(){ return old_price;}
    public void setOld_price(String old_price){ this.old_price = old_price;}

    public String getPrice(){ return price;}
    public void setPrice(String price){ this.price= price;}

    public String getRating(){ return rating;}
    public void setRating(String rating){ this.rating = rating;}




}

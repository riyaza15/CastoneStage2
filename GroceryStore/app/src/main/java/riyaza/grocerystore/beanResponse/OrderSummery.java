package riyaza.grocerystore.beanResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderSummery {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Information")
    @Expose
    private Information information;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }


    public class ProdDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("qty")
        @Expose
        private String qty;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

    }

    public class Information {

        @SerializedName("prod_details")
        @Expose
        private List<ProdDetail> prodDetails = null;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;
        @SerializedName("shippingfee")
        @Expose
        private String shippingfee;
        @SerializedName("tax")
        @Expose
        private String tax;
        @SerializedName("ordertotal")
        @Expose
        private String ordertotal;

        public List<ProdDetail> getProdDetails() {
            return prodDetails;
        }

        public void setProdDetails(List<ProdDetail> prodDetails) {
            this.prodDetails = prodDetails;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getShippingfee() {
            return shippingfee;
        }

        public void setShippingfee(String shippingfee) {
            this.shippingfee = shippingfee;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getOrdertotal() {
            return ordertotal;
        }

        public void setOrdertotal(String ordertotal) {
            this.ordertotal = ordertotal;
        }

    }

}
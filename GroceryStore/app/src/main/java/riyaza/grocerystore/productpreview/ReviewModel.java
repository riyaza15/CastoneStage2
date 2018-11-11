package riyaza.grocerystore.productpreview;


public class ReviewModel   {

    private String title, desc, username, date, rating;
    public ReviewModel(String title, String desc, String username, String date, String rating){

        this.title = title;
        this.desc = desc;
        this.username = username;
        this.date = date;
        this.rating = rating;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}

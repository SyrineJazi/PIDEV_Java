package tn.esprit.models;

import java.util.Date;

public class comm {
    private int id;
    private int blog_id;
    private String contenu;
private  Date created_at ;
   // public comm() {
   // }
    //public Blog( int id,String titre, String content, String imageb) {

    public comm(int id, int blog_id, String contenu ,Date created_at) {
        this.id = id;
        this.blog_id = blog_id;
        this.contenu = contenu;
        this.created_at = created_at;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
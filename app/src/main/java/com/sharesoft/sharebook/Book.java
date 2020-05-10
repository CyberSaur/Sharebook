package com.sharesoft.sharebook;

import android.graphics.Bitmap;

public class Book {
    private Bitmap Photo;
    private String Bookname;
    private String Author;
    private String Publisher;
    private String ISBN;
    private String Genre;
    private String Price;
    private String Contact;
    private String Imageid;

    public String getImageid() {
        return Imageid;
    }

    public void setImageid(String imageid) {
        Imageid = imageid;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Book() {
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap photo) {
        Photo = photo;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuler_durden.clientgui;

/**
 *
 * @author Admin
 */

import java.util.List;

public class Library {
    private int id;
    private String name;
    private List<Book> books;
    private List<Reader> readers;

    // Конструкторы, геттеры и сеттеры
    public Library(int id, String name, List<Book> books, List<Reader> readers) {
        this.id = id;
        this.name = name;
        this.books = books;
        this.readers = readers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }
}

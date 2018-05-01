package com.example.daffodil.medidictionary;

import android.graphics.Bitmap;

public class WordData {

    private int id;
    private String word;
    private String definition;
    private String resource;
    private String imageByte;
    private byte[] image;



    public WordData(int id, String word, String definition, String resource) {
        this.id=id;
        this.word = word;
        this.definition = definition;
        this.resource = resource;
    }

    public WordData(int id, String word, String definition, String resource, byte[] image) {
        this.id=id;
        this.word = word;
        this.definition = definition;
        this.resource = resource;
        this.image = image;
    }

    public WordData(int id, String word, String definition, String resource, String imageByte) {
        this.id=id;
        this.word = word;
        this.definition = definition;
        this.resource = resource;
        this.imageByte = imageByte;
    }

    public WordData(int id, String word) {
        this.id=id;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getImageByte() {
        return imageByte;
    }

    public void setImageByte(String imageByte) {
        this.imageByte = imageByte;
    }

}

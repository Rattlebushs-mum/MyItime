package com.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LabelDataSource {
    Context context;

    public LabelDataSource(Context context) {
        this.context = context;
    }

    private ArrayList<Label> labels = new ArrayList<Label>();//不用一直判断是否是null

    public ArrayList<Label> getLabels() {
        return labels;
    }
    public void setLabels(ArrayList<Label> labels) {
        this.labels=labels;
    }
    public void save(){

        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new ObjectOutputStream(context.openFileOutput("LabelSerializable.txt", Context.MODE_PRIVATE)));
            outputStream.writeObject(labels);
            outputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            //AlertDialog.Builder()
        }
    }
    public ArrayList<Label> load(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new ObjectInputStream(context.openFileInput("LabelSerializable.txt")));
            labels = (ArrayList<Label>) inputStream.readObject();
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return labels;
    }
}

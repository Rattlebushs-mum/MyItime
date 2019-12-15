package com.data;

import android.content.Context;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ItemDataSource {
    Context context;

    public ItemDataSource(Context context) {
        this.context = context;
    }

    private ArrayList<EveItem> Items = new ArrayList<EveItem>();//不用一直判断是否是null

    public ArrayList<EveItem> getGoods() {
        return Items;
    }
    public void setGoods(ArrayList<EveItem> items) {
        this.Items=items;
    }
    public void save(){

        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new ObjectOutputStream(context.openFileOutput("Serializable.txt", Context.MODE_PRIVATE)));
            outputStream.writeObject(Items);
            outputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            //AlertDialog.Builder()
        }
    }
    public ArrayList<EveItem> load(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new ObjectInputStream(context.openFileInput("Serializable.txt")));
            Items = (ArrayList<EveItem>) inputStream.readObject();
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Items;
    }
}

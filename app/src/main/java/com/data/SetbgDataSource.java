package com.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;

public class SetbgDataSource {
    Context context;

    public SetbgDataSource(Context context) {
        this.context = context;
    }

    private SetBg setBg = new SetBg();//不用一直判断是否是null

    public SetBg getSet() {
        return setBg;
    }

    public void save(){

        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new ObjectOutputStream(context.openFileOutput("SetbgSerializable.txt", Context.MODE_PRIVATE)));
            outputStream.writeObject(setBg);
            outputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            //AlertDialog.Builder()
        }
    }
    public SetBg load(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new ObjectInputStream(context.openFileInput("SetbgSerializable.txt")));
            setBg = (SetBg) inputStream.readObject();
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (setBg==null){
            new SetBg();
        }
        return setBg;
    }
}

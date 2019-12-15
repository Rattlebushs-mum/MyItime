package com.example.myitime.ui.gallery;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.data.Label;
import com.data.LabelDataSource;
import com.example.myitime.NewActivity;
import com.example.myitime.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private ListView listView;
    private EditText metlb;
    private View root,dialog_new;
    private ArrayList<String> labels;
    private ArrayList<Label> label;
    private LabelDataSource labelDataSource;
    private ArrayAdapter<String> stringArrayAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        labelDataSource=new LabelDataSource(root.getContext());
        labels=new ArrayList<String>();
        label=labelDataSource.load();
        init(label);
        listView= (ListView)root.findViewById(R.id.label_list_view);
        stringArrayAdapter=new ArrayAdapter<String>(
                root.getContext(),android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(stringArrayAdapter);
        registerForContextMenu(listView);
        return root;


    }
    void init(ArrayList<Label> label){
        if(label.size()==0){
            labels.add("生日");
            labels.add("纪念日");
            labels.add("节日");
            labels.add("长按添加、删除标签");
            for (String a:labels){
                label.add(new Label(a));
            }
            labelDataSource.setLabels(label);
            labelDataSource.save();
        }else{
            for(Label a:label){
                labels.add(a.getTitle());
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v == root.findViewById(R.id.label_list_view)) {
            menu.add(0, 1, 0, "新建");
            menu.add(0, 2, 0, "删除");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1: {

                dialog_new = LayoutInflater.from(root.getContext()).inflate(R.layout.label_dialog, null);
                metlb=dialog_new.findViewById(R.id.lb_new);
                AlertDialog.Builder builder=new AlertDialog.Builder(root.getContext());
                builder.setView(dialog_new).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String temp=metlb.getText().toString();
                        if (temp.length()!=0){
                            label.add(menuInfo.position,new Label(temp));
                            labels.add(menuInfo.position,temp);
                            labelDataSource.setLabels(label);
                            labelDataSource.save();
                            stringArrayAdapter.notifyDataSetChanged();
                        }
                    }
                }).setTitle("     ").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
            break;
            case 2: {
                final int[] itemposition = {menuInfo.position};
                new AlertDialog.Builder(root.getContext())
                        .setTitle("确认删除")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                label.remove(menuInfo.position);
                                labels.remove(menuInfo.position);
                                labelDataSource.setLabels(label);
                                labelDataSource.save();
                                stringArrayAdapter.notifyDataSetChanged();
                                Toast.makeText(root.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
            }
            break;
        }
        return super.onContextItemSelected(item);

    }
}
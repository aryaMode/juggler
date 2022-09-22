//First Commit From Ubuntu
package com.uzumaki.haxorus;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText txtinput;
    Button btn1, btn3, btn4;
    ListView listview;
    Context ctx = this;
    AdView madView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allocateMemory();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setEnabled(false);
        setAction();
        AdRequest adRequest = new AdRequest.Builder().build();//.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)(For virtual device)
        madView.loadAd(adRequest);
    }
    private void setEnabled(boolean enable) {
        Drawable d= (Drawable)getResources().getDrawable(R.drawable.rounded_button);
        Drawable g= (Drawable)getResources().getDrawable(R.drawable.grounded_button);
        btn3.setEnabled(enable);
        btn4.setEnabled(enable);
        if (enable) {

            btn3.setTextColor(Color.WHITE);
            btn4.setTextColor(Color.WHITE);
            btn3.setBackgroundDrawable(d);
            btn4.setBackgroundDrawable(d);
            return;
        }

        btn3.setTextColor(Color.DKGRAY);
        btn4.setTextColor(Color.DKGRAY);

        btn3.setBackgroundDrawable(g);
        btn4.setBackgroundDrawable(g);
    }

    private ArrayList<String> split(String s) {
        ArrayList<String> list = new ArrayList<>();
        Scanner sc = new Scanner(s);
        while (sc.hasNextLine()) {
            String line = capitalize(sc.nextLine().trim());
            if (!line.equals(""))
                list.add(line);
        }
        return list;
    }

    private String capitalize(String s)
    {
        String output="";
        String [] words = s.split(" ");
        for(String x:words)
            output+=Character.toUpperCase(x.charAt(0))+x.substring(1).toLowerCase()+" ";
        return output;
    }
    private void addItem() {
        String s = txtinput.getText().toString();
        if (!s.equals("")) {
            list.addAll(split(s));
            adapter.notifyDataSetChanged();
            txtinput.setText("");
            setEnabled(true);
        } else
            Toast.makeText(ctx, "Please Enter Text", Toast.LENGTH_LONG).show();
    }

    private void setAction() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabled(false);
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.custom_dialog_answer);
                TextView t = (TextView) dialog.findViewById(R.id.txtcdamsg);
                t.setText(randomize());
                dialog.findViewById(R.id.btncdamsg).setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.custom_dialog_delete);
                dialog.findViewById(R.id.btncddcancel).setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btncdddelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        if (list.isEmpty()) {
                            setEnabled(false);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private String randomize() {
        return list.get(new Random().nextInt(list.size()));
    }

    private void allocateMemory() {
        MobileAds.initialize(this, "ca-app-pub-6553397552505779~9884389806");
       // MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        madView = (AdView) findViewById(R.id.adView);
        txtinput = (EditText) findViewById(R.id.txtinput);
        btn1 = (Button) findViewById(R.id.btn1);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(ctx, R.layout.list_item, R.id.txtitem, list);
        listview.setAdapter(adapter);

    }

}

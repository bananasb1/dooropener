/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package door.opener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class IndexActivity extends Activity {

   Global gb;
   Spinner dropdown;
   boolean statusDoor = false;

   /**
    * Called when the activity is first created.
    *
    * @param savedInstanceState
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      gb = Global.getInstance();
      setContentView(screen());

      final Handler h = new Handler();
      h.postDelayed(new Runnable() {
         public void run() {
            gb.http_status = "update_status";
            gb.connect_server(gb.link_update, gb.http_status, null);
            h.postDelayed(this, 2000);
         }
      }, 2000);
   }

   LinearLayout screen() {

      LinearLayout layout = new LinearLayout(this);
      layout.setLayoutParams(new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT));
      layout.setOrientation(LinearLayout.VERTICAL);
      layout.setGravity(Gravity.CENTER_HORIZONTAL);
      layout.setBackgroundResource(R.color.light_gray);

      TextView txt_WelcomeUser = new TextView(this);
      txt_WelcomeUser.setLayoutParams(new LinearLayout.LayoutParams(
              gb.displayWidth(60),
              LinearLayout.LayoutParams.WRAP_CONTENT));
      txt_WelcomeUser.setTextSize(18);
      txt_WelcomeUser.setText("  Welcome ".concat(gb.username));
      txt_WelcomeUser.setTextColor(Color.BLACK);
      txt_WelcomeUser.setTypeface(Typeface.SERIF, Typeface.BOLD);

      dropdown = new Spinner(this);
      gb.arrayListIndex = new ArrayList<String>();
      gb.arrayListIndex.addAll(Arrays.asList(gb.devices));

      ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gb.arrayListIndex);
      dropdown.setAdapter(adp);
      dropdown.setLayoutParams(new LinearLayout.LayoutParams(gb.displayWidth(80), gb.displayHeight(7)));

      ScrollView scroll = new ScrollView(this);
      scroll.setLayoutParams(new LinearLayout.LayoutParams(
              gb.displayWidth(80),
              gb.displayHeight(35)));
      scroll.setBackgroundResource(R.drawable.style2);

      gb.txt_Log = new TextView(this);
      gb.txt_Log.setLayoutParams(new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.WRAP_CONTENT));
      gb.txt_Log.setPadding(gb.displayWidth(1), gb.displayWidth(1), gb.displayWidth(1), gb.displayWidth(1));
      gb.txt_Log.setText("Log status");
      gb.txt_Log.setTextColor(Color.BLUE);

      scroll.addView(gb.txt_Log);

      LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
              gb.displayWidth(50), LinearLayout.LayoutParams.WRAP_CONTENT);
      p.setMargins(0, gb.displayHeight(20), 0, 0);

      LinearLayout.LayoutParams b = new LinearLayout.LayoutParams(
              gb.displayWidth(50), LinearLayout.LayoutParams.WRAP_CONTENT);
      b.setMargins(0, gb.displayHeight(1), 0, 0);

      Button btn_Logout = new Button(this);
      btn_Logout.setLayoutParams(p);
//      btn_Logout.setBackgroundResource(R.drawable.style2);
      btn_Logout.setText("Logout");

      Button btn_Setting = new Button(this);
      btn_Setting.setLayoutParams(b);
//      btn_Setting.setBackgroundResource(R.drawable.style2);
      btn_Setting.setText("Setting");

      GridLayout layout_Top = new GridLayout(this);
      layout_Top.setLayoutParams(new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.WRAP_CONTENT,
              LinearLayout.LayoutParams.WRAP_CONTENT));
      layout_Top.setBackgroundColor(Color.rgb(231, 106, 129));
      layout_Top.setColumnCount(2);
      layout_Top.setRowCount(1);

      layout_Top.addView(txt_WelcomeUser);
      layout_Top.addView(dropdown);

      LinearLayout.LayoutParams img_params = new LinearLayout.LayoutParams(
              gb.displayWidth(40),
              gb.displayWidth(40));
      img_params.setMargins(0, gb.displayHeight(25), 0, 0);

      final ImageView btn_Open = new ImageView(this);
      btn_Open.setLayoutParams(img_params);
      btn_Open.setBackgroundResource(R.drawable.open);
      btn_Open.setOnClickListener(new OnClickListener() {
         public void onClick(View arg0) {
            String st = "0";
            if (statusDoor) {
               st = "0";
               statusDoor = false;
               btn_Open.setBackgroundResource(R.drawable.open);
            } else {
               st = "1";
               statusDoor = true;
               btn_Open.setBackgroundResource(R.drawable.close);
            }
            String doorx = dropdown.getSelectedItem().toString();
            String tag1 = "doorname=" + doorx;
            String tag2 = "status=" + st;
            gb.http_status = "update_status_door";
            gb.connect_server(gb.link_update, gb.http_status, new String[]{tag1, tag2});

         }
      });
      layout.addView(layout_Top);
      //layout.addView(scroll);
      layout.addView(btn_Open);
      layout.addView(btn_Logout);
      layout.addView(btn_Setting);

      //     btn_Open.setOnClickListener(new OnClickListener() {
      //        public void onClick(View arg0) {
      //           String devicename = dropdown.getItemAtPosition(0).toString();
      //           gb.http_status = "setdoor_status";
      //           gb.connect_server(gb.link_update, gb.http_status, new String[]{
      //              "doorname=".concat(devicename)
      //           });
      //       }
      //    });
      btn_Setting.setOnClickListener(new OnClickListener() {
         public void onClick(View arg0) {
            Intent intent = new Intent(gb.getContext(), ManagedActivity.class);
            gb.getContext().startActivity(intent);
         }
      });

      btn_Logout.setOnClickListener(new OnClickListener() {
         public void onClick(View arg0) {
            signOut();
         }
      });

      return layout;
   }

   void signOut() {
      Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra("finish", true);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
      startActivity(intent);
      finish();
   }

}

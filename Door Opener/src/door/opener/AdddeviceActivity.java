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
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import static android.view.View.GONE;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import static door.opener.Global.context;

public class AdddeviceActivity extends Activity {

   Global gb;

   //===============================================================
   /**
    * Called when the activity is first created.
    *
    * @param icicle
    */
   @Override
   public void onCreate(Bundle icicle) {
      super.onCreate(icicle);
      gb = Global.getInstance();
      setContentView(screen());
   }

   //===============================================================
   LinearLayout screen() {
      LinearLayout layout = new LinearLayout(this);
      layout.setLayoutParams(new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT));
      layout.setOrientation(LinearLayout.VERTICAL);
      layout.setGravity(Gravity.CENTER);
      layout.setBackgroundResource(R.color.light_gray);

      ImageView Logo = new ImageView(this);
      Logo.setLayoutParams(new LinearLayout.LayoutParams(
              gb.displayWidth(85),
              gb.displayWidth(43)));
      Logo.setBackgroundResource(R.drawable.logo);

      LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
              gb.displayWidth(60), LinearLayout.LayoutParams.WRAP_CONTENT
      );
      TextView txt_AddDevice = new TextView(this);
      txt_AddDevice.setLayoutParams(txt_params);
      txt_AddDevice.setTextSize(36);
      txt_AddDevice.setTypeface(Typeface.DEFAULT_BOLD);
      txt_AddDevice.setTextColor(Color.rgb(231, 106, 129));

      LinearLayout.LayoutParams inp_params = new LinearLayout.LayoutParams(
              gb.displayWidth(94),
              LinearLayout.LayoutParams.WRAP_CONTENT);
      inp_params.setMargins(0, gb.displayHeight(2), 0, 0);

      final EditText txtbox_DeviceName = new EditText(this);
      txtbox_DeviceName.setBackgroundResource(R.layout.style);
      txtbox_DeviceName.setHint("Device's name");
      txtbox_DeviceName.setInputType(InputType.TYPE_CLASS_TEXT);
      txtbox_DeviceName.setLayoutParams(inp_params);

      final EditText txtbox_IP = new EditText(this);
      txtbox_IP.setBackgroundResource(R.layout.style);
      txtbox_IP.setHint("IP");
      txtbox_IP.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
      txtbox_IP.setLayoutParams(inp_params);
      txtbox_IP.setText(" ");
      txtbox_IP.setVisibility(GONE);
      LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(
              gb.displayWidth(95),
              gb.displayHeight(10));
      btn_params.setMargins(0, gb.displayHeight(2), 0, 0);
      Button btn_Add = new Button(this);
      btn_Add.setLayoutParams(btn_params);
      btn_Add.setText("Add");

      layout.addView(Logo);
      layout.addView(txt_AddDevice);
      layout.addView(txtbox_DeviceName);
      layout.addView(txtbox_IP);
      layout.addView(btn_Add);

      btn_Add.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg0) {
            String device_name = txtbox_DeviceName.getText().toString();
            String device_ip = txtbox_IP.getText().toString();
            if (device_name.equals("") || device_ip.equals("")) {
               gb.show_popup("invalid device name or ip");
               return;
            }
            gb.http_status = "adddevice";
            gb.connect_server(gb.link_update, gb.http_status, new String[]{
               "username=".concat(gb.username),
               "device_name=".concat(device_name),
               "device_ip=".concat(device_ip)
            });
            Intent intent = new Intent(getApplication(), IndexActivity.class);
            context.startActivity(intent);
            finish();
         }
      });

      return layout;
   }
}

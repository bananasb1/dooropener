package door.opener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class ManagedActivity extends Activity {

   Global gb;
   Spinner dropdown;
   ArrayList<String> arrayList1;
   boolean isFinish = false;
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

   @Override
   protected void onResume() {
      super.onResume(); //To change body of generated methods, choose Tools | Templates.

   }

   //===============================================================
   LinearLayout screen() {
      LinearLayout layout = new LinearLayout(this);
      layout.setLayoutParams(new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT));
      layout.setOrientation(LinearLayout.VERTICAL);
      layout.setGravity(Gravity.CENTER_HORIZONTAL);
      layout.setBackgroundResource(R.color.light_gray);

      dropdown = new Spinner(this);

      arrayList1 = new ArrayList<String>();
      arrayList1.addAll(Arrays.asList(gb.devices));

      ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList1);
      dropdown.setAdapter(adp);
      dropdown.setLayoutParams(new LinearLayout.LayoutParams(gb.displayWidth(95), gb.displayHeight(10)));

      LinearLayout.LayoutParams btn1_params = new LinearLayout.LayoutParams(
              gb.displayWidth(95),
              gb.displayHeight(10));
      //btn1_params.setMargins(0, gb.displayHeight(2), 0, gb.displayHeight(1));
      Button btn_Add = new Button(this);
      btn_Add.setLayoutParams(btn1_params);
      btn_Add.setText("Add");

      Button btn_Delete = new Button(this);
      btn_Delete.setLayoutParams(btn1_params);
      btn_Delete.setText("Delete");

      LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
              gb.displayWidth(50), LinearLayout.LayoutParams.WRAP_CONTENT
      );
      TextView txt_devices = new TextView(this);
      txt_devices.setLayoutParams(txt_params);
      txt_devices.setTextSize(36);
      txt_devices.setTypeface(Typeface.DEFAULT_BOLD);
      txt_devices.setTextColor(Color.rgb(231, 106, 129));
      txt_devices.setText("Devices");

      layout.addView(txt_devices);
      layout.addView(dropdown);
      layout.addView(btn_Add);
      layout.addView(btn_Delete);

      btn_Add.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg0) {
            Intent intent = new Intent(gb.getContext(), AdddeviceActivity.class);
            gb.getContext().startActivity(intent);
            isFinish = true;
         }
      });

      btn_Delete.setOnClickListener(
              new Button.OnClickListener() {
                 public void onClick(View v) {
                    String devicename = dropdown.getSelectedItem().toString();
                    gb.http_status = "deletedevice";
                    gb.connect_server(gb.link_update, gb.http_status, new String[]{
                       "username=".concat(gb.username),
                       "device_name=".concat(devicename)
                    });
                    finish();
                 }
              });

      return layout;
   }
}

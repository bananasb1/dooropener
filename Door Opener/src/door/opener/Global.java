package door.opener;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Arrays;

//==================================================================
//==================================================================
public class Global {

   static Global instance;
   static DisplayMetrics displaymetrics;
   static int display_width;
   static int display_height;
   static Context context;

   public AsyncHttpClient httpClient;
   public RequestParams TagParams;
   //public String login_link = "http://dooropener.96.lt/action.php";
   public String link_update = "http://103.58.148.244/~dooropen/org/link_update.php";
   public String http_status;
   public String[] devices;
   public String username;
   public ArrayList<String> arrayListIndex;
   public TextView txt_Log;

   //===============================================================
   public static synchronized Global getInstance() {
      if (instance == null) {
         instance = new Global();
         displaymetrics = new DisplayMetrics();
      }
      return instance;
   }

   //===============================================================
   public DisplayMetrics getDisplayMetrics() {
      return displaymetrics;
   }

   //===============================================================
   public void initialResolution() {
      display_width = displaymetrics.widthPixels;
      display_height = displaymetrics.heightPixels;
   }

   //===============================================================
   public void setContext(Context ext_context) {
      context = ext_context;
   }

   //===============================================================
   public Context getContext() {
      return context;
   }

   //===============================================================
   public int displayWidth(float percent) {
      return (int) ((display_width * percent) / 100);
   }

   //===============================================================
   public int displayHeight(float percent) {
      return (int) ((display_height * percent) / 100);
   }

   //===============================================================
   void connect_server(String link, String command, String[] tag_data) {
      TagParams.put("command", command);
      if (tag_data != null) {
         for (String data : tag_data) {
            String[] tag = data.split("=");
            TagParams.put(tag[0], tag[1]);
         }
      }
      httpClient.post(context, link, TagParams, AsyncHandler());
   }

   //===============================================================
   AsyncHttpResponseHandler AsyncHandler() {
      return new AsyncHttpResponseHandler() {
         @Override
         public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String msg_respond = new String(bytes);
            /////////////////////////////ถ้า login_ok ก้ไปดึง device มาจาก managedevice////////////////////////////////
            if (http_status.equals("login")) {
               show_popup(msg_respond);
               if (msg_respond.equals("login_ok")) {
                  http_status = "managedevice";
                  connect_server(link_update, http_status, new String[]{"username=".concat(username)});
                  show_popup("getting devices from server");
               }
               ///////////////////////////////Check sensor/////////////////////////////////////////////
               //    } else if (http_status.equals("check_sensor")) {
               //         show_popup(msg_respond);
               //           if (msg_respond.equals("Door_OPENED")) {
               //                show_popup("Door Opened!");
//               }
               /////////////////////////////ดึงdeviceมาshow////////////////////////////////
            } else if (http_status.equals("managedevice")) {
               //show_popup(msg_respond);
               String[] devices_list = msg_respond.split("<br><br>");
               devices = new String[devices_list.length];
               int ix = 0;
               //String msg = "";
               for (String dev : devices_list) {
                  if (dev != null && !"".equals(dev)) {
                     String[] d = dev.split("<br>");
                     devices[ix++] = d[0];
                     //msg += d[0] + "\n";
                  }
               }
               //show_popup(msg);
               Intent intent = new Intent(getContext(), IndexActivity.class);
               context.startActivity(intent);
               /////////////////////////////Add device เข้าสู่ list/////////////////////////////////////////////////

            } else if (http_status.equals("managedevice1")) {
               String[] devices_list = msg_respond.split("<br><br>");
               devices = new String[devices_list.length];
               int ix = 0;
               //String msg = "";
               for (String dev : devices_list) {
                  if (dev != null && !"".equals(dev)) {
                     String[] d = dev.split("<br>");
                     devices[ix++] = d[0];
                     //msg += d[0] + "\n";
                  }
               }
               arrayListIndex.removeAll(arrayListIndex);
               arrayListIndex.addAll(Arrays.asList(devices));
               show_popup("finish");
               /////////////////////////////add device แล้วให้ไป show ที่ list โดยจากนี้ไปเชคที่ managedevice1/////////////////////////////////

            } else if (http_status.equals("adddevice")) {
               http_status = "managedevice1";
               connect_server(link_update, "managedevice", new String[]{"username=".concat(username)});
               //show_popup("getting devices from server");

               //show_popup(msg_respond);
               ///////////////////////////////////////////////////////////////////////////////////////////
            } else if (http_status.equals("deletedevice")) {
               //show_popup(msg_respond);
               if (msg_respond.equals("delete_failed")) {
                  show_popup(msg_respond);
               } else {
                  String[] devices_list = msg_respond.split("<br><br>");
                  devices = new String[devices_list.length];
                  int ix = 0;
                  //String msg = "";
                  for (String dev : devices_list) {
                     if (dev != null && !"".equals(dev)) {
                        String[] d = dev.split("<br>");
                        devices[ix++] = d[0];
                        //msg += d[0] + "\n";
                     }
                  }
                  arrayListIndex.removeAll(arrayListIndex);
                  arrayListIndex.addAll(Arrays.asList(devices));
               }
            } else if (http_status.equals("adduser")) {
               show_popup(msg_respond);
            } else if (http_status.equals("update_status")) {
               if (!msg_respond.equals("empty")) {
                  String[] devices_list = msg_respond.split("<br><br>");
                  String msg = "";
                  for (String dev : devices_list) {
                     if (dev != null && !"".equals(dev)) {
                        msg = msg.concat(dev).concat(" opened");
                     }
                  }
                  txt_Log.setText(msg);
               } else {
                  txt_Log.setText("Log status");
               }
            }
         }

         @Override
         public void onFailure(int i, Header[] headers, byte[] bytes, Throwable thrwbl) {

         }
      };
   }

   public void show_popup(String message) {

      Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
      t.show();
      t.setGravity(Gravity.FILL_HORIZONTAL, 50, 170);

      //     Toast.makeText(
      //           context,
      //         message,
      //       Toast.LENGTH_SHORT).show();
   }
}
//==================================================================

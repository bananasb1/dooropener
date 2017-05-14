package door.opener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {

   Global gb;

   //===============================================================
   /**
    * Called when the activity is first created.
    *
    * @param savedInstanceState
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      gb = Global.getInstance();
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      getWindowManager().getDefaultDisplay().getMetrics(gb.getDisplayMetrics());
      gb.initialResolution();
      gb.setContext(this);
      gb.http_status = "";

      gb.httpClient = new AsyncHttpClient();
      gb.TagParams = new RequestParams();

      setContentView(Login_screen());
   }

   //===============================================================
   LinearLayout Login_screen() {
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

      LinearLayout.LayoutParams msg_params = new LinearLayout.LayoutParams(
              gb.displayWidth(25),
              LinearLayout.LayoutParams.WRAP_CONTENT);

      LinearLayout.LayoutParams inp_params = new LinearLayout.LayoutParams(
              gb.displayWidth(55),
              LinearLayout.LayoutParams.WRAP_CONTENT);

      TextView msg_user = new TextView(this);
      msg_user.setLayoutParams(msg_params);
      msg_user.setTextColor(Color.BLACK);
      msg_user.setText("username");
      TextView msg_pass = new TextView(this);
      msg_pass.setLayoutParams(msg_params);
      msg_pass.setTextColor(Color.BLACK);
      msg_pass.setText("password");

      final EditText inp_user = new EditText(this);
      inp_user.setLayoutParams(inp_params);
      inp_user.setBackgroundResource(R.drawable.style);
      inp_user.setInputType(InputType.TYPE_CLASS_TEXT);

      final EditText inp_pass = new EditText(this);
      inp_pass.setLayoutParams(inp_params);
      inp_pass.setBackgroundResource(R.drawable.style);
      inp_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

      inp_user.setText("untika");
      inp_pass.setText("1234567");

      LinearLayout.LayoutParams slip_params = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.WRAP_CONTENT,
              LinearLayout.LayoutParams.WRAP_CONTENT);
      slip_params.setMargins(0, gb.displayHeight(1), 0, gb.displayHeight(1));

      LinearLayout slip1 = new LinearLayout(this);
      slip1.setLayoutParams(slip_params);
      LinearLayout slip2 = new LinearLayout(this);
      slip2.setLayoutParams(slip_params);
      LinearLayout slip3 = new LinearLayout(this);
      slip3.setLayoutParams(slip_params);

      LinearLayout.LayoutParams msg_params2 = new LinearLayout.LayoutParams(
              gb.displayWidth(30),
              LinearLayout.LayoutParams.WRAP_CONTENT);

      TextView msg_login = new TextView(this);
      msg_login.setLayoutParams(msg_params2);
      msg_login.setTextColor(Color.BLACK);
      msg_login.setText("Login");
      msg_login.setGravity(Gravity.CENTER);
      TextView msg_space = new TextView(this);
      msg_space.setTextColor(Color.BLACK);
      msg_space.setText("  |  ");
      TextView msg_register = new TextView(this);
      msg_register.setLayoutParams(msg_params2);
      msg_register.setTextColor(Color.BLACK);
      msg_register.setText("Register");
      msg_register.setGravity(Gravity.CENTER);

      slip1.addView(msg_user);
      slip1.addView(inp_user);
      slip2.addView(msg_pass);
      slip2.addView(inp_pass);
      slip3.addView(msg_login);
      slip3.addView(msg_space);
      slip3.addView(msg_register);

      layout.addView(Logo);
      layout.addView(slip1);
      layout.addView(slip2);
      layout.addView(slip3);

      msg_login.setOnClickListener(new OnClickListener() {
         public void onClick(View arg0) {
            String username = inp_user.getText().toString();
            String password = inp_pass.getText().toString();
            if (username.equals("") || password.equals("")) {
               gb.show_popup("invalid username or password...");
               return;
            }
            gb.http_status = "login";
            gb.username = username;
            gb.connect_server(gb.link_update, gb.http_status, new String[]{
               "username=".concat(username),
               "password=".concat(password)
            });
            gb.show_popup("connecting to server");
         }
      });

      msg_register.setOnClickListener(new OnClickListener() {
         public void onClick(View arg0) {
            Intent intent = new Intent(gb.getContext(), SettingActivity.class);
            gb.getContext().startActivity(intent);
         }
      });

      return layout;
   }

   //===============================================================
   public void startIndex() {
      Intent intent = new Intent(gb.getContext(), IndexActivity.class);
      startActivity(intent);
   }
	//===============================================================
}

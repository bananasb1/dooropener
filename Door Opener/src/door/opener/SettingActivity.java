package door.opener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends Activity {

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
      layout.setGravity(Gravity.CENTER_HORIZONTAL);
      layout.setBackgroundResource(R.color.light_gray);

      LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
              gb.displayWidth(100), LinearLayout.LayoutParams.WRAP_CONTENT
      );

      TextView txt_Account = new TextView(this);
      txt_Account.setLayoutParams(txt_params);
      txt_Account.setTextSize(36);
      txt_Account.setTypeface(Typeface.DEFAULT_BOLD);
      txt_Account.setTextColor(Color.rgb(231, 106, 129));
      txt_Account.setText("    Create account");

      LinearLayout.LayoutParams inp_params = new LinearLayout.LayoutParams(
              gb.displayWidth(50), LinearLayout.LayoutParams.WRAP_CONTENT
      );
      inp_params.setMargins(0, gb.displayHeight(2), 0, 0);

      final EditText txtbox_username = new EditText(this);
      txtbox_username.setLayoutParams(inp_params);
      txtbox_username.setBackgroundResource(R.layout.style);
      txtbox_username.setHint("Username");

      final EditText txtbox_password = new EditText(this);
      txtbox_password.setLayoutParams(inp_params);
      txtbox_password.setBackgroundResource(R.layout.style);
      txtbox_password.setHint("Password");
      txtbox_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

      final EditText txtbox_confirmpassword = new EditText(this);
      txtbox_confirmpassword.setLayoutParams(inp_params);
      txtbox_confirmpassword.setBackgroundResource(R.layout.style);
      txtbox_confirmpassword.setHint("Confirm password");
      txtbox_confirmpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

      final EditText txtbox_email = new EditText(this);
      txtbox_email.setLayoutParams(inp_params);
      txtbox_email.setBackgroundResource(R.layout.style);
      txtbox_email.setHint("E-mail");

      LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(gb.displayWidth(50), LinearLayout.LayoutParams.WRAP_CONTENT);
      btn_params.setMargins(0, gb.displayHeight(2), 0, 0);
      Button btn_signup = new Button(this);
      btn_signup.setLayoutParams(btn_params);
      btn_signup.setText("Add");

      layout.addView(txt_Account);
      layout.addView(txtbox_username);
      layout.addView(txtbox_password);
      layout.addView(txtbox_confirmpassword);
      layout.addView(txtbox_email);
      layout.addView(btn_signup);

      btn_signup.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg0) {
            String username = txtbox_username.getText().toString();
            String password = txtbox_password.getText().toString();
            String confirm_password = txtbox_confirmpassword.getText().toString();
            String e_mail = txtbox_email.getText().toString();

            Pattern pattern = Pattern.compile("\\p{Alnum}+");
            Matcher matcher = pattern.matcher(username);

            if (!matcher.matches()) {
               gb.show_popup("only letter and digits");
               return;
            } else if (username.equals("") || password.equals("") || confirm_password.equals("") || e_mail.equals("")) {
               gb.show_popup("Fill in the blanks!");
               return;
            } else if (username.length() >= 11 || username.length() <= 5) {
               gb.show_popup("Username must be greater than 5 and less than 10");
               return;
            } else if (!password.equals(confirm_password)) {
               gb.show_popup("Password does not match");
               return;
            } else {
               gb.show_popup("Please login again ");
            }

            //           if (!password.equals(confirm_password)) {
            //            gb.show_popup("Password not matchhh");
            //          return;
            //     } else {
            //      gb.show_popup("Please login..");
            // }
            //else {
            // gb.show_popup("Password not matchhh");
            // return;
            //}
            gb.http_status = "adduser";
            gb.connect_server(gb.link_update, gb.http_status, new String[]{
               "username=".concat(username),
               "password=".concat(password),
               "confirm_password=".concat(confirm_password),
               "e_mail=".concat(e_mail)
            });

            finish();
         }
      });

      return layout;
   }

	//===============================================================
}

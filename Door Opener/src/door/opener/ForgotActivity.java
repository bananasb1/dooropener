package door.opener;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForgotActivity extends Activity {

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
		// ToDo add your GUI initialization code here
	}

	//===============================================================
	LinearLayout screen() {
		LinearLayout เลย์เอ้าท์ = new LinearLayout(this);
		เลย์เอ้าท์.setLayoutParams(new LinearLayout.LayoutParams(
				  LinearLayout.LayoutParams.MATCH_PARENT,
				  LinearLayout.LayoutParams.MATCH_PARENT));
		เลย์เอ้าท์.setOrientation(LinearLayout.VERTICAL);
		เลย์เอ้าท์.setGravity(Gravity.CENTER);
		เลย์เอ้าท์.setBackgroundResource(R.color.light_gray);

		LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
				  gb.displayWidth(80), LinearLayout.LayoutParams.WRAP_CONTENT
		);

		TextView ข้อความ_forgotpassword = new TextView(this);
		ข้อความ_forgotpassword.setLayoutParams(txt_params);
		ข้อความ_forgotpassword.setTextSize(36);
		ข้อความ_forgotpassword.setTypeface(Typeface.DEFAULT_BOLD);
		ข้อความ_forgotpassword.setTextColor(Color.rgb(231, 106, 129));
		ข้อความ_forgotpassword.setText("Forgot password");

		LinearLayout.LayoutParams inp_params = new LinearLayout.LayoutParams(
				  gb.displayWidth(50), LinearLayout.LayoutParams.WRAP_CONTENT
		);
		inp_params.setMargins(0, gb.displayHeight(2), 0, 0);

		EditText กล่อง_username = new EditText(this);
		กล่อง_username.setLayoutParams(inp_params);
		กล่อง_username.setBackgroundResource(R.layout.style);
		กล่อง_username.setHint("Username");

		LinearLayout.LayoutParams btn1_params = new LinearLayout.LayoutParams(gb.displayWidth(100), LinearLayout.LayoutParams.WRAP_CONTENT);
		Button ปุ่ม_Sent = new Button(this);
		ปุ่ม_Sent.setLayoutParams(btn1_params);
		ปุ่ม_Sent.setText("Sent");

		LinearLayout.LayoutParams btn2_params = new LinearLayout.LayoutParams(gb.displayWidth(100), LinearLayout.LayoutParams.WRAP_CONTENT);
		Button ปุ่ม_back = new Button(this);
		ปุ่ม_back.setLayoutParams(btn2_params);
		ปุ่ม_back.setText("<-");

		return เลย์เอ้าท์;
	}
	//===============================================================
}

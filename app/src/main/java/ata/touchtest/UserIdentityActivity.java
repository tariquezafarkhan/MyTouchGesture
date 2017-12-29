package ata.touchtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserIdentityActivity extends Activity{
	
	public static String curname="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_identity);
		
		Button b = (Button) findViewById(R.id.btn_userdata);
		b.setOnClickListener(new View.OnClickListener() {
      
         public void onClick(View v) {
         // here i call new screen;
         Intent i = new Intent(UserIdentityActivity.this, TouchScreenActivity.class);
         curname=((EditText)findViewById(R.id.editText1)).getText().toString();
         startActivity(i);
         } 
      });
	}

}

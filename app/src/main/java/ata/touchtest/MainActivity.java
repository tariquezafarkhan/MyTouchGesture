package ata.touchtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
			/*Button b = (Button) findViewById(R.id.btn_getdata);
			b.setOnClickListener(new View.OnClickListener() {
	      
	         public void onClick(View v) {
	         // here i call new screen;
	         Intent i = new Intent(MainActivity.this, UserIdentityActivity.class);
	         startActivity(i);
	         } 
	      }); */
			
			Button bsd = (Button) findViewById(R.id.btn_showdata);
			bsd.setOnClickListener(new View.OnClickListener() {
	      
	         public void onClick(View v) {
	         // here i call new screen;
	         Intent i = new Intent(MainActivity.this, ShowUserDataActivity.class);
	         startActivity(i);
	         } 
	      });
			
		/*	Button btn = (Button) findViewById(R.id.btn_userValidte);
			btn.setOnClickListener(new View.OnClickListener() {
	      
	         public void onClick(View v) {
	         // here i call new screen;
	         Intent i = new Intent(MainActivity.this, VerifyScreenActivity.class);
	         startActivity(i);
	         } 
	      });*/
			
			Button bxn = (Button) findViewById(R.id.btn_exportData);
			bxn.setOnClickListener(new View.OnClickListener() {
	      
	         public void onClick(View v) {
	         // here i call new screen;
	         Intent i = new Intent(MainActivity.this, ExportDataActivity.class);
	         startActivity(i);
	         } 
	      });
			
//			Button bcls = (Button) findViewById(R.id.btn_classify);
//			bcls.setOnClickListener(new View.OnClickListener() {
//	      
//	         public void onClick(View v) {
//	         // here i call new screen;
//	         Intent i = new Intent(MainActivity.this, ClassifierActivity.class);
//	         startActivity(i);
//	         } 
//	      });
			
			Button bg = (Button) findViewById(R.id.btn_getGesture);
			bg.setOnClickListener(new View.OnClickListener() {
	      
	         public void onClick(View v) {
	         // here i call new screen;
	         Intent i = new Intent(MainActivity.this, GestureBuilderActivity.class);
	         startActivity(i);
	         } 
	      });
			
//			Button bv = (Button) findViewById(R.id.btn_verification);
//			bv.setOnClickListener(new View.OnClickListener() {
//	      
//	         public void onClick(View v) {
//	         // here i call new screen;
//	         Intent i = new Intent(MainActivity.this, GestureTest.class);
//	         startActivity(i);
//	         } 
//	      });
	}

}

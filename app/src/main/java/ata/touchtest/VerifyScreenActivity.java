package ata.touchtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class VerifyScreenActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(new VerifyTouchEventView(this, null));
	  }
	  
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.layout.verifyscreen, menu);
	    return true;
	  }
}

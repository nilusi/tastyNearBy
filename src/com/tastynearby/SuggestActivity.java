
package com.tastynearby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tastynearby.LoginActivity;
import com.tastynearby.R;
import com.tastynearby.library.UserFunctions;

public class SuggestActivity extends Activity {
	UserFunctions userFunctions;
	Button btnLogout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        	setContentView(R.layout.suggest_rest);
        	
     
    }
}
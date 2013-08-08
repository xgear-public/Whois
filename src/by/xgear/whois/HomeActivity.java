package by.xgear.whois;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import by.xgear.louversview.LouversView;
import by.xgear.whois.entity.UserInfoRoot;
import by.xgear.whois.nfc.NFCManager;
import by.xgear.whois.rest.RestHelper;
import by.xgear.whois.ui.activity.NFCActivity;
import by.xgear.whois.ui.fragment.PickUserDialogFragment;
import by.xgear.whois.ui.view.SkewImageView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class HomeActivity extends FragmentActivity {
	
	public static final String USERNAME = "username";
	
	private Button mCompare;
//	private LouversView mUserIcon;
	
	private String mCurUsername;
	private SeekBar mAngleSeekBar;
	private SkewImageView mSkewImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
//		mCompare = (Button)findViewById(R.id.compare);
//		mUserIcon = (LouversView)findViewById(R.id.user_icon);
		mAngleSeekBar = (SeekBar)findViewById(R.id.angle_seek);
//		mCompare.setOnClickListener(mStartClickListener);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
		mCurUsername = prefs.getString(USERNAME, null);
		
		mSkewImage  = (SkewImageView)findViewById(R.id.skew_image);
		mAngleSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSkewImage.setAngle(progress);
				mSkewImage.invalidate();
			}
		});
//		NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
//		nfcAdapter.setNdefPushMessageCallback(mSendNFCMessageCallback, this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(mPrefsChangeListener);
		if(!TextUtils.isEmpty(mCurUsername)) {
			getActionBar().setTitle(mCurUsername);
			getRestHelper().getUserInfo(new Listener<UserInfoRoot>() {

				@Override
				public void onResponse(UserInfoRoot response) {
					Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
				}
			}, new ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			},
			mCurUsername);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.unregisterOnSharedPreferenceChangeListener(mPrefsChangeListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.action_setup_username: {
	        	showUserPickUserDialog();
	            return true;
		    }
	    }

        return super.onOptionsItemSelected(item);
	}

	private OnSharedPreferenceChangeListener mPrefsChangeListener = new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			if(key.equals(USERNAME)){
				mCurUsername = sharedPreferences.getString(USERNAME, null);
				if(!TextUtils.isEmpty(mCurUsername)) {
					getActionBar().setTitle(mCurUsername);
				}
			}
		}
	};

	private void showUserPickUserDialog() {
        FragmentManager fm = getSupportFragmentManager();
        PickUserDialogFragment editNameDialog = new PickUserDialogFragment();
        editNameDialog.show(fm, PickUserDialogFragment.class.getSimpleName());
	}

	private OnClickListener mStartClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(HomeActivity.this, NFCActivity.class);
			Bundle data = new Bundle();
			data.putString("payload", "antoxa_89");
			i.putExtras(data);
			startActivity(i);
		}
	};
	
	private RestHelper getRestHelper() {
		WhoisApplication app = (WhoisApplication) getApplication();
		return app.getRestHelper();
	}
	
	private CreateNdefMessageCallback mSendNFCMessageCallback = new CreateNdefMessageCallback() {
		
		@Override
		public NdefMessage createNdefMessage(NfcEvent event) {
			return NFCManager.prepareMessage(mCurUsername);
		}
	}; 
			

}

package by.xgear.whois.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import by.xgear.whois.HomeActivity;
import by.xgear.whois.R;
import by.xgear.whois.WhoisApplication;
import by.xgear.whois.entity.ComparisonRoot;
import by.xgear.whois.entity.Image;
import by.xgear.whois.entity.UserInfoRoot;
import by.xgear.whois.rest.RestHelper;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NFCFragment extends Fragment {

	private ImageView mUserIcon;
	private TextView mPartyName;
	private Button mAddToFriends;
	private String mPayload;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getActivity().getIntent();
//		Parcelable[] messages = intent
//				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//		NdefMessage message = (NdefMessage) messages[0];
//		NdefRecord record = message.getRecords()[0];
		mPayload = intent.getExtras().getString("payload");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nfc, container, false);
		mUserIcon = (ImageView) v.findViewById(R.id.user_icon);
		mAddToFriends = (Button) v.findViewById(R.id.add_to_friends);
		mPartyName = (TextView) v.findViewById(R.id.party_name);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		if(!TextUtils.isEmpty(mPayload)){
			mPartyName.setText(mPayload);
			getRestHelper().getUserInfo(new Listener<UserInfoRoot>() {

				@Override
				public void onResponse(UserInfoRoot response) {
					Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
					ImageLoader.getInstance().displayImage(getBestUrl(response.getUser().getImages()),mUserIcon);
				}
			}, new ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			},
			mPayload);

			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());
			String mCurUsername = prefs.getString(HomeActivity.USERNAME, null);
			if(!TextUtils.isEmpty(mCurUsername)){
				getRestHelper().compare(new Listener<ComparisonRoot>() {
	
					@Override
					public void onResponse(ComparisonRoot response) {
						if(response != null && response.getComparison() != null) {
							int alpha = (int) (255*Double.parseDouble(response.getComparison().getResult().getScore()));
							mAddToFriends.setBackgroundColor(Color.argb(alpha,124,252,0));
						}
					}
				},
				new ErrorListener() {
	
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
					
				},
				mCurUsername,
				mPayload);
			}
		}
		
	}
	
	private String getBestUrl(List<Image> images) {
		String url = null;
		
		return url;
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	private RestHelper getRestHelper() {
		WhoisApplication app = (WhoisApplication) getActivity().getApplication();
		return app.getRestHelper();
	}

}

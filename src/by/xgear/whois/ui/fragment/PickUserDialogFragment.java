package by.xgear.whois.ui.fragment;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import by.xgear.whois.HomeActivity;
import by.xgear.whois.R;
import by.xgear.whois.WhoisApplication;
import by.xgear.whois.entity.UserInfoRoot;
import by.xgear.whois.rest.RestHelper;

public class PickUserDialogFragment extends DialogFragment {

	public static final int RESULT = 1234;
	
	private EditText mUsername;
	private Button mApplyButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_picker_dialog, container);
        mUsername = (EditText) view.findViewById(R.id.username);
        mApplyButton = (Button) view.findViewById(android.R.id.button1);
        getDialog().setTitle(R.string.enter_username);
        mApplyButton.setOnClickListener(mApplayClickListener);
        return view;
    }
    
    public OnClickListener mApplayClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			setUIEnabled(false);
			getRestHelper().getUserInfo(new Listener<UserInfoRoot>() {

				@Override
				public void onResponse(UserInfoRoot response) {
					setUIEnabled(true);
					Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
					Editor editor = prefs.edit();
					editor.putString(HomeActivity.USERNAME, mUsername.getText().toString());
					editor.apply();
					dismiss();
				}
			}, new ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					setUIEnabled(true);
					Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			},
			mUsername.getText().toString());
		}
	};
	
	private void setUIEnabled(boolean enabled){
		mUsername.setEnabled(enabled);
		mApplyButton.setEnabled(enabled);
		getDialog().setCancelable(enabled);
	}

	private RestHelper getRestHelper() {
		WhoisApplication app = (WhoisApplication) getActivity().getApplication();
		return app.getRestHelper();
	}

}

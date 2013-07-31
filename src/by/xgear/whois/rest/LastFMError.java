package by.xgear.whois.rest;

import com.android.volley.VolleyError;

@SuppressWarnings("serial")
public class LastFMError extends VolleyError {

	private int error;
	private String message;

	public int getErrCode() {
		return error;
	}

	public void setErrCode(int errCode) {
		this.error = errCode;
	}	

	@Override
	public String getMessage() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}
}

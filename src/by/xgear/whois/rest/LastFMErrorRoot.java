package by.xgear.whois.rest;

import com.android.volley.VolleyError;

@SuppressWarnings("serial")
public class LastFMErrorRoot extends VolleyError {
	
	private LastFMError error;

    public LastFMErrorRoot() { }

	public LastFMError getError() {
		return error;
	}

	public void setError(LastFMError error) {
		this.error = error;
	}

}

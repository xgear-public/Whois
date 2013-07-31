package by.xgear.whois.rest;

import java.util.HashMap;
import java.util.Map;

import by.xgear.whois.entity.ComparisonRoot;
import by.xgear.whois.entity.UserInfoRoot;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;

public class RestHelper {

	private RequestQueue mReqQueue;
    private final RequestParamsBuilder mMethodUriBuilder;
    private WhoisDefaultRetryPolicy mRetryPlicy = new WhoisDefaultRetryPolicy();
	
	public RestHelper(RequestQueue mReqQueue, String apiPath) {
		super();
		
		this.mReqQueue = mReqQueue;
        this.mMethodUriBuilder = MethodParamsBuilderFactory.createURIBuilder(apiPath);
	}

	public void compare(Listener<ComparisonRoot> listener, ErrorListener errorListener, String curUser, String partyUser) {
		
		prepareRequest(mMethodUriBuilder.compareTaste(curUser, partyUser),
				Method.GET,
				ComparisonRoot.class,
				null,
				listener,
				errorListener,
				null);
	}
	
	public void getUserInfo(Listener<UserInfoRoot> listener, ErrorListener errorListener, String username) {
		
		prepareRequest(mMethodUriBuilder.getUserInfo(username),
				Method.GET,
				UserInfoRoot.class,
				null,
				listener,
				errorListener,
				null);
	}
	
	/**
	 * Adds request with specified arguments to request queue
	 * 
	 * @param url - URL
	 * @param method - Request method {@link Method}
	 * @param clazz - Class for parameterization while decoding from json to specified type
	 * @param headers - Additional headers which can provided in request
	 * @param listener - Response listener which called when response successfully received  
	 * @param errorListener - Response listener which called when something went wrong
	 * @param requestData - Body to post with request
	 */
	private <T> void prepareRequest(
			String url,
			int method,
			Class<T> clazz,
			Map<String, String> headers,
			Listener<T> listener,
			ErrorListener errorListener,
			String requestData			) {
		
		if(headers == null)
			headers = new HashMap<String, String>();
		
		GsonRequest<T> request = new GsonRequest<T>(
        		url,
        		method,
        		clazz,
        		headers,
        		listener,
        		errorListener != null ? errorListener : mDefaultListener);
		
		addToQueue(request);
	}
	
	private void addToQueue(@SuppressWarnings("rawtypes") Request request){

		request.setRetryPolicy(mRetryPlicy);
			
        mReqQueue.add(request);
	}

	private ErrorListener mDefaultListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			throw new RuntimeException(error);
		}
	};

}

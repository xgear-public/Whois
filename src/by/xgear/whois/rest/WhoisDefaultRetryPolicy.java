package by.xgear.whois.rest;

import com.android.volley.DefaultRetryPolicy;

public class WhoisDefaultRetryPolicy extends DefaultRetryPolicy {

	private static int DEFAULT_JIRA_TIMEOUT_MS = 8000;
	private static int DEFAULT_JIRA_MAX_RETRIES = 0;

	public WhoisDefaultRetryPolicy() {
        this(DEFAULT_JIRA_TIMEOUT_MS, DEFAULT_JIRA_MAX_RETRIES, DEFAULT_BACKOFF_MULT);
	}

	public WhoisDefaultRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
		super(initialTimeoutMs, maxNumRetries, backoffMultiplier);
	}

}
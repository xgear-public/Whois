package by.xgear.whois.nfc;

import java.nio.charset.Charset;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

public class NFCManager {

//	private NdefRecord userRecord;
//	private NdefMessage userMessage;

	public NFCManager() {
		super();
	}
	
	public static NdefMessage prepareMessage(String username) {
		return new NdefMessage(new NdefRecord[]{
				prepareRecord(username),
				NdefRecord.createApplicationRecord("by.xgear.whois")});
	}

	private static NdefRecord prepareRecord(String username) {
		return new NdefRecord(
			    NdefRecord.TNF_MIME_MEDIA ,
			    "application/vnd.com.example.android.beam".getBytes(Charset.forName("US-ASCII")),
			    new byte[0], username.getBytes(Charset.forName("US-ASCII")));
	}
	
}

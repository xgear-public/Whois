package by.xgear.whois;

import java.util.concurrent.atomic.AtomicReference;

import android.app.Application;
import by.xgear.whois.rest.RestHelper;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class WhoisApplication extends Application{

	private ImageLoader mImageLoader;
	private final AtomicReference<RestHelper> mRestHelperHolder = new AtomicReference<RestHelper>();

	public static final String SERVER_HTTP_DEV = "http://ws.audioscrobbler.com/2.0/";

	@Override
	public void onCreate() {
		super.onCreate();

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		mRestHelperHolder.compareAndSet(null, new RestHelper(requestQueue,SERVER_HTTP_DEV));

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.default_user)
                .showImageForEmptyUri(R.drawable.default_user)
                .delayBeforeLoading(100)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new FadeInBitmapDisplayer(500)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
		.defaultDisplayImageOptions(displayImageOptions)
		.build();
        mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(config);
	}
	

	public RestHelper getRestHelper() {
		return mRestHelperHolder.get();
	}

}
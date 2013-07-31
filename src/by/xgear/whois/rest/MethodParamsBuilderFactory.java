package by.xgear.whois.rest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.util.Log;

public final class MethodParamsBuilderFactory {

    public static RequestParamsBuilder createURIBuilder(String apiPath) {
        return (RequestParamsBuilder)Proxy.newProxyInstance(
                RequestParamsBuilder.class.getClassLoader(),
                new Class[] {RequestParamsBuilder.class},
                new UriBuilderInvocationHandler(apiPath));
    }

    protected static class UriBuilderInvocationHandler implements InvocationHandler {

        private static final String LOG_TAG = "UriBuilder";

        private final String apiPath;

        public UriBuilderInvocationHandler(String apiPath) {
            this.apiPath = apiPath;

        }

        public Object invoke(final Object proxy, final Method method, final Object[] args)
                throws Throwable {
            Object retValue = null;

            if (method.isAnnotationPresent(UriFormat.class)) {
                UriFormat annotation = method.getAnnotation(UriFormat.class);
                retValue = formatUri(args, annotation.format());
            } else {
                Log.d(LOG_TAG, "Method '" + method.getName()
                        + "' doesn't contains 'UriFormat' annotation");
            }

            return retValue;
        }

        private String formatUri(final Object[] args, String format) {
            final String retValue;

            String methodParams = String.format(format, args);

            StringBuilder uriBuilder = new StringBuilder(apiPath).append(methodParams);
            
            retValue = uriBuilder.toString();

            return retValue;
        }
    }
}

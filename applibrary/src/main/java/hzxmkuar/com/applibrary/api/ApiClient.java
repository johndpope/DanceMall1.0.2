package hzxmkuar.com.applibrary.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import hzxmkuar.com.applibrary.R;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import util.NetWorkUtil;

/**
 * Created by xzz on 2017/8/25.
 **/

public class ApiClient {
    private static Retrofit retrofit;
    private Context mContext;
    private long time = System.currentTimeMillis();
//    private int[] certificates = {R.raw.joyhome};

    private static class SingletonHolder {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {

        return SingletonHolder.INSTANCE;
    }


    //构造方法私有


    private class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(mContext)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    public void init(Context context) {
        this.mContext = context;
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(mContext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        Interceptor headInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
                .addHeader("time", String.valueOf(time))
                .addHeader("Content-Type", "application/json")
                .build());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(25000, TimeUnit.MILLISECONDS)
                .connectTimeout(25000, TimeUnit.MILLISECONDS)
                .addInterceptor(headInterceptor)
                .addInterceptor(logInterceptor)
//                .sslSocketFactory(getSSLSocketFactory(mContext, certificates))
                .hostnameVerifier((hostname, session) -> "".equals(hostname))
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();



     String baseUrl ="http://m.wumeihui.net/";




        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static <T> T create(Class<T> service) {
        return retrofit.create(service);
    }


    private static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        CertificateFactory certificateFactory = null;
        try {
            try {
                certificateFactory = CertificateFactory.getInstance("x.509");
            } catch (CertificateException e) {
                e.printStackTrace();

            }
            KeyStore keyStore = null;
            try {
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            try {
                assert keyStore != null;
                keyStore.load(null, null);
            } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < certificates.length; i++) {
                InputStream certificate = context.getResources().openRawResource(certificates[i]);
                try {
                    assert certificateFactory != null;
                    keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
                } catch (KeyStoreException | CertificateException e) {
                    e.printStackTrace();
                }

                if (certificate != null) {
                    try {
                        certificate.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            TrustManagerFactory trustManagerFactory = null;
            try {
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                assert trustManagerFactory != null;
                trustManagerFactory.init(keyStore);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            try {
                assert sslContext != null;
                sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

package com.example.retrofitnetworkcallinkotlin.retrofitClient

import android.content.Context
import com.example.retrofitnetworkcallinkotlin.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object RetrofitClient {
   // const val baseUrl = ""
    private var retrofit: Retrofit? = null

    @Synchronized
    fun getInstance(context: Context, mBaseUrl: String): Retrofit? {
        if (retrofit == null) {
            createRetrofit(context, mBaseUrl)
        }
        return retrofit
    }

    fun createRetrofit(context: Context, baseUrl: String) {
        val pair = getSSLSocketFactory(context)
        val loggingInterceptor = HttpLoggingInterceptor()
        /* Only show basic information in logCat
        * ex... method post, url, http code*/
        //   loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)

      /*  val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()*/

/*        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)*/

        pair?.let {
            httpClient.sslSocketFactory(it.first, trustManager = it.second)
        }

        val client = httpClient.build()

        if (baseUrl == "api") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        } else if (baseUrl == "ajkerdeal") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.ajkerdeal.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        } else if (baseUrl == "githubApi") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        } else if (baseUrl == "royalGreen") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.pacenet.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }

    @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class
    )
    private fun createCertificate(caInput: InputStream): Pair<SSLContext, X509TrustManager> {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        val cFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
        val certificate: X509Certificate = caInput.use {
            cFactory.generateCertificate(it) as X509Certificate
        }

        // creating a KeyStore containing our trusted CAs
        val keyStoreType: String = KeyStore.getDefaultType()
        val keyStore: KeyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", certificate)
        }

        // creating a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }

        // creating an SSLSocketFactory that uses our TrustManager
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }

        return Pair(sslContext, tmf.trustManagers[0] as X509TrustManager)
    }

    private fun getSSLSocketFactory(context: Context): Pair<SSLSocketFactory, X509TrustManager>? {
        var sslContext: SSLContext? = null
        var trustManager: X509TrustManager? = null
        try {
            val pair = createCertificate(context.resources.openRawResource(R.raw.pacenet_ssl_certificate))
            sslContext = pair.first
            trustManager = pair.second
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return sslContext?.let { sslCtx ->
            trustManager?.let {
                Pair(sslCtx.socketFactory, it)
            }
        }
    }
}
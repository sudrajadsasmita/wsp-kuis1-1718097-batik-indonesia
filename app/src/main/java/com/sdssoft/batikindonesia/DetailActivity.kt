package com.sdssoft.batikindonesia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_BATIK = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val actionBack = supportActionBar!!
        actionBack.setDisplayHomeAsUpEnabled(true)
        actionBack.setDisplayHomeAsUpEnabled(true)
        val batik = intent.getParcelableExtra<Batik>(EXTRA_BATIK)!!
        val url = "http://batikita.herokuapp.com/index.php/batik/${batik.nama_batik}"
        AndroidNetworking.initialize(this)
        AndroidNetworking.get(url)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val hasil = response?.getJSONArray("hasil")
                    try {
                        for (i in 0 until hasil!!.length()) {
                            val hsl = hasil.getJSONObject(i)
                            val id = hsl.getInt("id")
                            makna_batik.text = hsl.getString("makna_batik")
                            nama_batik.text = hsl.getString("nama_batik")
                            daerah_batik.text = "Asal : "+hsl.getString("daerah_batik")
                            val link_batik = hsl.getString("link_batik")
                            Glide.with(this@DetailActivity)
                                .load(link_batik)
                                .apply(RequestOptions())
                                .into(img_detail)
                            harga_rendah.text = "Harga dari Rp. "+hsl.getInt("harga_rendah").toString()
                            harga_tinggi.text = "Rp. "+hsl.getInt("harga_tinggi").toString()

                            Log.e("test", harga_rendah.toString())
                        }

                    } catch (e: Exception) {
                        Log.d("onResponse:", e.message.toString())
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.d("anError:", anError?.message.toString())
                }
            })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
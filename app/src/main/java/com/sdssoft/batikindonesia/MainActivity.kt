package com.sdssoft.batikindonesia

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<Batik>()
    private lateinit var cardBatikAdapter: CardBatikAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFan(null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.cari_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                loadFan(p0)
                return true
            }
        })
        return true
    }

    fun loadFan(batik: String?) {
        if (batik == null || batik == "") {
            showLoading(true)
            val url = "http://batikita.herokuapp.com/index.php/batik/all"
            list.clear()
            cardBatikAdapter = CardBatikAdapter()
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
                                val makna_batik = hsl.getString("makna_batik")
                                val nama_batik = hsl.getString("nama_batik")
                                val daerah_batik = hsl.getString("daerah_batik")
                                val link_batik = hsl.getString("link_batik")
                                val harga_rendah = hsl.getInt("harga_rendah")
                                val harga_tinggi = hsl.getInt("harga_tinggi")
                                list.add(
                                    Batik(
                                        id,
                                        nama_batik,
                                        daerah_batik,
                                        link_batik,
                                        makna_batik,
                                        harga_tinggi,
                                        harga_rendah
                                    )
                                )
                                Log.e("test", harga_rendah.toString())
                            }
                            showLoading(false)
                            loadRecyclerView(list)
                        } catch (e: Exception) {
                            Log.d("onResponse:", e.message.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("anError:", anError?.message.toString())
                    }
                })
        } else {
            showLoading(true)
            val url = "http://batikita.herokuapp.com/index.php/batik/$batik"
            list.clear()
            cardBatikAdapter = CardBatikAdapter()
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
                                val makna_batik = hsl.getString("makna_batik")
                                val nama_batik = hsl.getString("nama_batik")
                                val daerah_batik = hsl.getString("daerah_batik")
                                val link_batik = hsl.getString("link_batik")
                                val harga_rendah = hsl.getInt("harga_rendah")
                                val harga_tinggi = hsl.getInt("harga_tinggi")
                                list.add(
                                    Batik(
                                        id,
                                        nama_batik,
                                        daerah_batik,
                                        link_batik,
                                        makna_batik,
                                        harga_tinggi,
                                        harga_rendah
                                    )
                                )
                                Log.e("test", harga_rendah.toString())
                            }
                            showLoading(false)
                            loadRecyclerView(list)
                        } catch (e: Exception) {
                            Log.d("onResponse:", e.message.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("anError:", anError?.message.toString())
                    }
                })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.halaman_pengembang) {
            Toast.makeText(this, "Halaman ini masih dalam tahap pengembangan", Toast.LENGTH_SHORT)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadRecyclerView(list: ArrayList<Batik>) {
        rv_batik.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_batik.adapter = cardBatikAdapter
        rv_batik.setHasFixedSize(true)
        cardBatikAdapter.setData(list)
        cardBatikAdapter.setOnClickCallback(object : CardBatikAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Batik) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_BATIK, data)
                startActivity(intent)
            }
        })
    }

    fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
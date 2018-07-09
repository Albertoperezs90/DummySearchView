package com.example.alberto.searchviewfragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alberto.searchviewfragment.search.Adapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by alberto on 09/07/2018.
 */

class MainFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var users: MutableList<String>
    private lateinit var adapter: Adapter

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        searchView.setQuery("",false)
        hideList()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()){
            adapter.filter.filter("")
            hide.visibility = View.VISIBLE
        }else {
            adapter.filter.filter(newText)
            hide.visibility = View.GONE
        }
        return true
    }

    fun showList(){
        rv.visibility = View.VISIBLE
    }

    fun hideList(){
        rv.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, null)
        return view
    }

    override fun onStart() {
        super.onStart()
        users = mutableListOf(
                "Alberto",
                "Pepe",
                "Carlos",
                "Rosa",
                "Cristina",
                "Gloria"
        )

        adapter = Adapter(context, users, this)
        rv.adapter = adapter
        rv.isTextFilterEnabled = true
        setupSearchView()
        rv.visibility = View.GONE
        hide.visibility = View.VISIBLE

        searchView.setOnQueryTextFocusChangeListener { view, b ->
            if (b){
                showList()
            }else {
                hideList()
            }
        }

        mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search")
            startActivityForResult(intent, 1)
        }
    }

    private fun setupSearchView() {
        searchView.isIconified = true
        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(this)
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Prueba..."

        Handler().postDelayed(Runnable { searchView.clearFocus() }, 300)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val matchSize = matches.size

                matches.forEachIndexed { index: Int, s: String? ->
                    Log.i("FRAGMENT", "$index: $s")
                    if (index == 0){
                        testTV.text = s
                    }
                }

            }else if (resultCode != RESULT_OK){

            }
        }
    }
}

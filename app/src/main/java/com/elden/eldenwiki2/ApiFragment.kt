package com.elden.eldenwiki2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.elden.eldenwiki2.databinding.FragmentApiBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var adapter: GamesAdapter
    private var _binding: FragmentApiBinding? = null
    private var lista = mutableListOf<GamesResponseItem>()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
                            : View {

        _binding = FragmentApiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchGames.setOnQueryTextListener(this)
        initRecycle()
        //buscarGames()
    }

    private fun initRecycle() {
        adapter = GamesAdapter(lista)
        binding.rvGames.layoutManager = LinearLayoutManager(context)
        binding.rvGames.adapter = adapter
    }

    private fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buscarGames(genero:String) {
        CoroutineScope(Dispatchers.IO).launch{
        val call = getRetrofit().create(APIService::class.java).getGames("games")
        val juegos = call.body()!!
        activity?.runOnUiThread() {
        if(call.isSuccessful){
            lista.clear()
            for ( aux in juegos ) {
                if ( aux.genre.equals(genero))
                lista.add(aux)
                adapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
        }
         hideKeyboard()
       }
    }
  }
    private fun hideKeyboard() {
        val imn = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            buscarGames(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}

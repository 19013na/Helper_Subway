package com.example.projectapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectapplication.databinding.FragmentSubwayBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// btnSearch
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubwayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubwayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSubwayBinding.inflate(inflater, container, false)
        var mutableList: MutableList<ItemSubwayModel>

        binding.btnSearch.setOnClickListener {  // 검색버튼을 눌렀을 때,
            var keyword = binding.edtProduct.text.toString()    // 검색한 키워드 가져온다.

            val call: Call<MyModel> = MyApplication.networkService.getList(
                "6f6669585870686f3831586c724e6b",
                "json",
                1000,
            )

            Log.d("mobileApp", "${call.request()}") // 오류 점검

            call?.enqueue(object: Callback<MyModel> {
                override fun onResponse(call: Call<MyModel>, response: Response<MyModel>) {
                    if(response.isSuccessful) {
                        Log.d("mobileApp", "${response.body()}")    // 검사
                        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.retrofitRecyclerView.adapter = MySubwayAdapter(requireContext(), response.body()!!.SeoulMetroFaciInfo.row)
                    }
                }

                override fun onFailure(call: Call<MyModel>, t: Throwable) {
                    Log.d("mobileApp", "${t.toString()}")
                }
            })
        }

        mutableList = mutableListOf<ItemSubwayModel>()  // 초기화
        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.retrofitRecyclerView.adapter = MySubwayAdapter(requireContext(), mutableList)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubwayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubwayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
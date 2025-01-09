package com.example.esemkacorporation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireContext().getSharedPreferences("EsemkaCorporation", MODE_PRIVATE).getInt("ID", 0)

        val JobHistoryBtn = view.findViewById<Button>(R.id.JobHistoryBtn)
        JobHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(), JobHistory::class.java))
        }

        val WorksWithBtn = view.findViewById<Button>(R.id.WorksWithBtn)
        WorksWithBtn.setOnClickListener {
            startActivity(Intent(requireContext(), WorksWith::class.java))
        }

        val SubordinateBtn = view.findViewById<Button>(R.id.SubordinateBtn)
        SubordinateBtn.setOnClickListener {
            startActivity(Intent(requireContext(), Subordinate::class.java))
        }

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        val hello = view.findViewById<TextView>(R.id.hello_employee)
        val name = view.findViewById<TextView>(R.id.profile_name)
        val email = view.findViewById<TextView>(R.id.profile_email)
        val phone = view.findViewById<TextView>(R.id.profile_number)
        val hiredate = view.findViewById<TextView>(R.id.profile_hiredate)
        val position = view.findViewById<TextView>(R.id.profile_position)
        val joblevel = view.findViewById<TextView>(R.id.profile_joblevel)
        val departement = view.findViewById<TextView>(R.id.profile_departement)

        GlobalScope.launch(Dispatchers.IO) {
            val conn = URL("http://192.168.1.151:5000/api/Employee/${id}").openConnection() as HttpURLConnection
            val response = conn.inputStream.bufferedReader().readText()
            val data = JSONObject(response)

            GlobalScope.launch(Dispatchers.Main) {
                val rawDate = data.getString("hireDate")
                val parsedDateTime = LocalDateTime.parse(rawDate)
                val parsedDate = parsedDateTime.toLocalDate()

                hello.text = "Hello, ${data.getString("name")}"
                name.text = data.getString("name")
                email.text = data.getString("email")
                phone.text = data.getString("phoneNumber")
                hiredate.text = parsedDate.format(formatter)
                position.text = data.getString("position")
                joblevel.text = data.getString("jobLevel")
                departement.text = data.getString("departement")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package ca.tiffinsp.tiffinserviceapplication.tabs.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.FirestoreCollections
import ca.tiffinsp.tiffinserviceapplication.R
import ca.tiffinsp.tiffinserviceapplication.UserProfile
import ca.tiffinsp.tiffinserviceapplication.databinding.FragmentHomeBinding
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.models.User
import ca.tiffinsp.tiffinserviceapplication.utils.DialogProgress
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment : Fragment() {
    lateinit var contentView: View
    private val db = Firebase.firestore
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ServiceAdapter
    lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            setUser(contentView)
        }

    }



override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentHomeBinding.inflate( inflater, container, false)
    contentView = binding.root
    return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ServiceAdapter(requireContext(), arrayListOf())
        setUser(view)

        view.findViewById<CircleImageView>(R.id.civ_profile).setOnClickListener {
            val intent = Intent(requireContext(), UserProfile::class.java)
            startForResult.launch(intent)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_services)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun getData() {
        progressDialog = DialogProgress.build(requireContext())
        db.collection(FirestoreCollections.SUBSCRIPTIONS).whereEqualTo("uid", Firebase.auth.currentUser!!.uid).whereEqualTo("active", true).get().addOnCompleteListener {subscriptionSnapShots ->
            if (subscriptionSnapShots.isSuccessful && subscriptionSnapShots.result != null) {
                val gson = Gson()
                val subscriptions = arrayListOf<Subscription>()
                subscriptionSnapShots.result!!.forEach { snapshot ->
                    val restaurantJson = gson.toJson(snapshot.data)
                    val subscription = gson.fromJson(restaurantJson, Subscription::class.java)
                    subscriptions.add(subscription)
                }
                adapter.addNewData(subscriptions)
                db.collection(FirestoreCollections.BANNERS).get().addOnCompleteListener { bannerSnapshot ->
                    if (bannerSnapshot.isSuccessful && bannerSnapshot.result != null) {
                        val images = arrayListOf<String>()
                        bannerSnapshot.result!!.forEach { snapshot ->
                            val url = snapshot.getString("url")
                            println(snapshot)
                            if(url != null){
                                images.add(url)
                            }
                        }

                        binding.apply {
                            vp.adapter = BannerSliderAdapter(
                                requireContext(),
                                images
                            )
                            dotsIndicator.setViewPager2(vp)
                        }
                    }
                }
            }
            progressDialog.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun setUser(view: View){
        val userJson = PreferenceHelper().getPref(requireContext()).getString(PreferenceHelper.USER_PREF, "{}");
        val user = Gson().fromJson(userJson, User::class.java)
        view.findViewById<TextView>(R.id.tv_name).text = user.name
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
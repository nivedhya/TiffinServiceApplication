package ca.tiffinsp.tiffinserviceapplication.tabs


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.tiffinsp.tiffinserviceapplication.LoginPage
import ca.tiffinsp.tiffinserviceapplication.SubscriptionDetail
import ca.tiffinsp.tiffinserviceapplication.UserProfile
import ca.tiffinsp.tiffinserviceapplication.databinding.FragmentSettingsBinding
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate( inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvProfile.setOnClickListener {
                val intent = Intent(requireContext(), UserProfile::class.java)
                activity?.startActivity(intent)
            }


            tvPrivacy.setOnClickListener {
                openWebPage("https://www.google.com")
            }

            tvTerms.setOnClickListener {
                openWebPage("https://www.google.com")
            }

            tvLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                PreferenceHelper().deleteUserPref(requireContext())
                val intent = Intent(requireContext(), LoginPage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity?.startActivity(intent)
            }
        }
    }

    fun openWebPage(url: String) {
        println(url)
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (activity != null && intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {}
    }
}
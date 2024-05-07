package com.rm.android_fundamentals.topics.t7_storagetypes.contentprovider

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.FragmentContentProviderBinding
import com.rm.android_fundamentals.utils.toast
import timber.log.Timber

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactAdapter: ContactAdapter

    private var contacts: ArrayList<Contact> = arrayListOf()
    private var contactsStr: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabContentProvider.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requireActivity().toast("Permission granted")
            fetchContacts()
            setAdapter()

        } else {
            requireActivity().toast("Permission denied")
        }
    }

    @SuppressLint("Range", "Recycle")
    private fun fetchContacts() {
        val resolver: ContentResolver = requireActivity().contentResolver
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                    if (phoneNum > 0) {
                        val cursorPhone = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",
                            arrayOf(id),
                            null
                        )

                        cursorPhone?.let {
                            if (cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                        cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                    )
                                    contactsStr.add("$name|$phoneNumValue")
                                }
                            }
                            cursorPhone.close()
                        }
                    }
                }
            }
            cursor.close()
            for (contact in contactsStr) {
                val contactSplit = contact.split("|")
                Timber.d("CONTACT", contactSplit.toString())
                contacts.add(Contact(contactSplit[0], contactSplit[1]))
            }
        }
    }

    private fun setAdapter() {
        contactAdapter = ContactAdapter()
        binding.apply {
            rvContentProvider.layoutManager = LinearLayoutManager(requireContext())
            rvContentProvider.adapter = contactAdapter
            contactAdapter.contactList = contacts
        }
    }
}

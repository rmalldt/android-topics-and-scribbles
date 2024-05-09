package com.rm.android_fundamentals.topics.t8_storagetypes.contentprovider

import android.Manifest
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.databinding.FragmentContentProviderBinding
import com.rm.android_fundamentals.utils.toast
import timber.log.Timber
import java.util.Calendar

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactAdapter: ContactAdapter

    private val contacts: ArrayList<Contact> = arrayListOf()
    private val contactsStr: ArrayList<String> = arrayListOf()

    private val images: MutableList<Image> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()

        binding.btnFetchContacts.setOnClickListener {
            fetchContacts()
            setAdapter()
        }

        binding.btnFetchImages.setOnClickListener {
            fetchMediaFiles()
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            resultLauncherForMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            )
        }
    }

    private val resultLauncherForMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    requireActivity().toast("All permission granted!")
                } else {
                    requireActivity().toast("Permissions denied!")
                }
            }
        }

    private fun fetchMediaFiles() {
        // Defines specific columns that we want to query.
        val projection = arrayOf(
            MediaStore.Images.Media._ID,            // image id
            MediaStore.Images.Media.DISPLAY_NAME    // image name
        )

        // Defines WHERE condition similar to SQL WHERE clause
        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >=?"

        val millisInDaysBack = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -3)   // go to 3 days back
        }.timeInMillis

        // The argument for the selection.
        // Without this all images in the storage will be returned
        val selectionArgs = arrayOf(millisInDaysBack.toString())

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val resolver = requireActivity().contentResolver
        resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,   // specify content provider and the location of the content
            projection,                                     // specify specific columns
            selection,                                      // specify condition
            selectionArgs,                                  // specify arguments for the condition above
            sortOrder                                       // specify sort order
        )?.use { cursor ->
            Timber.d("Images cursor count: ${cursor.count}")
            // Here, we get the cursor which is used to iterate a large data set.
            // It needs to be closed after using it, hence using use function to auto-close it when done.

            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            while(cursor.moveToNext()) {    // returns true if there is result for the query
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(Image(id, name, uri))
            }
        }

        Timber.d("Images: $images")
    }

    private fun fetchContacts() {
        val resolver: ContentResolver = requireActivity().contentResolver // get content resolver

        resolver.query( // get contacts cursor
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.count > 0) {
                val idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val hasPhoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)

                while (cursor.moveToNext()) {
                    val id = cursor.getString(idColumn)
                    val name = cursor.getString(nameColumn)
                    val phoneNum = cursor.getString(hasPhoneColumn).toInt()

                    if (phoneNum > 0) {
                        val cursorPhone = resolver.query(   // get phoneNumber cursor
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",
                            arrayOf(id),
                            null
                        )
                        cursorPhone?.let {
                            if (cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumColumn = cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                    val phoneNumValue = cursorPhone.getString(phoneNumColumn)
                                    contactsStr.add("$name|$phoneNumValue")
                                }
                            }
                            cursorPhone.close()
                        }
                    }
                }
            }
        }
        for (contact in contactsStr) {
            val contactSplit = contact.split("|")
            Timber.d("CONTACT", contactSplit.toString())
            contacts.add(Contact(contactSplit[0], contactSplit[1]))
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


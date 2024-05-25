package com.rm.android_fundamentals.topics.t11_cryptography

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rm.android_fundamentals.databinding.FragmentKeystoreBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EncryptionFragment : Fragment() {

    private var _binding: FragmentKeystoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentKeystoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyStoreUtil = KeyStoreUtil("secret")

        binding.btnEncrypt.setOnClickListener {
            val textToEncrypt = binding.tvTextToEncrypt.text.toString()
            val byteArray = textToEncrypt.encodeToByteArray()
            val file = File(requireActivity().filesDir, "test5.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            val fos = FileOutputStream(file)

            val encryptedValue = keyStoreUtil.encrypt(
                bytes = byteArray,
                outputStream = fos
            ).decodeToString()
            binding.tvEncryptedValue.text = encryptedValue
        }

        binding.btnDecrypt.setOnClickListener {
            val file = File(requireActivity().filesDir, "test5.txt")

            val decryptedValue = keyStoreUtil.decrypt(
                inputStream = FileInputStream(file)
            ).decodeToString()
            binding.tvDecryptedValue.text = decryptedValue
        }
    }
}
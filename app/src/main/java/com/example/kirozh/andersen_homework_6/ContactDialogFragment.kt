package com.example.kirozh.andersen_homework_6

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


/**
 * @author Kirill Ozhigin on 16.10.2021
 */
class ContactDialogFragment : DialogFragment(){
    private lateinit var okButtonListener : OkButtonListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OkButtonListener)
            okButtonListener = parentFragment as OkButtonListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireContext().let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Delete")
                .setMessage("Delete anyway")
                .setCancelable(true)
                .setPositiveButton("yes") { dialog, id ->
                }
                .setNegativeButton("no",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface OkButtonListener{
        fun onOkClicked()
    }

    companion object{
        fun newInstance(): ContactDialogFragment{
            return ContactDialogFragment()
        }
    }
}
package id.smartech.creepystory.activity.profile.editprofile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import id.smartech.creepystory.R
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityEditProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {
    private lateinit var calendar: Calendar
    private lateinit var dob: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout =  R.layout.activity_edit_profile
        super.onCreate(savedInstanceState)

        calendar = Calendar.getInstance()
        dateOfBirth()
        val roleOption = arrayOf("vilagger","witch","guardian","angel","ghost","hunter","werewolf","wizard")
        bind.spinnerListProject.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, roleOption)
        bind.spinnerListProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@EditProfileActivity, roleOption.get(position), Toast.LENGTH_SHORT).show()
            }
        }

        bind.etDob.setOnClickListener {
            DatePickerDialog(
                    this@EditProfileActivity, dob, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun dateOfBirth() {
        dob = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_dob)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(calendar.time)
        }
    }


}
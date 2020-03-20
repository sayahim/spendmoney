package com.himorfosis.kelolabelanja.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.financial.model.InputDataModel
import com.himorfosis.kelolabelanja.utilities.DateSet
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.input_finance.*
import java.text.DecimalFormat
import java.util.*

class InputFinanceBottomDialog(context: Context): BottomSheetDialog(context) {

    companion object {

        private val TAG = "InputFinanceDialog"

        lateinit var onClickItem: OnClickItemDialog
        interface OnClickItemDialog {
            fun onItemClicked(data: InputDataModel)

        }

        fun setOnclick(onClickItem: OnClickItemDialog) {
            this.onClickItem = onClickItem

        }

    }

    init {

        setContentView(R.layout.input_finance)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        close_img.setOnClickListener {

            dismiss()
        }

        // setting date
        var dateSelected = DateSet.getDateToday()

        select_date_ll.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dateDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                dateSelected = "$dayOfMonth.${monthOfYear + 1}.$year"

                Util.log(TAG, "date selected : $dateSelected")


            }, year, month, day)
            dateDialog.show()

        }

        // setNumberFormatNominal
        val decimalFormat = DecimalFormat("#,###,###,###,###,##")
        decimalFormat.isDecimalSeparatorAlwaysShown = true
        val decimalFormatNotEdit = DecimalFormat("#,###")
        var hasFractionalPart = false

        nominal_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                nominal_et.removeTextChangedListener(this)

                try {

                    var nominalSize = nominal_et.text.toString().length

                    var originalString = s.toString()

                    originalString = originalString.replace(decimalFormat.decimalFormatSymbols.groupingSeparator.toString(), "")

                    var number: Number = decimalFormat.parse(originalString)

                    var selectionNominal = nominal_et.selectionStart

                    if (hasFractionalPart) {

                        nominal_et.setText(decimalFormat.format(number))

                    } else {

                        nominal_et.setText(decimalFormatNotEdit.format(number))

                    }

                    val endSize = nominal_et.text.toString().length

                    var setIndicatorEditText = (selectionNominal + (endSize - nominalSize))

                    if (setIndicatorEditText in 1..endSize) {

                        nominal_et.setSelection(setIndicatorEditText)

                    } else {

                        nominal_et.setSelection(endSize - 1)

                    }

                } catch (e : java.lang.NumberFormatException) {
                    // do nothing?
                }

                nominal_et.addTextChangedListener(this)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

            }

        })

        // setNoteLength
        note_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                var noteSize = note_et.text.toString().length
                note_length_tv.text = "$noteSize/50"
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

        })

        save_btn.setOnClickListener {

            val nominal = nominal_et.text.toString()
            val note = note_et.text.toString()
            val date = dateSelected

            onClickItem.onItemClicked(InputDataModel(nominal, note, date))

            dismiss()

        }

    }
}
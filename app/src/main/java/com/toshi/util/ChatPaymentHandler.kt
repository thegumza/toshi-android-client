package com.toshi.util

import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import com.toshi.R
import com.toshi.manager.model.PaymentTask
import com.toshi.model.local.User
import com.toshi.model.sofa.PaymentRequest
import com.toshi.model.sofa.payment.Payment
import com.toshi.view.fragment.PaymentConfirmationFragment

class ChatPaymentHandler(private val activity: AppCompatActivity) {

    fun showResendPaymentConfirmationDialog(receiver: User,
                                            payment: Payment,
                                            listener: (PaymentTask) -> Unit) {
        val dialog = PaymentConfirmationFragment.newInstanceToshiPayment(
                toshiId = receiver.toshiId,
                value = payment.value,
                paymentType = PaymentType.TYPE_SEND
        )
        dialog.show(this.activity.supportFragmentManager, PaymentConfirmationFragment.TAG)
        dialog.setOnPaymentConfirmationApprovedListener { listener(it) }
    }

    fun showPaymentConfirmationDialog(receiver: User,
                                      value: String) {
        val dialog = PaymentConfirmationFragment.newInstanceToshiPayment(
                toshiId = receiver.toshiId,
                value = value,
                paymentType = PaymentType.TYPE_SEND
        )
        dialog.show(this.activity.supportFragmentManager, PaymentConfirmationFragment.TAG)
    }

    fun showPaymentRequestConfirmationDialog(receiver: User,
                                             paymentRequest: PaymentRequest,
                                             listener: (PaymentTask) -> Unit) {
        val dialog = PaymentConfirmationFragment.newInstanceToshiPaymentRequest(
                toshiId = receiver.toshiId,
                toAddress = paymentRequest.destinationAddresss,
                value = paymentRequest.value,
                paymentType = PaymentType.TYPE_REQUEST
        )
        dialog.show(this.activity.supportFragmentManager, PaymentConfirmationFragment.TAG)
        dialog.setOnPaymentConfirmationApprovedListener { listener(it) }
    }

    fun showResendDialog(listener: () -> Unit,
                         deleteListener: () -> Unit) {
        val resendDialog = BottomSheetDialog(this.activity)
        val sheetView = this.activity.layoutInflater.inflate(R.layout.view_chat_resend, null)
        val deleteView = sheetView.findViewById<LinearLayout>(R.id.deleteMessage)
        deleteView.setOnClickListener {
            resendDialog.dismiss()
            deleteListener()
        }
        val retryView = sheetView.findViewById<LinearLayout>(R.id.retry)
        retryView.setOnClickListener {
            resendDialog.dismiss()
            listener()
        }
        resendDialog.setContentView(sheetView)
        resendDialog.show()
    }
}

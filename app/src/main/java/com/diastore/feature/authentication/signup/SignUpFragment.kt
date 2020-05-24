package com.diastore.feature.authentication.signup

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.findNavController
import com.diastore.R
import com.diastore.SignUpBinding
import com.diastore.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment<SignUpBinding, SignUpViewModel>(R.layout.fragment_signup) {
    override val viewModel by sharedViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAlreadyRegisteredTextSpan()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToAboutYouFragment())
        }

    }

    private fun setAlreadyRegisteredTextSpan() {
        val alreadyRegisteredText = getString(R.string.sign_up_already_registered_text)
        val alreadyRegisteredLogInText = getString(R.string.sign_up_already_registered_login_text)
        binding.alreadyRegisteredText.text = SpannableString(alreadyRegisteredText).apply {
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.typeface = Typeface.DEFAULT_BOLD
                        ds.isUnderlineText = true
                        ds.color = resources.getColor(R.color.colorPrimary, null)
                    }
                },
                alreadyRegisteredText.indexOf(alreadyRegisteredLogInText),
                alreadyRegisteredText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        binding.alreadyRegisteredText.movementMethod = LinkMovementMethod.getInstance()
    }
}
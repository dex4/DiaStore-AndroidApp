package com.diastore.util.bindingadapters

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.diastore.R
import com.diastore.model.MealTypeSpecifier
import com.diastore.model.MomentSpecifier
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@BindingAdapter("glucoseLevel")
fun TextView.setGlucoseLevel(glucoseLevel: Int) {
    val formattedGlucoseLevelText = context.getString(R.string.entry_glucose_level, glucoseLevel)
    text = SpannableString(formattedGlucoseLevelText).apply {
        setSpan(RelativeSizeSpan(1f), 0, glucoseLevel.toString().length - 1, 0) // set size
        setSpan(RelativeSizeSpan(0.4f), glucoseLevel.toString().length, formattedGlucoseLevelText.length, 0) // set color
    }
}

@BindingAdapter("momentType", "mealType", requireAll = false)
fun TextView.setMealMomentText(momentType: MomentSpecifier?, mealType: MealTypeSpecifier?) {
    var mealMoment =
        when (momentType) {
            MomentSpecifier.BEFORE_MEAL -> context.getString(R.string.moment_before)
            MomentSpecifier.AFTER_MEAL -> context.getString(R.string.moment_after)
            else -> ""
        }
    mealMoment += when (mealType) {
        MealTypeSpecifier.BREAKFAST -> "Breakfast"
        MealTypeSpecifier.LUNCH -> "Lunch"
        MealTypeSpecifier.DINNER -> "Dinner"
        MealTypeSpecifier.SNACK -> "Snack"
        null -> ""
    }
    text = mealMoment
}

@BindingAdapter("entryTime")
fun TextView.setEntryTime(entryTime: OffsetDateTime) {
    text = entryTime.format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mm", Locale.ENGLISH))
}
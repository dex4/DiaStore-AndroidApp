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
        setSpan(RelativeSizeSpan(1f), 0, glucoseLevel.toString().length - 1, 0)
        setSpan(RelativeSizeSpan(0.4f), glucoseLevel.toString().length, formattedGlucoseLevelText.length, 0)
    }
}

@BindingAdapter("momentType", "mealType", requireAll = false)
fun TextView.setMealMomentText(momentType: MomentSpecifier?, mealType: MealTypeSpecifier?) {
    val mealMoment =
        when (momentType) {
            MomentSpecifier.BEFORE_MEAL -> context.getString(R.string.entry_moment_type_before)
            MomentSpecifier.AFTER_MEAL -> context.getString(R.string.entry_moment_type_after)
            else -> ""
        }
    val meal = when (mealType) {
        MealTypeSpecifier.BREAKFAST -> context.getString(R.string.entry_meal_type_display_breakfast)
        MealTypeSpecifier.LUNCH -> context.getString(R.string.entry_meal_type_display_lunch)
        MealTypeSpecifier.DINNER -> context.getString(R.string.entry_meal_type_display_dinner)
        MealTypeSpecifier.SNACK -> context.getString(R.string.entry_meal_type_display_snack)
        else -> ""
    }
    text = if (mealMoment.isNotEmpty() && meal.isNotEmpty()) {
        mealMoment + meal
    } else if (mealMoment.isNotEmpty() && meal.isEmpty()) {
        mealMoment + context.getString(R.string.entry_meal_type_display_none)
    } else if (mealMoment.isEmpty()) {
        meal
    } else {
        ""
    }
}

@BindingAdapter("entryTime")
fun TextView.setEntryTime(entryTime: OffsetDateTime) {
    text = entryTime.format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mm", Locale.ENGLISH))
}
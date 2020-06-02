package com.diastore.util.bindingadapters

import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.diastore.R
import com.diastore.model.MealTypeSpecifier
import com.diastore.model.MomentSpecifier


@BindingAdapter("entryMoment")
fun RadioGroup.selectEntryMoment(entryMomentSpecifier: MomentSpecifier?) {
    when (entryMomentSpecifier) {
        MomentSpecifier.BEFORE_MEAL -> this.check(R.id.before)
        MomentSpecifier.AFTER_MEAL -> this.check(R.id.after)
        else -> this.clearCheck()
    }
}

@InverseBindingAdapter(attribute = "entryMoment")
fun RadioGroup.getEntryMoment(): MomentSpecifier? =
    when (checkedRadioButtonId) {
        R.id.before -> MomentSpecifier.BEFORE_MEAL
        R.id.after -> MomentSpecifier.AFTER_MEAL
        else -> MomentSpecifier.NONE
    }

@BindingAdapter("entryMomentAttrChanged")
fun RadioGroup.setEntryMomentListeners(
    attrChange: InverseBindingListener
) {
    setOnCheckedChangeListener { _, _ ->
        attrChange.onChange()
    }
}

@BindingAdapter("mealType")
fun RadioGroup.selectMealType(mealTypeSpecifier: MealTypeSpecifier?) {
    when (mealTypeSpecifier) {
        MealTypeSpecifier.BREAKFAST -> this.check(R.id.breakfast)
        MealTypeSpecifier.LUNCH -> this.check(R.id.lunch)
        MealTypeSpecifier.DINNER -> this.check(R.id.dinner)
        MealTypeSpecifier.SNACK -> this.check(R.id.snack)
        else -> this.clearCheck()
    }
}

@InverseBindingAdapter(attribute = "mealType")
fun RadioGroup.getMealType(): MealTypeSpecifier? =
    when (checkedRadioButtonId) {
        R.id.breakfast -> MealTypeSpecifier.BREAKFAST
        R.id.lunch -> MealTypeSpecifier.LUNCH
        R.id.dinner -> MealTypeSpecifier.DINNER
        R.id.snack -> MealTypeSpecifier.SNACK
        else -> MealTypeSpecifier.NONE
    }

@BindingAdapter("mealTypeAttrChanged")
fun RadioGroup.setMealTypeListeners(
    attrChange: InverseBindingListener
) {
    setOnCheckedChangeListener { _, _ ->
        attrChange.onChange()
    }
}
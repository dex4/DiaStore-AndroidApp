package com.diastore.util.bindingadapters

import androidx.databinding.BindingAdapter
import com.diastore.R
import com.diastore.feature.authentication.signup.ProfileDetailsField

@BindingAdapter("profileFieldValue")
fun ProfileDetailsField.setProfileFieldValue(fieldValue: String) {
    setFieldValue(fieldValue)
}

@BindingAdapter("age")
fun ProfileDetailsField.setAge(age: Int) {
    setFieldValue(resources.getQuantityString(R.plurals.profile_age_format, age, age))
}

@BindingAdapter("weight")
fun ProfileDetailsField.setWeight(weight: Int) {
    setFieldValue(context.getString(R.string.profile_weight_format, weight))
}

@BindingAdapter("height")
fun ProfileDetailsField.setHeight(height: Int) {
    setFieldValue(context.getString(R.string.profile_height_format, height))
}

@BindingAdapter("carbsToInsulinUnit")
fun ProfileDetailsField.setCarbsToInsulinUnit(carbsToInsulinUnit: Int) {
    setFieldValue(context.getString(R.string.profile_carbs_format, carbsToInsulinUnit))
}

@BindingAdapter("bloodSugarToInsulinUnit")
fun ProfileDetailsField.setBloodSugarToInsulinUnit(bloodSugarToInsulinUnit: Int) {
    setFieldValue(context.getString(R.string.profile_blood_sugar_format, bloodSugarToInsulinUnit))
}

@BindingAdapter("firstName", "lastName")
fun ProfileDetailsField.setUserName(firstName: String, lastName: String) {
    setFieldValue(context.getString(R.string.profile_user_name_format, firstName, lastName))
}
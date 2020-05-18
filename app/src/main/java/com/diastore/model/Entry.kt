package com.diastore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime
import java.util.UUID

@Parcelize
data class Entry(
    val id: UUID = UUID.randomUUID(),
    val bloodSugarLevel: Int,
    val carbohydratesIntake: Int,
    val insulinIntake: Float,
    val entryTime: LocalDateTime,
    val physicalActivityDuration: Int = 0,
    val entryMomentSpecifier: MomentSpecifier? = null,
    val mealTypeSpecifier: MealTypeSpecifier? = null,
    val hasDonePhysicalActivity: Boolean = false
) : Parcelable, Comparable<Entry> {
    override fun compareTo(other: Entry): Int = entryTime.compareTo(other.entryTime)
}

enum class MomentSpecifier {
    BEFORE_MEAL, AFTER_MEAL
}

enum class MealTypeSpecifier {
    BREAKFAST, LUNCH, DINNER, SNACK
}
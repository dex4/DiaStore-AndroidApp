package com.diastore.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.OffsetDateTime
import java.util.UUID

@Entity(tableName = "entries")
@Parcelize
data class Entry(
    @PrimaryKey(autoGenerate = false)
    val id: UUID = UUID.randomUUID(),
    val bloodSugarLevel: Int,
    val carbohydratesIntake: Int,
    val insulinIntake: Float,
    val entryTime: OffsetDateTime,
    val physicalActivityDuration: Int = 0,
    val entryMomentSpecifier: MomentSpecifier? = null,
    val mealTypeSpecifier: MealTypeSpecifier? = null,
    val hasDonePhysicalActivity: Boolean = false
) : Parcelable, Comparable<Entry> {
    override fun compareTo(other: Entry): Int = entryTime.compareTo(other.entryTime)
}

enum class MomentSpecifier {
    BEFORE_MEAL, AFTER_MEAL, NONE
}

enum class MealTypeSpecifier {
    BREAKFAST, LUNCH, DINNER, SNACK, NONE
}
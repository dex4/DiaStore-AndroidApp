package com.diastore.database

import androidx.room.TypeConverter
import com.diastore.model.MealTypeSpecifier
import com.diastore.model.MomentSpecifier
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.UUID

object Converters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    @JvmStatic
    fun fromUUID(id: UUID): String = id.toString()

    @TypeConverter
    @JvmStatic
    fun toUUID(id: String): UUID = UUID.fromString(id)

    @TypeConverter
    @JvmStatic
    fun fromMomentSpecifier(momentSpecifier: MomentSpecifier?): String =
        momentSpecifier.toString()

    @TypeConverter
    @JvmStatic
    fun toMomentSpecifier(momentSpecifierString: String): MomentSpecifier =
        MomentSpecifier.valueOf(momentSpecifierString)

    @TypeConverter
    @JvmStatic
    fun fromMealTypeSpecifier(mealTypeSpecifier: MealTypeSpecifier?): String =
        mealTypeSpecifier.toString()

    @TypeConverter
    @JvmStatic
    fun toMealTypeSpecifier(mealTypeSpecifierString: String): MealTypeSpecifier =
        MealTypeSpecifier.valueOf(mealTypeSpecifierString)

}
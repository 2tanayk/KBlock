package com.kamathtanay.kblock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kamathtanay.kblock.data.db.entity.typeconverter.Converters
import java.util.*

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int=0,
    @ColumnInfo(name = "contact_name")
    val contactName: String,
    @ColumnInfo(name = "contact_phone_number")
    val contactPhoneNumber: String,
    @ColumnInfo(name = "is_blocked")
    val isBlocked: Boolean = false,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "date_blocked")
    val dateBlocked: Date? = null,
)


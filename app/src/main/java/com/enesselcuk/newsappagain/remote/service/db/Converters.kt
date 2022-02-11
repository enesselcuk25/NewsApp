package com.enesselcuk.newsappagain.remote.service.db

import androidx.room.TypeConverter
import com.enesselcuk.newsappagain.model.Source


class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}
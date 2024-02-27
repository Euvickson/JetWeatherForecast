package br.com.euvickson.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.euvickson.jetweatherforecast.model.Favorite
import br.com.euvickson.jetweatherforecast.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
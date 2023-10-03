package com.example.repository

import com.example.models.Country
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import java.sql.DriverManager
import java.sql.ResultSet

@Singleton
class CountryRepository(
    @Value("\${datasources.default.url}") private val url: String,
    @Value("\${datasources.default.username}") private val username: String,
    @Value("\${datasources.default.password}") private val password: String
) {
    fun getAllCountries(): List<Country> {
        val countries = mutableListOf<Country>()
        val query = "SELECT * FROM countries"

        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                val resultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val countryId = resultSet.getString("country_id")
                    val countryName = resultSet.getString("country_name")
                    val regionId = resultSet.getInt("region_id")
                    countries.add(Country(countryId, countryName, regionId))
                }
            }
        }
        return countries
    }

    fun getCountryByCountryId(countryId: String): Country? {
        val query = "SELECT * FROM countries WHERE country_id = ?"
        var country: Country? = null

        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, countryId)
                val resultSet: ResultSet = statement.executeQuery()
                if (resultSet.next()) {
                    val fetchedCountryId = resultSet.getString("country_id")
                    val countryName = resultSet.getString("country_name")
                    val regionId = resultSet.getInt("region_id")
                    country = Country(fetchedCountryId, countryName, regionId)
                }
            }
        }
        return country
    }

    fun createCountry(newCountry: Country): Country{
        val query = "INSERT INTO Countries (country_id, country_name, region_id) VALUES (?, ?, ?)"

        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, newCountry.countryId)
                statement.setString(2, newCountry.countryName)
                statement.setInt(3, newCountry.regionId)
                statement.executeUpdate()
            }
        }
        return  newCountry
    }

    fun updateCountry(countryId: String, upCountry: Country): Country{
        val query = "UPDATE Countries SET country_name = ?, region_id = ? WHERE country_id = ?"
        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(3, countryId)
                statement.setString(1, upCountry.countryName)
                statement.setInt(2, upCountry.regionId)
                val row = statement.executeUpdate()
                if(row == 0){
                    throw Exception("Update failed")
                }
            }
        }
        return upCountry
    }

    fun removeCountry(countryId: String): String{
        val query = "DELETE FROM Countries WHERE country_id = ?"
        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, countryId)
                val row = statement.executeUpdate()
                if (row == 0){
                    return "Failed to delete country!"
                }
            }
        }
        return "Country deleted successfully!"
    }
}
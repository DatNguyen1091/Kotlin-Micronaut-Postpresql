package com.example.controller

import com.example.models.Country
import com.example.repository.CountryRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/countries")
@Tag(name = "countries")
class CountryController(private val countryRepository: CountryRepository) {

    @Get("/")
    fun getAllCountries(): List<Country> {
        return countryRepository.getAllCountries()
    }

    @Get("{countryId}")
    fun getCountryById(countryId: String): Country? {
        return countryRepository.getCountryByCountryId(countryId)
    }

    @Post("/createCountry")
    fun postCountry(newCountry: Country): Country{
        return countryRepository.createCountry(newCountry)
    }

    @Put("/{countryId}")
    fun putCountry(countryId: String ,upCountry: Country): Country{
        return countryRepository.updateCountry(countryId, upCountry)
    }

    @Delete("/{countryId}", produces=["text/plain"])
    fun deleteCountry(countryId: String): String{
        return countryRepository.removeCountry(countryId)
    }
}
package com.example.controller

import com.example.models.Employee
import com.example.repository.EmployeeRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/employees")
@Tag(name = "employees")
class EmployeeController( private val employeeRep: EmployeeRepository) {

    @Get("/")
    fun getListEmployees(): List<Employee> {
        return employeeRep.getAllEmployees()
    }
}
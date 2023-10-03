package com.example.models

import java.time.LocalDateTime

data class Employee(
    var employeesId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var hireDate: LocalDateTime,
    var jobId: String,
    var salary: Double,
    var commissionPct: Double,
    var managerId: Int,
    var departmentId: Int
)

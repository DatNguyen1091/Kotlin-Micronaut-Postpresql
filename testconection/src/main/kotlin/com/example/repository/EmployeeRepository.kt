package com.example.repository

import com.example.models.Employee
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import java.sql.DriverManager

@Singleton
class EmployeeRepository(@Value("\${datasources.default.url}") private val url: String,
                  @Value("\${datasources.default.username}") private val username: String,
                  @Value("\${datasources.default.password}") private val password: String){

    fun getAllEmployees(): List<Employee> {
        val employees = mutableListOf<Employee>()
        val query = "SELECT * FROM employees"

        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                val resultSet = statement.executeQuery()
                while (resultSet.next()){
                    val employeesId = resultSet.getInt("employee_id")
                    val firstName = resultSet.getString("first_name")
                    val lastName = resultSet.getString("last_name")
                    val email = resultSet.getString("email")
                    val phoneNumber = resultSet.getString("phone_number")
                    val hireDate = resultSet.getTimestamp("hire_date").toLocalDateTime()
                    val jobId = resultSet.getString("job_id")
                    val salary = resultSet.getDouble("salary")
                    val commissionPct = resultSet.getDouble("commission_pct")
                    val managerId = resultSet.getInt("manager_id")
                    val departmentId = resultSet.getInt("department_id")
                    employees.add(Employee(employeesId, firstName, lastName, email, phoneNumber, hireDate,
                        jobId , salary, commissionPct, managerId, departmentId))
                }
            }
        }
        return employees
    }

    fun getById(employeesId: Int): Employee? {
        val query = "SELECT * FROM employees WHERE employee_id = ?"
        var employee: Employee? = null

        DriverManager.getConnection(url, username, password).use { connection ->
            connection.prepareStatement(query).use { statement ->
                val resultSet = statement.executeQuery()
                if (resultSet.next()){
                    val fetchEmployeesId = resultSet.getInt("employee_id")
                    val firstName = resultSet.getString("first_name")
                    val lastName = resultSet.getString("last_name")
                    val email = resultSet.getString("email")
                    val phoneNumber = resultSet.getString("phone_number")
                    val hireDate = resultSet.getTimestamp("hire_date").toLocalDateTime()
                    val jobId = resultSet.getString("job_id")
                    val salary = resultSet.getDouble("salary")
                    val commissionPct = resultSet.getDouble("commission_pct")
                    val managerId = resultSet.getInt("manager_id")
                    val departmentId = resultSet.getInt("department_id")
                    employee = Employee(fetchEmployeesId, firstName, lastName, email, phoneNumber, hireDate,
                        jobId , salary, commissionPct, managerId, departmentId)
                }
            }
        }
        return employee
    }
}

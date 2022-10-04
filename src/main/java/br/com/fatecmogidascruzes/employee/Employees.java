package br.com.fatecmogidascruzes.employee;

import br.com.fatecmogidascruzes.course.Course;

public interface Employees {

    Employee add(Employee employee);

    ResultPage<Employee> filter(String filter, PaginationRequest paginationRequest);

    ResultPage<Employee> findCourseProfessors(Course course);

}

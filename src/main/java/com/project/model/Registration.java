package com.project.model;

import java.time.LocalDate;

/**
 * Registration sınıfı, bir öğrencinin bir derse kaydolmasını temsil eder.
 */
public class Registration {

    private Student student;
    private Course course;
    private LocalDate registrationDate;

    /**
     * Yeni bir kayıt oluşturur.
     *
     * @param student  Kaydı yapan öğrenci
     * @param course   Kaydolunan ders
     */
    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.registrationDate = LocalDate.now();
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        // HATALI: course.getTitle()
        // DOĞRUSU: course.getCourseName()
        return student.getName() + " -> " + course.getCourseName() + " (" + registrationDate + ")";
    }
}

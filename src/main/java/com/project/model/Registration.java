package com.project.model;

import java.time.LocalDate;

/**
 * Registration sınıfı, bir öğrencinin bir ders ŞUBESİNE (CourseSection) kaydını temsil eder.
 */
public class Registration {

    private Student student;
    private CourseSection section;
    private LocalDate registrationDate;

    public Registration(Student student, CourseSection section) {
        this.student = student;
        this.section = section;
        this.registrationDate = LocalDate.now();
    }

    public Student getStudent() {
        return student;
    }

    // Kayıt edilen asıl şey: şube
    public CourseSection getSection() {
        return section;
    }

    // Kolaylık: ders bilgisine direkt erişim
    public Course getCourse() {
        return section.getCourse();
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        // section.toString() zaten ders + eğitmen + saat bilgilerini içeriyor
        return student.getName() + " -> " + section + " (" + registrationDate + ")";
    }
}

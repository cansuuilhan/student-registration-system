package com.project.service;

import com.project.model.CourseSection;
import com.project.model.Student;

/**
 * RegistrationService, öğrencilerin derslere
 * kayıt işlemlerini yöneten servis sınıfıdır.
 */
public class RegistrationService {

    public boolean register(Student student, CourseSection section) {

        // Aynı derse tekrar kayıt engeli
        if (student.isAlreadyRegistered(section)) {
            System.out.println("Bu derse zaten kayıtlısınız.");
            return false;
        }

        // Kontenjan kontrolü
        if (section.isFull()) {
            System.out.println("Ders kontenjanı dolu.");
            return false;
        }

        // Kayıt işlemi
        section.enrollStudent(student);
        student.addCourse(section);

        System.out.println("Kayıt başarılı.");
        return true;
    }
}

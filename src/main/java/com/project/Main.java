package com.project;

import com.project.model.Course;
import com.project.model.GraduateStudent;
import com.project.model.Student;
import com.project.model.Instructor;
import com.project.service.RegistrationService;

public class Main {
    public static void main(String[] args) {

        // Öğrenci oluştur
        Student s1 = new Student("1001", "Cansu", "CE");

        // Yüksek lisans öğrencisi
        GraduateStudent gs1 = new GraduateStudent("2001", "Cansu YL", "CE", 5000);

        // Ders oluştur
        Course c1 = new Course("CE101", "Nesne Tabanlı Programlama", 4);

        // Eğitmen
        Instructor ins = new Instructor("3001", "Tuğberk Kocatekin", "CE");

        // RegistrationService
        RegistrationService service = new RegistrationService();

        // Kayıt işlemleri (service üzerinden)
        service.register(s1, c1);
        service.register(s1, c1); // aynı ders, ikinci kayıt engellenmeli

        System.out.println("Normal öğrenci:");
        System.out.println(s1);

        System.out.println("\nGraduate öğrenci:");
        System.out.println(gs1);
        System.out.println("Tuition: " + gs1.calculateTuition());

        System.out.println("\nDers bilgisi:");
        System.out.println(c1);

        System.out.println("\nEğitmen bilgisi:");
        System.out.println(ins);

        System.out.println("\nKayıtlar:");
        service.getAllRegistrations().forEach(System.out::println);
    }
}

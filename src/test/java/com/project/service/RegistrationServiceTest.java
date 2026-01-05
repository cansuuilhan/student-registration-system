package com.project.service;

import com.project.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    private RegistrationService service;

    private Student student;
    private Course course1;
    private Course course2;
    private Instructor instructor;
    private CourseSection section1;
    private CourseSection section2;

    @BeforeEach
    void setUp() {
        service = new RegistrationService();

        student = new Student("S1", "Ali", "CENG");

        instructor = new Instructor("I1", "Dr. Smith", "CENG");

        course1 = new Course("C1", "OOP", 4);
        course2 = new Course("C2", "DS", 4);

        // Aynı zaman dilimi: çakışma testi için
        TimeSlot ts1 = new TimeSlot("MONDAY", 9, 11);
        TimeSlot ts2 = new TimeSlot("MONDAY", 9, 11);

        section1 = new CourseSection(course1, instructor, ts1, 2);
        section2 = new CourseSection(course2, instructor, ts2, 2);
    }

    @Test
    void shouldRegisterStudentSuccessfully() {
        boolean result = service.register(student, section1);

        assertTrue(result);
        assertEquals(1, student.getRegisteredCourses().size());
        assertEquals(1, service.getAllRegistrations().size());
    }

    @Test
    void shouldNotAllowDuplicateRegistrationToSameCourse() {
        assertTrue(service.register(student, section1));
        // Aynı dersin aynı şubesine tekrar kayıt denemesi
        assertFalse(service.register(student, section1));

        assertEquals(1, student.getRegisteredCourses().size());
        assertEquals(1, service.getAllRegistrations().size());
    }

    @Test
    void shouldNotAllowRegistrationWhenSectionIsFull() {
        // kapasiteyi 1 yapmak için yeni section oluşturalım
        CourseSection cap1Section = new CourseSection(course1, instructor, new TimeSlot("TUESDAY", 10, 12), 1);

        Student s2 = new Student("S2", "Veli", "CENG");

        assertTrue(service.register(student, cap1Section));
        assertFalse(service.register(s2, cap1Section)); // kontenjan dolu olmalı

        assertEquals(1, cap1Section.getEnrolledStudents().size());
        assertEquals(1, service.getAllRegistrations().size()); // sadece ilk kayıt
    }

    @Test
    void shouldNotAllowRegistrationWhenTimeConflicts() {
        assertTrue(service.register(student, section1));
        // section2 aynı saat (MONDAY 9-11) => çakışma olmalı
        assertFalse(service.register(student, section2));

        assertEquals(1, student.getRegisteredCourses().size());
        assertEquals(1, service.getAllRegistrations().size());
    }

    @Test
    void shouldDropCourseSuccessfully() {
        assertTrue(service.register(student, section1));

        boolean dropped = service.drop(student, section1);
        assertTrue(dropped);

        assertEquals(0, student.getRegisteredCourses().size());
        assertEquals(0, service.getAllRegistrations().size());
    }
}

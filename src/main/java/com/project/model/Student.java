package com.project.model;

import com.project.service.RegistrationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student implements Registrable {

    private String id;
    private String name;
    private String department;

    // Öğrencinin kayıtlı olduğu ders şubeleri
    private List<CourseSection> registeredCourses;

    public Student(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    /**
     * Base tuition (GraduateStudent bunu override eder)
     */
    public double calculateTuition() {
        return 2000;
    }

    // Yeni şube ekleme
    public void addCourse(CourseSection section) {
        registeredCourses.add(section);
    }

    // Şube kaldırma (drop için gerekli)
    public void removeCourse(CourseSection section) {
        registeredCourses.remove(section);
    }

    // Aynı dersin (CourseId) herhangi bir şubesine kayıtlı mı?
    public boolean isRegisteredToCourse(String courseId) {
        for (CourseSection section : registeredCourses) {
            if (section.getCourse().getCourseId().equalsIgnoreCase(courseId)) {
                return true;
            }
        }
        return false;
    }

    // Daha önce aynı şubeye kayıtlı mı? (opsiyonel)
    public boolean isAlreadyRegisteredToSection(CourseSection section) {
        return registeredCourses.contains(section);
    }

    /**
     * Zaman çakışması kontrolü:
     * Yeni şubenin TimeSlot'u, öğrencinin mevcut kayıtlı şubeleriyle çakışıyorsa true döner.
     */
    public boolean hasTimeConflict(CourseSection newSection) {
        if (newSection == null) return false;

        TimeSlot newSlot = newSection.getTimeSlot();
        if (newSlot == null) return false;

        for (CourseSection existing : registeredCourses) {
            TimeSlot existingSlot = existing.getTimeSlot();
            if (existingSlot != null && existingSlot.conflictsWith(newSlot)) {
                return true;
            }
        }
        return false;
    }

    // Dışarıya güvenli liste dönmek daha iyi
    public List<CourseSection> getRegisteredCourses() {
        return Collections.unmodifiableList(registeredCourses);
    }

    /**
     * Arayüz gereksinimi (Registrable):
     * Ders kaydı davranışını servis üzerinden gerçekleştirir.
     */
    @Override
    public boolean registerCourse(CourseSection section, RegistrationService service) {
        return service.register(this, section);
    }

    /**
     * Arayüz gereksinimi (Registrable):
     * Ders bırakma davranışını servis üzerinden gerçekleştirir.
     */
    @Override
    public boolean dropCourse(CourseSection section, RegistrationService service) {
        return service.drop(this, section);
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + department + ")";
    }
}

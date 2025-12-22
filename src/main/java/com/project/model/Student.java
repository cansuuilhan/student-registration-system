package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

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

    // Yeni ders ekleme
    public void addCourse(CourseSection section) {
        registeredCourses.add(section);
    }

    // Daha önce kayıtlı mı kontrolü
    public boolean isAlreadyRegistered(CourseSection section) {
        return registeredCourses.contains(section);
    }

    public List<CourseSection> getRegisteredCourses() {
        return registeredCourses;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + department + ")";
    }
}

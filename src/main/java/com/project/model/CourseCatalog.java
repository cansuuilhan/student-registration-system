package com.project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CourseCatalog sınıfı, sistemdeki tüm dersleri yönetir.
 */
public class CourseCatalog {

    private List<Course> courses;

    public CourseCatalog() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(String courseId) {
        courses.removeIf(course -> course.getCourseId().equalsIgnoreCase(courseId));
    }

    public List<Course> getAllCourses() {
        return Collections.unmodifiableList(courses);
    }

    public Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equalsIgnoreCase(courseId)) {
                return course;
            }
        }
        return null;
    }
}

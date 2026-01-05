package com.project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CourseCatalog sınıfı, sistemdeki tüm dersleri yönetir.
 */
public class CourseCatalog {

    private final List<Course> courses;

    public CourseCatalog() {
        this.courses = new ArrayList<>();
    }

    /**
     * Aynı courseId varsa eklemeyi engeller.
     * @return eklendiyse true, zaten varsa false
     */
    public boolean addCourse(Course course) {
        if (course == null || course.getCourseId() == null) return false;

        String id = course.getCourseId().trim();
        if (id.isEmpty()) return false;

        if (findCourseById(id) != null) return false;

        courses.add(course);
        return true;
    }

    /**
     * @return silindiyse true, bulunamadıysa false
     */
    public boolean removeCourse(String courseId) {
        if (courseId == null) return false;

        String id = courseId.trim();
        if (id.isEmpty()) return false;

        return courses.removeIf(course -> course.getCourseId().equalsIgnoreCase(id));
    }

    public List<Course> getAllCourses() {
        return Collections.unmodifiableList(courses);
    }

    public Course findCourseById(String courseId) {
        if (courseId == null) return null;

        String id = courseId.trim();
        if (id.isEmpty()) return null;

        for (Course course : courses) {
            if (course.getCourseId().equalsIgnoreCase(id)) {
                return course;
            }
        }
        return null;
    }

    public boolean contains(String courseId) {
        return findCourseById(courseId) != null;
    }
}

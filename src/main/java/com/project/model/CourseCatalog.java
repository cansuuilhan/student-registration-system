package com.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseCatalog sınıfı, sistemdeki tüm dersleri yönetir.
 */
public class CourseCatalog {

    private List<Course> courses;

    public CourseCatalog() {
        this.courses = new ArrayList<>();
    }

    /**
     * Yeni ders ekler.
     *
     * @param course eklenecek ders
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Ders siler.
     *
     * @param courseId silinecek dersin ID'si
     */
    public void removeCourse(String courseId) {
        courses.removeIf(course -> course.getCourseId().equals(courseId));
    }

    /**
     * Tüm dersleri listeler.
     *
     * @return ders listesi
     */
    public List<Course> getAllCourses() {
        return courses;
    }

    /**
     * ID'ye göre ders bulur.
     *
     * @param courseId ders ID
     * @return Course veya null
     */
    public Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}

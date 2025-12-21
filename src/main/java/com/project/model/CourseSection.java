package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class CourseSection {

    private Course course;
    private Instructor instructor;
    private TimeSlot timeSlot;
    private int capacity;
    private List<Student> enrolledStudents;

    public CourseSection(Course course, Instructor instructor, TimeSlot timeSlot, int capacity) {
        this.course = course;
        this.instructor = instructor;
        this.timeSlot = timeSlot;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
    }

    public Course getCourse() {
        return course;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public boolean enrollStudent(Student student) {
        if (isFull()) {
            return false;
        }
        enrolledStudents.add(student);
        return true;
    }

    @Override
    public String toString() {
        return course.getCourseId() + " - " + course.getCourseName()
                + " | " + instructor.getName()
                + " | " + timeSlot;
    }
}

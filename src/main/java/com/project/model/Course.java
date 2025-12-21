package com.project.model;

public class Course {

    private String courseId;
    private String courseName;
    private int credit;
    private String instructorName;

    public Course(String courseId, String courseName, int credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.instructorName = null;
    }

    public Course(String courseId, String courseName, int credit, String instructorName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.instructorName = instructorName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public String getInstructorName() {
        return instructorName;
    }

    @Override
    public String toString() {
        return courseId + " - " + courseName + " (" + credit + " credits)";
    }
}

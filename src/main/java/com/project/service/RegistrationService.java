package com.project.service;

import com.project.model.Course;
import com.project.model.Registration;
import com.project.model.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationService {

    private List<Registration> registrations;

    public RegistrationService() {
        this.registrations = new ArrayList<>();
    }

    public boolean register(Student student, Course course) {
        if (isAlreadyRegistered(student, course)) {
            System.out.println("Öğrenci bu derse zaten kayıtlı.");
            return false;
        }

        Registration registration = new Registration(student, course);
        registrations.add(registration);
        return true;
    }

    private boolean isAlreadyRegistered(Student student, Course course) {
        for (Registration r : registrations) {
            if (r.getStudent().getId().equals(student.getId())
                    && r.getCourse().getCourseId().equals(course.getCourseId())) {
                return true;
            }
        }
        return false;
    }

    public List<Registration> getAllRegistrations() {
        return registrations;
    }
}

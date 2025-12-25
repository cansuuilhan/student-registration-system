package com.project.model;

import com.project.service.RegistrationService;

public interface Registrable {
    boolean registerCourse(CourseSection section, RegistrationService service);
    boolean dropCourse(CourseSection section, RegistrationService service);
}

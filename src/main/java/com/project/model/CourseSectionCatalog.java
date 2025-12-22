package com.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseSectionCatalog, açılan dersleri (CourseSection)
 * tutar ve listeler.
 */
public class CourseSectionCatalog {

    private List<CourseSection> sections;

    public CourseSectionCatalog() {
        this.sections = new ArrayList<>();
    }

    public void addSection(CourseSection section) {
        sections.add(section);
    }

    public List<CourseSection> getAllSections() {
        return sections;
    }

    public void printSections() {
        System.out.println("Açılan Dersler:");
        for (CourseSection section : sections) {
            System.out.println(section);
        }
    }
}

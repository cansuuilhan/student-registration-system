package com.project.service;

import com.project.model.CourseSection;
import com.project.model.Registration;
import com.project.model.Student;
import com.project.model.TimeSlot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RegistrationService {

    private List<Registration> registrations;

    private static final String SEP = "|";

    public RegistrationService() {
        this.registrations = new ArrayList<>();
    }

    public boolean register(Student student, CourseSection section) {
        String courseId = section.getCourse().getCourseId();

        // 1) Aynı dersin herhangi bir şubesine tekrar kayıt engeli
        if (student.isRegisteredToCourse(courseId)) {
            System.out.println("Öğrenci bu derse zaten kayıtlı: " + courseId);
            return false;
        }

        // 2) Kontenjan kontrolü
        if (section.isFull()) {
            System.out.println("Kontenjan dolu: " + courseId);
            return false;
        }

        // 3) Zaman çakışması kontrolü
        if (student.hasTimeConflict(section)) {
            System.out.println("Zaman çakışması var: " + courseId);
            return false;
        }

        // 4) Section'a kayıt
        boolean enrolled = section.enrollStudent(student);
        if (!enrolled) {
            System.out.println("Kayıt başarısız (öğrenci zaten bu şubede olabilir): " + courseId);
            return false;
        }

        // 5) Öğrencinin listesine ekle
        student.addCourse(section);

        // 6) Registration kaydı oluştur
        Registration registration = new Registration(student, section);
        registrations.add(registration);

        System.out.println("Kayıt başarılı: " + courseId);
        return true;
    }

    /**
     * Ders çıkarma (Drop)
     */
    public boolean drop(Student student, CourseSection section) {
        String courseId = section.getCourse().getCourseId();

        // Öğrenci bu derse kayıtlı değilse bırakamaz
        if (!student.isRegisteredToCourse(courseId)) {
            System.out.println("Drop başarısız: Öğrenci bu derse kayıtlı değil: " + courseId);
            return false;
        }

        // Student listesinden çıkar
        student.removeCourse(section);

        // Section listesinden çıkar
        boolean droppedFromSection = section.dropStudent(student);
        if (!droppedFromSection) {
            System.out.println("Uyarı: Öğrenci section listesinde bulunamadı: " + courseId);
        }

        // Registrations listesinden ilgili kaydı sil
        registrations.removeIf(r ->
                r.getStudent().getId().equals(student.getId()) &&
                        r.getCourse().getCourseId().equalsIgnoreCase(courseId)
        );

        System.out.println("Ders çıkarıldı: " + courseId);
        return true;
    }

    public List<Registration> getAllRegistrations() {
        return Collections.unmodifiableList(registrations);
    }

    // =========================================================
    // KALICI KAYIT (DOSYAYA YAZ / DOSYADAN OKU)
    // =========================================================

    /**
     * Kayıtları dosyaya yazar.
     * Format: studentId|courseId|day|startHour|endHour
     */
    public void saveToFile(String filePath) {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(filePath))) {
            for (Registration r : registrations) {
                String studentId = r.getStudent().getId();

                CourseSection section = r.getSection();
                String courseId = section.getCourse().getCourseId();

                TimeSlot ts = section.getTimeSlot();

                String line = studentId + SEP
                        + courseId + SEP
                        + ts.getDay() + SEP
                        + ts.getStartHour() + SEP
                        + ts.getEndHour();

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kayıtlar dosyaya yazılamadı: " + e.getMessage());
        }
    }

    /**
     * Program açılırken kayıtları geri yükler.
     * Kuralların korunması için register(...) üzerinden yüklenir.
     */
    public void loadFromFile(String filePath, Map<String, Student> students, List<CourseSection> sections) {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) return;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length != 5) continue;

                String studentId = p[0].trim();
                String courseId = p[1].trim();
                String day = p[2].trim();
                int start = Integer.parseInt(p[3].trim());
                int end = Integer.parseInt(p[4].trim());

                Student st = students.get(studentId);
                if (st == null) continue;

                CourseSection sec = findSection(sections, courseId, day, start, end);
                if (sec == null) continue;

                // kontenjan/çakışma/duplicate kuralları korunur
                this.register(st, sec);
            }
        } catch (Exception e) {
            System.out.println("Kayıtlar dosyadan okunamadı: " + e.getMessage());
        }
    }

    private CourseSection findSection(List<CourseSection> sections, String courseId, String day, int start, int end) {
        for (CourseSection s : sections) {
            boolean sameCourse = s.getCourse().getCourseId().equalsIgnoreCase(courseId);

            TimeSlot ts = s.getTimeSlot();
            boolean sameTime = ts.getDay().equalsIgnoreCase(day)
                    && ts.getStartHour() == start
                    && ts.getEndHour() == end;

            if (sameCourse && sameTime) return s;
        }
        return null;
    }
}

package com.project;

import com.project.model.*;
import com.project.service.RegistrationService;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Map<String, String> credentials = new HashMap<>();
    private static final Map<String, Student> users = new HashMap<>();

    private static RegistrationService service;
    private static CourseCatalog catalog;
    private static List<CourseSection> sections;

    // Kalıcı kayıt dosyası
    private static final String REG_FILE = "registrations.txt";

    public static void main(String[] args) {
        initSystem();

        // Program açılırken eski kayıtları yükle
        service.loadFromFile(REG_FILE, users, sections);

        while (true) {
            Student activeStudent = login();
            if (activeStudent == null) {
                System.out.println("Program kapatılıyor.");
                return;
            }

            boolean logout = mainMenu(activeStudent);
            if (!logout) {
                return; // programdan tamamen çık
            }
        }
    }

    // -----------------------------
    // Sistem kurulumu
    // -----------------------------
    private static void initSystem() {
        service = new RegistrationService();
        catalog = new CourseCatalog(); // menüden ders listesi kaldırıldı ama sistemde durabilir
        sections = new ArrayList<>();

        Instructor ins = new Instructor("3001", "Tuğberk Kocatekin", "CE");

        Student s1 = new Student("1001", "Cansu", "CE");
        Student s2 = new Student("1002", "Ayşe", "CE");
        GraduateStudent gs1 = new GraduateStudent("2001", "Cansu YL", "CE", 5000);

        addUser(s1, "1234");
        addUser(s2, "1111");
        addUser(gs1, "9999");

        Course ce101 = new Course("CE101", "Nesne Tabanlı Programlama", 4);
        Course ce102 = new Course("CE102", "Veri Yapıları", 4);
        Course ce201 = new Course("CE201", "Bilgisayar Ağları", 4);

        catalog.addCourse(ce101);
        catalog.addCourse(ce102);
        catalog.addCourse(ce201);

        sections.add(new CourseSection(ce101, ins, new TimeSlot("MONDAY", 9, 11), 30));
        sections.add(new CourseSection(ce102, ins, new TimeSlot("MONDAY", 10, 12), 30));
        sections.add(new CourseSection(ce201, ins, new TimeSlot("TUESDAY", 13, 15), 1));
    }

    private static void addUser(Student s, String password) {
        users.put(s.getId(), s);
        credentials.put(s.getId(), password);
    }

    // -----------------------------
    // LOGIN
    // -----------------------------
    private static Student login() {
        System.out.println("\n========================================");
        System.out.println("   ÖĞRENCİ DERS KAYIT SİSTEMİ - LOGIN");
        System.out.println("========================================");

        for (int i = 1; i <= 3; i++) {
            System.out.print("Öğrenci No: ");
            String id = scanner.nextLine().trim();

            System.out.print("Şifre: ");
            String pass = scanner.nextLine().trim();

            if (credentials.containsKey(id) && credentials.get(id).equals(pass)) {
                Student st = users.get(id);
                System.out.println("\nGiriş başarılı. Hoş geldiniz: " + st.getName());
                return st;
            }
            System.out.println("Hatalı giriş. Kalan hak: " + (3 - i));
        }
        return null;
    }

    // -----------------------------
    // ANA MENÜ
    // -----------------------------
    private static boolean mainMenu(Student activeStudent) {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("ANA MENÜ");
            System.out.println("Aktif Öğrenci: " + activeStudent.getId() + " - " + activeStudent.getName());
            System.out.println("========================================");
            System.out.println("1) Şube Listesi Görüntüle");
            System.out.println("2) Derse Kayıt Ol");
            System.out.println("3) Ders Çıkar");
            System.out.println("4) Kayıtlı Derslerimi Gör");
            System.out.println("5) Çıkış Yap");
            System.out.println("0) Programdan Çık");

            int choice = readInt("Seçiminiz: ");

            switch (choice) {
                case 1 -> listSections();
                case 2 -> registerStep(activeStudent);
                case 3 -> removeCourseStep(activeStudent);
                case 4 -> showStudentCourses(activeStudent);
                case 5 -> {
                    System.out.println("Oturum kapatıldı. Giriş ekranına yönlendiriliyorsunuz.");
                    return true;   // tekrar login
                }
                case 0 -> {
                    System.out.println("Programdan çıkılıyor.");
                    return false;  // tamamen çık
                }
                default -> System.out.println("Geçersiz seçim.");
            }

            pressEnter();
        }
    }

    // -----------------------------
    // İŞLEMLER
    // -----------------------------
    private static void listSections() {
        System.out.println("\n--- Şube Listesi ---");
        for (int i = 0; i < sections.size(); i++) {
            System.out.println((i + 1) + ") " + sections.get(i));
        }
    }

    /**
     * Derse kayıt ol - İPTAL desteği:
     * 0 girilirse ana menüye döner.
     */
    private static void registerStep(Student student) {
        System.out.println("\n--- Derse Kayıt Ol ---");
        listSections();
        System.out.println("0) İptal (Ana menüye dön)");

        int choice = readInt("Şube numarası seçin: ");
        if (choice == 0) {
            System.out.println("İşlem iptal edildi.");
            return;
        }

        int idx = choice - 1;
        if (idx < 0 || idx >= sections.size()) {
            System.out.println("Geçersiz seçim.");
            return;
        }

        boolean ok = student.registerCourse(sections.get(idx), service);
        if (ok) {
            service.saveToFile(REG_FILE);
        }
    }

    /**
     * Ders çıkar - İPTAL desteği:
     * 0 girilirse ana menüye döner.
     */
    private static void removeCourseStep(Student student) {
        System.out.println("\n--- Ders Çıkar ---");
        List<CourseSection> regs = student.getRegisteredCourses();

        if (regs.isEmpty()) {
            System.out.println("Kayıtlı ders yok.");
            return;
        }

        for (int i = 0; i < regs.size(); i++) {
            System.out.println((i + 1) + ") " + regs.get(i));
        }
        System.out.println("0) İptal (Ana menüye dön)");

        int choice = readInt("Çıkarılacak ders numarası: ");
        if (choice == 0) {
            System.out.println("İşlem iptal edildi.");
            return;
        }

        int idx = choice - 1;
        if (idx < 0 || idx >= regs.size()) {
            System.out.println("Geçersiz seçim.");
            return;
        }

        boolean ok = student.dropCourse(regs.get(idx), service);
        if (ok) {
            service.saveToFile(REG_FILE);
        }
    }

    private static void showStudentCourses(Student student) {
        System.out.println("\n--- Kayıtlı Derslerim ---");
        if (student.getRegisteredCourses().isEmpty()) {
            System.out.println("Kayıtlı ders yok.");
            return;
        }
        student.getRegisteredCourses().forEach(System.out::println);
    }

    // -----------------------------
    // UTIL
    // -----------------------------
    private static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sayı girin.");
            }
        }
    }

    private static void pressEnter() {
        System.out.print("\nDevam etmek için Enter...");
        scanner.nextLine();
    }
}
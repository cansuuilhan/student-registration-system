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
        catalog = new CourseCatalog();
        sections = new ArrayList<>();

        // --- 3 Öğretmen ---
        Instructor ins1 = new Instructor("3001", "Tuğberk Kocatekin", "CE");
        Instructor ins2 = new Instructor("3002", "Mert Yağcıoğlu", "CE");
        Instructor ins3 = new Instructor("3003", "Pınar Karadayı", "CE");

        // --- 6 Öğrenci (Numaralar artarak) ---
        // Mezun öğrenci = Cansu
        GraduateStudent gs1 = new GraduateStudent("220309044", "Cansu", "CE", 5000);

        Student s2 = new Student("220309045", "Selin", "CE");
        Student s3 = new Student("220309046", "Emre", "CE");
        Student s4 = new Student("220309047", "Hayat", "CE");
        Student s5 = new Student("220309048", "Murat", "CE");
        Student s6 = new Student("220309049", "Melis", "CE");

        // Şifreler: öğrenci numarasının son 4 hanesi
        addUser(gs1, "9044");
        addUser(s2, "9045");
        addUser(s3, "9046");
        addUser(s4, "9047");
        addUser(s5, "9048");
        addUser(s6, "9049");

        // --- 5 Ders ---
        Course c1 = new Course("CE201", "İleri Veritabanı", 4);
        Course c2 = new Course("CE101", "Nesne Programlama", 4);
        Course c3 = new Course("CE305", "Veri Madenciliği", 4);
        Course c4 = new Course("CE203", "İşletim Sistemleri", 4);
        Course c5 = new Course("CE407", "Görüntü İşleme", 4);

        catalog.addCourse(c1);
        catalog.addCourse(c2);
        catalog.addCourse(c3);
        catalog.addCourse(c4);
        catalog.addCourse(c5);

        // --- Şubeler (CourseSection) ---
        // TimeSlot çakışmasını azaltmak için saatleri dağıttım.
        sections.add(new CourseSection(c2, ins1, new TimeSlot("MONDAY", 9, 11), 3));        // Nesne Programlama - Tuğberk
        sections.add(new CourseSection(c1, ins2, new TimeSlot("MONDAY", 11, 13), 30));       // İleri Veritabanı - Mert
        sections.add(new CourseSection(c3, ins3, new TimeSlot("TUESDAY", 10, 12), 30));      // Veri Madenciliği - Pınar
        sections.add(new CourseSection(c4, ins2, new TimeSlot("WEDNESDAY", 9, 11), 30));     // İşletim Sistemleri - Mert
        sections.add(new CourseSection(c5, ins3, new TimeSlot("THURSDAY", 13, 15), 30));     // Görüntü İşleme - Pınar
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

            // Java 8/11 uyumlu klasik switch-case
            switch (choice) {
                case 1:
                    listSections();
                    break;
                case 2:
                    registerStep(activeStudent);
                    break;
                case 3:
                    removeCourseStep(activeStudent);
                    break;
                case 4:
                    showStudentCourses(activeStudent);
                    break;
                case 5:
                    System.out.println("Oturum kapatıldı. Giriş ekranına yönlendiriliyorsunuz.");
                    return true;   // tekrar login
                case 0:
                    System.out.println("Programdan çıkılıyor.");
                    return false;  // tamamen çık
                default:
                    System.out.println("Geçersiz seçim.");
                    break;
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

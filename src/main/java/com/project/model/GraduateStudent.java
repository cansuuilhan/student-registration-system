package com.project.model;

/**
 * GraduateStudent sınıfı, Student sınıfından türetilmiş
 * yüksek lisans öğrencilerini temsil eder.
 * Registrable arayüzünü uygulayarak kayıt davranışlarını tanımlar.
 */
public class GraduateStudent extends Student implements Registrable {

    private double thesisFee;

    /**
     * GraduateStudent kurucusu.
     *
     * @param id         Öğrenci ID
     * @param name       Öğrenci adı
     * @param department Bölüm
     * @param thesisFee  Tez ücreti
     */
    public GraduateStudent(String id, String name, String department, double thesisFee) {
        super(id, name, department);
        this.thesisFee = thesisFee;
    }

    public double getThesisFee() {
        return thesisFee;
    }

    /**
     * Polimorfizm örneği: Yüksek lisans öğrencilerinin
     * eğitim ücretini hesaplar.
     *
     * @return ücret + tez ücreti
     */
    public double calculateTuition() {
        return 2000 + thesisFee;
    }

    @Override
    public void register() {
        System.out.println(getName() + " yüksek lisans öğrencisi olarak kaydedildi.");
    }

    @Override
    public void unregister() {
        System.out.println(getName() + " kaydı silindi.");
    }
}

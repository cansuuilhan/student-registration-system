package com.project.model;

/**
 * GraduateStudent sınıfı, Student sınıfından türetilmiş
 * yüksek lisans öğrencilerini temsil eder.
 */
public class GraduateStudent extends Student {

    private double thesisFee;

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
     */
    @Override
    public double calculateTuition() {
        return 2000 + thesisFee;
    }
}

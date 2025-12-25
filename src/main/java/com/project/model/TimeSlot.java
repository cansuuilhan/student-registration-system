package com.project.model;

/**
 * TimeSlot sınıfı, bir dersin haftanın hangi günü
 * hangi saatler arasında yapıldığını temsil eder.
 */
public class TimeSlot {

    private String day;
    private int startHour;
    private int endHour;

    public TimeSlot(String day, int startHour, int endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    /**
     * Aynı gün ve saat aralığı kesişiyorsa true döner.
     * Örn: 9-11 ile 10-12 çakışır.
     */
    public boolean conflictsWith(TimeSlot other) {
        if (other == null) return false;

        // Gün farklıysa çakışma yok (case-insensitive)
        if (!this.day.equalsIgnoreCase(other.day)) {
            return false;
        }

        // Saat aralığı çakışma kontrolü: [start, end) kesişimi
        return this.startHour < other.endHour && other.startHour < this.endHour;
    }

    @Override
    public String toString() {
        return day + " " + startHour + ":00 - " + endHour + ":00";
    }
}

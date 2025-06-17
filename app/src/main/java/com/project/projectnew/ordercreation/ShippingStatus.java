package com.project.projectnew.ordercreation;

import java.io.Serializable;

// Kelas ini berfungsi sebagai model data untuk satu entri status pengiriman.
public class ShippingStatus implements Serializable {
    private String statusDate;
    private String statusTime;
    private String statusDescription;
    private boolean isActive; // Untuk menentukan ikon mana yang digunakan (aktif atau tidak)

    public ShippingStatus(String statusDate, String statusTime, String statusDescription, boolean isActive) {
        this.statusDate = statusDate;
        this.statusTime = statusTime;
        this.statusDescription = statusDescription;
        this.isActive = isActive;
    }

    // Getters
    public String getStatusDate() { return statusDate; }
    public String getStatusTime() { return statusTime; }
    public String getStatusDescription() { return statusDescription; }
    public boolean isActive() { return isActive; }
}

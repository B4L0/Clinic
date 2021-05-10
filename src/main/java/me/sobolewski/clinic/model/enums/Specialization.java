package me.sobolewski.clinic.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Specialization {
    
    FAMILY("Lekarz rodzinny"),
    ORTHOPEDICS("Ortopedia"),
    DENTISTRY("Stomatologia"),
    NEUROLOGY("Neurologia"),
    REHABILITATION("Rehabilitacja"),
    GYNECOLOGY("Ginekologia"),
    PEDIATRICS("Pediatria"),
    PULMONOLOGY("Pulmonologia"),
    OPHTHALMOLOGY("Okulistyka"),
    LARYNGOLOGY("Laryngologia"),
    CARDIOLOGY("Kardiologia");
    
    @Getter
    private final String value;
    
    @Override
    public String toString() {
        return value;
    }
}

package me.sobolewski.clinic.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExaminationType {

    PRELIMINARY("wstępne"),
    PERIODIC("okresowe"),
    ROUTINE("kontrolne"),
    FINAL("końcowe"),
    DIAGNOSTIC("diagnostyczne"),
    SPECIALIST("specjalistyczne");
    
    @Getter
    private final String value;

    @Override
    public String toString(){
        return value;
    }
}

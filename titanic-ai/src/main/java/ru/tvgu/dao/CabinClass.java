package ru.tvgu.dao;

public enum CabinClass {
    FIRST,
    SECOND,
    THIRD,
    CREW_MEMBER;

    public static CabinClass ofOrdinal(int ordinal) {
        return CabinClass.values()[ordinal];
    }
}

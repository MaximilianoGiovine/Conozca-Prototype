package com.conozca.prototype.model;

public enum Privilege {
    EDITOR("EDITOR"),
    ADMIN("ADMIN");

    private final String value;

    Privilege(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    // Método útil para validar privilegios desde String
    public static boolean isValid(String privilege) {
        if (privilege == null) return false;

        for (Privilege p : Privilege.values()) {
            if (p.getValue().equals(privilege.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static Privilege fromString(String privilege) {
        if (privilege == null) return EDITOR; // default

        for (Privilege p : Privilege.values()) {
            if (p.getValue().equals(privilege.toUpperCase())) {
                return p;
            }
        }
        return EDITOR; // default si no coincide
    }
}
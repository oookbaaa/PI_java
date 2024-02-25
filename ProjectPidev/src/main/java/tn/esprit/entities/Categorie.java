package tn.esprit.entities;

public enum Categorie {
    PortDeCharge("Port de charge"),
    PiecesDetachees("Pièces détachées"),
    Accessoires("Accessoires"),
    AUTRE("Autre");

    private final String nomAffiche;

    Categorie(String nomAffiche) {
        this.nomAffiche = nomAffiche;
    }

    public String getNomAffiche() {
        return nomAffiche;
    }

    @Override
    public String toString() {
        return nomAffiche;
    }
    public String nomAffiche() {
        switch (this) {
            case PortDeCharge:
                return "Port de charge";
            case PiecesDetachees:
                return "Pièces détachées";
            case Accessoires:
                return "Accessoires";
            case AUTRE:
                return "Autre";
            default:
                return "Catégorie inconnue";
        }
    }
}

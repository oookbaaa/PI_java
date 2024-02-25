package tn.esprit.entities;

public enum StatutCommande {
    EnAttente("En attente"),
    Expediee("Expédiée"),
    Livree("Livrée");

    private final String nomAffiche;

    StatutCommande(String nomAffiche) {
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
            case EnAttente:
                return "En attente";
            case Expediee:
                return "Expédiée";
            case Livree:
                return "Livrée";
            default:
                return "Statut inconnu";
        }
    }
}

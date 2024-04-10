package com.dysonsurvivors.dysonsurvivors.Models;

public class JoueurSingleton {
    private static Joueur instance;

    // Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur de la classe
    private JoueurSingleton() {
        // Initialisation du personnage
        instance = new Joueur("ThierryLeLooser", 100); // Vous pouvez ajuster les paramètres selon vos besoins
    }

    // Méthode statique pour récupérer l'instance unique du personnage
    public static Joueur getInstance() {
        // Créer une instance uniquement si elle n'existe pas déjà
        if (instance == null) {
            synchronized (JoueurSingleton.class) {
                if (instance == null) {
                    new JoueurSingleton();
                }
            }
        }
        return instance;
    }
}
# Pitch du projet

## TODO : Décrivez votre projet
Montrez qu'il mobilise des techniques de POO avancée
Comment allez-vous utiliser les patrons de conception ?
Comment allez-vous utiliser l'architecture MVC ?

Notre projet java sera un jeu vidéo basé sur le concept du jeu Vampire Survivors.
Un personnage qui affronte des vagues d'ennemis ce qui le fera évoluer au fil du temps.

Le projet sera basé sur une architecture **MVC** : 
    - Il utilisera des **vues** basées sur **JavaFX**. 
    - Chaque entité aura un **modèle** et un **controller** lié. 
    - Chaque modèle pourra implémenter des **interfaces/annotations** si nécessaire, et pourront **hériter d'autres modèles** au besoin (Monstre -> MonstreA, MonstreB).

## Exemples d'utilisations des patrons de conceptions

* **Observateur/Observable**
Surveiller les événements du jeu, comme la mort d'un ennemi ou la collecte d'un objet par le joueur.

* **Stratégie**
 Gérer différents comportements de déplacement pour les ennemis, comme la marche aléatoire, la poursuite du joueur, etc.
* **Fabrique**
 Créer des ennemis avec des caractéristiques différentes (vitesse, santé, force, etc.) en fonction du niveau du joueur.

* **Singleton**
Gérer des ressources partagées telles que le joueur, le gestionnaire d'effets sonores, etc.

* **Décorateur**
Pour ajouter des capacités spéciales à certaines armes ou à certains ennemis, comme empoisonner, brûler, ou geler.
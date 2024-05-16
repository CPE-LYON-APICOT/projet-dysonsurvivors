
# Retro-conception

**Binome 1 : Alexandre Claucigh**
**Binome 2 : Nils Oberholz**

Complétez ce document pour décrire votre projet, les difficultés rencontrées, les design patterns mis en oeuvre, les améliorations possibles, et en quoi la POO vous a été utile.

> **Faites le avec sérieux, ce document ainsi que votre code seront évalués.**
Si vous considérez que votre code est quasi-parfait, je vais chercher les erreurs et en trouver, et cela vous pénalisera.
Si vous êtes critique envers vous-même et que vous expliquez correctement vos difficultés, vous serez "à moitié" pardonné.

Ce n'est pas grave de ne pas avoir été au bout de ce que vous vouliez faire, comportez vous comme un ingénieur qui doit rendre des comptes à son client, et qui doit expliquer pourquoi il n'a pas pu tout faire.
Pour rappel le client n'est pas un developpeur (sinon il ne vous aurait pas payé une fortune pour le faire à sa place), vous devez lui expliquer de manière simple et claire les problèmes rencontrés, en vous justifiant 
>Imaginez que vous avez codé Mortal Kombat 
Ne dites pas "je n'ai pas eu le temps de tout faire", mais plutôt "j'ai préféré me concentrer sur la création des spectaculaires "Finish Him" des personnages car c'est un élément essentiel du gameplay, cependant la difficulté dynamique en fonction de la maîtrise du joueur n'a pas pu être implémentée à temps, compte tenu que les critères de maîtrises sont difficilement modélisables, toutefois nous pourrions envisager d'implémenter que plus le combat dure longtemps, plus les coups portés sont puissants, ce qui est rapide à implémenter et lors d'une mise à jour, nous pourrions remplacer ce système par quelque chose de plus élaboré"

Aussi, en entreprise, vous serez confronté à des programmes très mal codés, et vous allez galérer à les comprendre, vous risquez d'essayer de les corriger et tomber dans les mêmes ecueils que les développeurs précédents.
Pour cela, il est courrant de tenir un jour un Document d'Architecture Technique (DAT) qui explique comment fonctionne le programme, et comment le reprendre ainsi qu'un document de réversibilité qui explique comment reprendre le code de quelqu'un d'autre.
(C'est obligatoire pour les marchés publics de prévoir une réversibilité, c'est à dire que le client peut vous dégager et une autre entreprise doit pouvoir reprendre votre code sans difficulté)
Dans ces documents, il ne s'agit pas de cacher la poussière sous le tapis, il faut être honnête et proposer une vision d'ensemble de votre code, et expliquer pourquoi vous avez fait des choix, et pourquoi vous n'avez pas fait d'autres choix, il est souvent question de compromis (on fait un truc pas ouf pour gagner du temps, mais la qualité du code en pâtit, etc.)
> Vous pouvez dire : "Pour la gestion des collisions, nous utilisons une librairie tierce, toutefois celle-ci ne gère que les collisions entre des rectangles, au fur et à mesure des itérations, des ennemis de grande taille et de forme complexe sont apparus, toutefois, nous avons conservé une hitbox rectangulaire, en résulte que le joueur peut être touché alors que visuellement, il n'est pas en contact avec l'ennemi ; nous avions également envisagé de créer plusieurs hitbox de tailles différentes sur un même ennemi afin de mieux coller à la forme de celui-ci, toutefois, les performances du jeu ont étés trop dégradées"



---
# Partie "Client" (pas trop technique) :

## Objectif du projet

[Décrivez ici l'objectif initial du projet, ne cherchez pas à le minorer si vous n'avez pas tout fini, décrivez ce que vous avez voulu faire]

Notre objectif initial était de faire un jeu se rapprochant de "Vampire Survivor". Dans se jeu, on incarne un personnage qui doit échapper
à des hordes de monstres de plus en plus puissantes. Pour survivre le joueur doit les abattre pour progresser en niveau et obtenir plus d'équipement.

## Résultat

La majorité des fonctionnalité essentielles du jeu ont été implémentées sous une forme basique.

- Personnage qui se déplace sur une carte et attaque automatiquement.
- Hordes de monstres qui évoluent avec le temps et attaquent le joueur.
- Menu pause avec quelques options basiques et inventaire visible.
- Système d'expérience, de niveaux et de statistiques pour le joueur.
- Système de loot aléatoire pour les monstres.
- Différentes variantes de monstres en fonction de leur puissance (visuellement).

### Améliorations possibles

[Décrivez ici les améliorations que vous auriez pu apporter si vous aviez eu plus de temps]

- Plus de types de monstres (voire boss)
- Plus d'objets différents (Armes, Armures, Consommables)
- Equilibrage du jeu
- Collisions du terrain

---
# Partie "Développeur" (plus technique) :


### Implémentations remarquables

[Si pendant votre implémentation, vous trouvez que vous pouvez être particulièrment fiers d'une partie de votre code, décrivez là ici ; par exemple si vous avez généré une carte de manière procédurale, ou à l'aide d'un fichier]

Nous sommes fiers du rendu visuel mais également de la gestion de nos monstres. Que ce soit leur apparition, apparence, déplacement ou statistiques :
    - Apparition : Les monstres apparaissent de manière aléatoire sur la carte
    - Apparence : Ils ont 3 skins différents et chacun d'eux à une variante "Elite" si le monstre à un certain niveau.
    - Déplacement : Un monstre peu avoir 3 types de déplacements différents (boiter, marcher, courrir). De plus, les déplacements sont  fait de manière à ce que leur objectif d'arrivé soit la position du joueur tout en étant fluides et aggrémentés de petites variations de trajectoire pour éviter que tous les monstres s'empilent les uns sur les autres.
    - Statistiques : Chaque monstre à des statistiques qui évolue en fonction de leur niveau qui dépend de la vague d'apparition.



### Faiblesses du code

[C'est ici que vous me dites ce que vous savez que vous avez mal fait, expliquez pourquoi vous avez fait ce choix (manque de temps, manque de compétence, trop pénible à faire, etc.)]

- Le fonctionnement de chaque arme n'est pas assez modulé et modulable car tout est quasiment dans la même fonction.
- La caméra (et l'UI qui en dépend) qui n'est pas du tout adaptée au plein écran mais nous avons fait ce choix car ce genre de changement sont particulièrement chronophages et peuvent rapidement bloquer le bon fonctionnement du jeu en cas de problème.


### Difficultés rencontrées

[Expliquez ici la difficulté rencontrée et comment vous l'avez contournée]

#### 1. Chargement des fichiers
Comportement étrange quant au chargement des fichiers (images, musiques). Notre projet refusait de trouver certains fichiers de manière assez "aléatoire" même si les ressources étaient dans le même package que le fichier qui les appelait. Nous avons généralement déplacé les ressources dans un package différent pour que celles-ci soient appelées depuis un autre endroit. Parfois renommer le fichier depuis l'explorateur de fichier windows était également une solution.

#### 2. Gestion des collisions
La gestion des collisions fonctionne mais elle fut complexe au début car c'était les mauvaises coordonnées qui était manipulées. Exemple : Notre personnage était considéré en (0;0) au centre de l'écran et les ennemies en (0;0) là où ils apparaissaient. En résultait l'impossibilité pour leur hitbox de se rencontrer. Nous avons donc utilisé les coordonnées de leur Pane sur le Pan principale afin d'obtenir des coordonnées différentes.

#### 3. Restructuration du système d'animation
Un autre problème est survenu suite au début du jeu, car nous avions modélisé les ennemis et le joueur avec des cercles. Nous avons ensuite voulu ajouter des animations, donc remplacer tous les objets cercles par des objet de type Pane, ce qui a induit de modifier une grande partie du code et des calculs de positions et coordonnées.


### *Design Patterns* mis en oeuvre

#### 1. [Factory]
[Décrivez ici brièvement le design pattern utilisé et pourquoi]

Nous avons utilisé une factory afin de générer des hordes de monstres évolutives.
<pre>
```java
public class FMonstre {
    public Monstre creerMonstre(int niveau, int atk, String spriteImage, LootTable lootTable) {
        String nom = "Monstre";
        int pvMax = 10 * niveau;
        int attaque = atk + niveau;
        int dropExp = 10 * niveau;
        return new Monstre(nom, pvMax, genererDeplacement(), attaque, spriteImage, lootTable, dropExp);
    }

    private IDeplacementMonstre genererDeplacement() {
        //Générer un déplacement aléatoire parmi SBoiter, SMarcher, SCourir
        double random = Math.random();
        if (random < 0.33) {
            return new SMarcher();
        } else if (random < 0.66) {
            return new SCourir();
        } else {
            return new SBoiter();
        }

    }
}
```
</pre>

#### 2. [Singleton]


Nous avons utilisé un Singleton qui permet de maintenir l'unicité d'un joueur au sein d'une partie.
<pre>
```java
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
```
</pre>

#### 3. [Stratégie]

Les stratégies nous permettent de créé différentes méthodes de déplacement pour nos monstres ou encore pour les différents rebinding claviers.
<pre>
```java
public class SCourir implements IDeplacementMonstre{

    public void seDeplacer(Joueur joueur, Monstre monstre) {
        Pane hJoueur = joueur.getHitbox();
        Pane hMonstre = monstre.getHitbox();

        double playerCenterX = hJoueur.getLayoutX() + hJoueur.getPrefWidth() / 2;
        double playerCenterY = hJoueur.getLayoutY() + hJoueur.getPrefHeight() / 2;
        double monstreCenterX = hMonstre.getLayoutX() + hMonstre.getPrefWidth() / 2;
        double monstreCenterY = hMonstre.getLayoutY() + hMonstre.getPrefHeight() / 2;

        double angleToPlayer = Math.atan2(playerCenterY - monstreCenterY, playerCenterX - monstreCenterX);
        double speed = 1; // Ajuster la vitesse du monstre

        // Ajout de l'aléatoire à l'angle de déplacement
        Random random = new Random();
        double randomAngle = random.nextDouble() * Math.PI - Math.PI / 2; // Angle aléatoire entre -pi/8 et pi/8
        double finalAngle = angleToPlayer + randomAngle;

        double newX = monstreCenterX + Math.cos(finalAngle) * speed;
        double newY = monstreCenterY + Math.sin(finalAngle) * speed;

        // Mettre à jour les coordonnées de la hitbox du monstre
        monstre.getHitbox().setLayoutX(newX - hMonstre.getPrefWidth() / 2);
        monstre.getHitbox().setLayoutY(newY - hMonstre.getPrefHeight() / 2);
    }
}
```
</pre>

Nous pouvons imaginer d'ajouter d'autres design paterns, des décorateurs pour les différents types d'armes et d'ennemis et des observateurs pour surveiller différents évènements comme la montée de niveau du joueur par exemple.

---
# Partie pédagogique


### En quoi la POO vous a été utile

[Par exemple, expliquez que vous auriez éprouvé des difficultés à gérer les collisions si vous n'aviez pas utilisé la POO, ou que vous avez pu facilement ajouter des fonctionnalités à votre jeu grâce à la POO
Minimum 10 lignes (personnalisé en fonction de votre projet)]

La POO nous a permis de mettre en place des nouvelles méthodes permettant d'avoir un code plus propre et efficace. La POO nous a donc permis d'organiser notre code et de le rendre modulable afin que celui-ci puisse évoluer et être reppris par quelqu'un d'autre par exemple. Cela nous a apporté un confort de développement qui a permis au projet d'évoluer plus vite. En effet la gestion des objets était plus simple grâce au modèle, leurs interactions plus claires grâce au contrôleur et l'affichage plus organisé grâce aux vues. Au travers des design patterns, notament la factory où l'on a pu générer facilement des hordes de monstres évolutives, nous avons pu découvrir de nouvelles façons d'optimiser le code pour le rendre encore plus lisible et évolutif. Cette méthode a également permis de meilleures interactions entre les objets via des méthodes communes permises par l'héritage comme le déplacement des ennemis (interface, stratégies...). 

### Conclusion

[Décrivez ici si vous avez compris un concept particulier que vous n'aviez pas compris en cours, inversement si vous pensiez qu'il était possible de faire qqchose mais que cela ne s'est pas passé comme prévu]

Nous avons obtenu une meilleure compréhension des design patterns que nous avons utilisés. La pratique nous as permis de bien comprendre l'application de ces derniers, la théorie était comprise mais leur utilisation et leur implémentation dans des cas réels étaient parfois un peu flou mais désormais cela nous apparaît bien plus clair.
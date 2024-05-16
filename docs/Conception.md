
Editez de fichier de conception au format Markdown 

Vous intégrez des diagrammes UML ainsi :

``` 

```plantuml 
@startuml
'https://plantuml.com/class-diagram

interface IDeplacement {
    +seDeplacer()
}

class SMarcher implements IDeplacement{
    +seDeplacer()
}

class SCourir implements IDeplacement{
    +seDeplacer()
}

class SBoiter implements IDeplacement{
    +seDeplacer()
}

enum Stats {
    Constitution
    Attaque
    Defense
    Vitesse
}

abstract class Personnage implements IDeplacement {
    -nom: String
    -pv: Int
    -pvMax: Int
    -stats: Map<Stats, Int>
    +seDeplacer()
}

class Joueur extends Personnage {
    -inventaire: Inventaire
    -xp: Int
    -niveau: Int
    +attaquer()
}

class Monstre extends Personnage



abstract class Objets

class Equipement extends Objets

class Arme extends Equipement

class Armure extends Equipement

class Consommable extends Objets

class Inventaire {
    -listeObjets: List<Objets>
    +ajouterObjet()
    +supprimerObjet()
}

@enduml
```

```

Attendus : 

- Un diagramme de classe
- Un diagramme de cas d'utilisation
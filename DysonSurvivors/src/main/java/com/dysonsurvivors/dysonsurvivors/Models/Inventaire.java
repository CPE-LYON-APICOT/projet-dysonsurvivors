package com.dysonsurvivors.dysonsurvivors.Models;

public class Inventaire {
    private Objet objets[];

    public Inventaire(){
        objets = new Objet[10];
    }

    public void ajouterObjet(Objet objet){
        for(int i = 0; i < objets.length; i++){
            if(objets[i] == null){
                objets[i] = objet;
                break;
            }
        }
    }

    public void retirerObjet(Objet objet){
        for(int i = 0; i < objets.length; i++){
            if(objets[i] == objet){
                objets[i] = null;
                break;
            }
        }
    }
}

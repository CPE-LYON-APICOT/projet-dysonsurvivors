package com.dysonsurvivors.dysonsurvivors.Models.Inventaire;

import com.dysonsurvivors.dysonsurvivors.Models.Objet;

public class Inventaire {
    private Objet objets[];

    public Inventaire(){
        objets = new Objet[10];
    }

    public void ajouterObjet(Objet objet){
        for(int i = 0; i < objets.length; i++){
            if(objets[i] == null){
                objets[i] = objet;
                return;
            }
        }
    }

    public void retirerObjet(Objet objet){
        for(int i = 0; i < objets.length; i++){
            if(objets[i] == objet){
                objets[i] = null;
                return;
            }
        }
    }

    public Objet[] getObjets(){
        return objets;
    }
}

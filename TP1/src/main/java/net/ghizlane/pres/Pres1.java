package net.ghizlane.pres;

import net.ghizlane.dao.DaoImp;
import net.ghizlane.metier.MetierImp;

public class Pres1 {
    public static void main(String[] args) {
        DaoImp d = new DaoImp();
        MetierImp metier=new MetierImp(d); // injection via le constructeur
        //metier.setDao(d); // Injection des dépendances via le setter
        System.out.println("Resultat = "+ metier.calcul());
    }
}

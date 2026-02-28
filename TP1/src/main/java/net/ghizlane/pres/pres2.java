package net.ghizlane.pres;

import net.ghizlane.dao.IDao;
import net.ghizlane.metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Scanner;

public class pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Scanner scanner = new Scanner(new File("config.txt"));
        String daoClassName = scanner.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao d= (IDao) cDao.newInstance();

        String metierClassName = scanner.nextLine();
        Class cmetier = Class.forName(metierClassName);
        IMetier metier= (IMetier)cmetier.getConstructor(IDao.class).newInstance(d);
        System.out.println("Resultat = " + metier.calcul());
    }
}

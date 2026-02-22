package net.ghizlane.metier;

import net.ghizlane.dao.IDao;

public class MetierImp implements  IMetier{
    private IDao dao;
    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 12 * Math.PI * Math.cos(t);
        return res;
    }
}

package net.ghizlane.ext;

import net.ghizlane.dao.IDao;

public class DaoImpV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version capteurs .....");
        double t = 12;
        return t;
    }
}

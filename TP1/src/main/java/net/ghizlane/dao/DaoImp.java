package net.ghizlane.dao;

import org.springframework.stereotype.Component;

@Component("d")
public class DaoImp implements IDao{
    @Override
    public double getData() {
        System.out.println("Version base de données ");
        double t = 34;
        return t;
    }
}

package net.ghizlane.metier;

import net.ghizlane.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImp implements  IMetier{
    private IDao dao;

    public MetierImp(@Qualifier("d2") IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 12 * Math.PI * Math.cos(t);
        return res;
    }
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}

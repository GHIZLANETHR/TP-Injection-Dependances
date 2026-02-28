package net.ghizlane.pres;

import net.ghizlane.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext applicationcontext =
                new AnnotationConfigApplicationContext("net.ghizlane");
        IMetier metier = applicationcontext.getBean(IMetier.class);
        System.out.println("Resultat = " + metier.calcul());
    }
}

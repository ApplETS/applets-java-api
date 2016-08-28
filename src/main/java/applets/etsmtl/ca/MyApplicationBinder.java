package applets.etsmtl.ca;

import applets.etsmtl.ca.news.db.NouvellesDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Cette classe sert à définir le bon mapping pour l'injection de dépendances
 * Les cibles de l'annotation @Inject doivent donc être référencées ici
 */
public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(NouvellesDAO.class).to(NouvellesDAO.class);
        bind(SourceDAO.class).to(SourceDAO.class);

    }
}
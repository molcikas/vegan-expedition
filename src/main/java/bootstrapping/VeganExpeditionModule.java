package bootstrapping;

import applicationservices.RecipeAppService;
import applicationservices.RecipeAppServiceImpl;
import applicationservices.UnitOfWorkFactory;
import applicationservices.UnitOfWorkFactoryImpl;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceBuilder;
import org.apache.tapestry5.ioc.ServiceResources;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VeganExpeditionModule
{
    private static final String persistenceUnitName = "veganexp";

    public static void bind(ServiceBinder binder)
    {
        // TODO: Tapestry should do this automatically be isn't for some reason? See https://tapestry.apache.org/integrating-with-jpa.html.
        binder.bind(EntityManagerFactory.class, new ServiceBuilder<EntityManagerFactory>()
        {
            @Override
            public EntityManagerFactory buildService(ServiceResources resources)
            {
                return Persistence.createEntityManagerFactory(persistenceUnitName);
            }
        });

        binder.bind(UnitOfWorkFactory.class, UnitOfWorkFactoryImpl.class).eagerLoad();
        binder.bind(RecipeAppService.class, RecipeAppServiceImpl.class);
    }
}

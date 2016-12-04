package bootstrapping;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class VeganExpeditionContextInit implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        RegistryContainer.initRegistry();

        // TODO: Wire tapestry registry into jersey.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {

    }
}

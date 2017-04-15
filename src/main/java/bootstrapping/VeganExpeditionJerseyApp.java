package bootstrapping;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.apache.tapestry5.ioc.Registry;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import webapi.RecipeResource;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class VeganExpeditionJerseyApp extends ResourceConfig
{
    public VeganExpeditionJerseyApp()
    {
        initLogging();

        RegistryContainer.initRegistry();
        Registry registry = RegistryContainer.getRegistry();

        //DomainEventsRegistration.registerEventHandler();

        this.registerInstances(registry.getService(RecipeResource.class));
    }

    private static void initLogging()
    {
        try
        {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            SLF4JBridgeHandler.removeHandlersForRootLogger();
            SLF4JBridgeHandler.install();
            StatusPrinter.print(loggerContext);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}

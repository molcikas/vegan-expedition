package bootstrapping;

import org.apache.tapestry5.ioc.Registry;
import org.glassfish.jersey.server.ResourceConfig;
import webapi.RecipeResource;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class VeganExpeditionJerseyApp extends ResourceConfig
{
    public VeganExpeditionJerseyApp()
    {
        RegistryContainer.initRegistry();
        Registry registry = RegistryContainer.getRegistry();

        //DomainEventsRegistration.registerEventHandler();

        this.registerInstances(registry.getService(RecipeResource.class));
    }
}

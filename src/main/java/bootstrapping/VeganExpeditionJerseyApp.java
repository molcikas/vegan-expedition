package bootstrapping;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class VeganExpeditionJerseyApp extends ResourceConfig
{
    public VeganExpeditionJerseyApp()
    {
        packages("webapi");
    }
}

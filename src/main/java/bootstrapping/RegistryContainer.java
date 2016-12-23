package bootstrapping;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;

public class RegistryContainer
{
    private static Registry registry = null;

    public static Registry getRegistry()
    {
        return registry;
    }

    public static synchronized void initRegistry()
    {
        if(registry != null)
        {
            return;
        }
        RegistryBuilder builder = new RegistryBuilder();
        builder.add(VeganExpeditionModule.class);
        registry = builder.build();
        registry.performRegistryStartup();
        registry.cleanupThread();
    }
}

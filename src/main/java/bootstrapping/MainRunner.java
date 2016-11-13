package bootstrapping;

import applicationservices.RecipeAppService;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class MainRunner
{
    public static void main(String[] args) throws Exception
    {
        RegistryBuilder builder = new RegistryBuilder();

        builder.add(VeganExpeditionModule.class);

        Registry registry = builder.build();

        registry.performRegistryStartup();

        RecipeAppService recipeAppService = registry.getService(RecipeAppService.class);
        recipeAppService.test();

//        DomainEvents.registerEventHandler();

        /*ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("webapi");
        resourceConfig.register(MultiPartFeature.class);

        ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(servletHolder, "/*");

        ResourceHandler staticResourceHandler = new ResourceHandler();
        staticResourceHandler.setResourceBase("./web/");
        ContextHandler staticContextHandler = new ContextHandler("/");
        staticContextHandler.setHandler(staticResourceHandler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]
            {
                staticContextHandler,
                servletContextHandler
            });

        Server server = new Server(38765);
        server.setHandler(handlers);

        try
        {
            server.start();
            server.join();
        }
        finally
        {
            server.destroy();
        }*/
    }
}
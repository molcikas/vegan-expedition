package bootstrapping;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class MainRunner
{


    public static void main(String[] args) throws Exception
    {
        // TODO: Is it possible to have jetty invoke VeganExpeditionContextInit?
        RegistryContainer.initRegistry();

        //DomainEventsRegistration.registerEventHandler();

        ServletHolder servletHolder = new ServletHolder(new ServletContainer(new VeganExpeditionJerseyApp()));
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(servletHolder, "/api/*");

        ResourceHandler staticResourceHandler = new ResourceHandler();
        staticResourceHandler.setResourceBase("./src/main/webapp");
        ContextHandler staticContextHandler = new ContextHandler("/");
        staticResourceHandler.setMinMemoryMappedContentLength(-1);
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
        }
    }
}

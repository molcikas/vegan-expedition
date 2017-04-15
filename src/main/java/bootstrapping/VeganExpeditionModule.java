package bootstrapping;

import aggregates.recipe.Recipe;
import aggregates.recipe.RecipeIngredient;
import aggregates.recipe.RecipeInstruction;
import applicationservices.RecipeAppService;
import applicationservices.RecipeAppServiceImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ddd.domainevents.DomainEventsRegistration;
import ddd.domainevents.DomainEventsRegistrationImpl;
import ddd.persistence.UnitOfWorkFactory;
import applicationservices.UnitOfWorkFactoryImpl;
import org.apache.commons.lang3.math.Fraction;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Symbol;
import photon.Photon;
import webapi.RecipeResource;
import webapi.RecipeResourceImpl;

import java.sql.Types;

public class VeganExpeditionModule
{
    public static final class Symbols
    {
        public static final String DATABASE_URL = "veganexpedition.database-url";
        public static final String DATABASE_USER = "veganexpedition.database-user";
        public static final String DATABASE_PASSWORD = "veganexpedition.database-password";
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration)
    {
        configuration.add(Symbols.DATABASE_URL, "jdbc:mysql://localhost:3306/veganexp?useSSL=false");
        configuration.add(Symbols.DATABASE_USER, "root");
        configuration.add(Symbols.DATABASE_PASSWORD, "bears");
    }

    public static void bind(ServiceBinder binder)
    {
        binder.bind(DomainEventsRegistration.class, DomainEventsRegistrationImpl.class);
        binder.bind(UnitOfWorkFactory.class, UnitOfWorkFactoryImpl.class);
        binder.bind(RecipeAppService.class, RecipeAppServiceImpl.class);
        binder.bind(RecipeResource.class, RecipeResourceImpl.class);
    }

    public static Photon buildPhoton(
        @Symbol(Symbols.DATABASE_URL) String databaseUrl,
        @Symbol(Symbols.DATABASE_USER) String databaseUser,
        @Symbol(Symbols.DATABASE_PASSWORD) String databasePassword)
    {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUser);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        Photon photon = new Photon(new HikariDataSource(hikariConfig));

        photon.registerAggregate(Recipe.class)
            .withId("recipeId")
            .withOrderBy("name")
            .withChild(RecipeInstruction.class)
                .withId("recipeInstructionId")
                .withColumnDataType("recipeInstructionId", Types.BINARY)
                .withForeignKeyToParent("recipeId")
                .withColumnDataType("recipeId", Types.BINARY)
                .withOrderBy("stepNumber")
                .addAsChild("instructions")
            .withChild(RecipeIngredient.class)
                .withId("recipeIngredientId")
                .withColumnDataType("recipeIngredientId", Types.BINARY)
                .withForeignKeyToParent("recipeId")
                .withColumnDataType("recipeId", Types.BINARY)
                .withColumnDataType("quantity", Types.VARCHAR)
                .withCustomToFieldValueConverter("quantity", val -> val != null ? Fraction.getFraction((String) val) : null)
                .addAsChild("ingredients")
            .register();

        return photon;
    }
}

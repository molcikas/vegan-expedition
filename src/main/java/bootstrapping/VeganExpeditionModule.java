package bootstrapping;

import aggregates.recipe.Recipe;
import aggregates.recipe.RecipeIngredient;
import aggregates.recipe.RecipeInstruction;
import applicationservices.RecipeAppService;
import applicationservices.RecipeAppServiceImpl;
import com.github.molcikas.photon.blueprints.table.ColumnDataType;
import com.github.molcikas.photon.options.DefaultTableName;
import com.github.molcikas.photon.options.PhotonOptions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ddd.domainevents.DomainEventsRegistration;
import ddd.domainevents.DomainEventsRegistrationImpl;
import ddd.persistence.UnitOfWorkFactory;
import applicationservices.UnitOfWorkFactoryImpl;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.Fraction;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Symbol;
import com.github.molcikas.photon.Photon;
import webapi.RecipeResource;
import webapi.RecipeResourceImpl;

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

    @SneakyThrows
    @EagerLoad
    public static Photon buildPhoton(
        @Symbol(Symbols.DATABASE_URL) String databaseUrl,
        @Symbol(Symbols.DATABASE_USER) String databaseUser,
        @Symbol(Symbols.DATABASE_PASSWORD) String databasePassword)
    {
        Class.forName("com.mysql.jdbc.Driver");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUser);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        PhotonOptions photonOptions = new PhotonOptions(
            null,
            null,
            DefaultTableName.ClassNameLowerCase,
            null,
            null);

        Photon photon = new Photon(new HikariDataSource(hikariConfig), photonOptions);

        photon.registerAggregate(Recipe.class)
            .withId("recipeId")
            .withOrderBySql("recipe.name")
            .withChild("instructions", RecipeInstruction.class)
                .withId("recipeInstructionId", ColumnDataType.BINARY)
                .withForeignKeyToParent("recipeId", ColumnDataType.BINARY)
                .withOrderBySql("recipeinstruction.stepNumber")
                .addAsChild()
            .withChild("ingredients", RecipeIngredient.class)
                .withId("recipeIngredientId", ColumnDataType.BINARY)
                .withForeignKeyToParent("recipeId")
                .withDatabaseColumn("recipeId", ColumnDataType.BINARY)
                .withDatabaseColumn("quantity", ColumnDataType.VARCHAR)
                .withFieldHydrater("quantity", val -> val != null ? Fraction.getFraction((String) val) : null)
                .withOrderBySql("recipeingredient.orderBy")
                .addAsChild()
            .register();

        return photon;
    }
}

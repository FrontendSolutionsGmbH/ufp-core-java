package com.froso.ufp.core.configuration;

import com.froso.ufp.core.converter.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import com.github.mongobee.*;
import com.mongodb.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.*;
import org.springframework.data.mongodb.config.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.mongodb.repository.config.*;

//import com.github.mongobee.*;

/**
 * The type Mongo config.
 */
@Configuration

@EnableMongoRepositories()
public class MongoConfig extends AbstractMongoConfiguration {
    /**
     * The constant PROP_NAME_UFP_CORE_MONGO_PORT.
     */
    public static final String PROP_NAME_UFP_CORE_MONGO_PORT = "ufp.core.mongo.port";
    /**
     * The constant PROP_NAME_UFP_CORE_MONGO_DATABASE.
     */
    public static final String PROP_NAME_UFP_CORE_MONGO_DATABASE = "ufp.core.mongo.database";
    /**
     * The constant PROP_NAME_UFP_CORE_MONGO_HOST.
     */
    public static final String PROP_NAME_UFP_CORE_MONGO_HOST = "ufp.core.mongo.host";
    /**
     * The constant PROP_NAME_UFP_CORE_MONGO_USERNAME.
     */
    public static final String PROP_NAME_UFP_CORE_MONGO_USERNAME = "ufp.core.mongo.username";
    /**
     * The constant PROP_NAME_UFP_CORE_MONGO_PASSWORD.
     */
    public static final String PROP_NAME_UFP_CORE_MONGO_PASSWORD = "ufp.core.mongo.password";
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoConfig.class);
    @Value("${" + PROP_NAME_UFP_CORE_MONGO_PORT + "}")
    private Integer mongoPort;
    @Value("${" + PROP_NAME_UFP_CORE_MONGO_DATABASE + "}")
    private String mongoDatabasename;
    @Value("${" + PROP_NAME_UFP_CORE_MONGO_HOST + "}")
    private String mongoHostname;
    @Value("${" + PROP_NAME_UFP_CORE_MONGO_USERNAME + "}")
    private String mongoUsername;
    @Value("${" + PROP_NAME_UFP_CORE_MONGO_PASSWORD + "}")
    private String mongoPasswort;

    public String getMigrationPackageName() {
        return "com.froso.ufp.demo.modules.mist.migration";
    }

    @Bean
    public Mongobee mongobee(MongoTemplate template) throws Exception {
        Mongobee mongobee = new Mongobee("mongodb://" + mongoUsername + ":" + mongoPasswort + "@" + mongoHostname + ":" + mongoPort + "/" + mongoDatabasename);
        mongobee.setChangeLogsScanPackage(getMigrationPackageName());
        // This next line fixes the missing constructor problem
        mongobee.setMongoTemplate(template);
        try {
            LOGGER.info("Executing DB  Migration {}", getMigrationPackageName());
            mongobee.execute();
        } catch (Exception ex) {
            LOGGER.error("Mongo Migration error, exiting application", ex);
            throw ex;
        }
        return mongobee;
    }

    /**
     * Sets mongo port.
     *
     * @param mongoPort the mongo port
     */
    public void setMongoPort(Integer mongoPort) {

        this.mongoPort = mongoPort;
    }

    /**
     * Gets database workflowName.
     *
     * @return the database workflowName
     */
    @Override
    protected String getDatabaseName() {

        return mongoDatabasename;
    }


    @Override
    @Bean
    public MongoClient mongoClient() {
        try {
            MongoClientOptions options = MongoClientOptions.builder()
                    .writeConcern(WriteConcern.ACKNOWLEDGED)
                    .addConnectionPoolListener(new MongoConnectionPoolListener())
                    .build();

            MongoClient mongoClient = new MongoClient(Collections.singletonList(new ServerAddress(mongoHostname, mongoPort)),
                    MongoCredential.createCredential(mongoUsername, mongoDatabasename, mongoPasswort.toCharArray()), options);
            // check connection
            mongoClient.getAddress();
            return mongoClient;
        } catch (Exception e) {

            LOGGER.error("Mongo Connection error, exiting", e);
            System.exit(1);
            return null;
        }
    }

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new MyAtomicIntegerToInteger());
        converters.add(new IntegerToMyAtomicInteger());

        converters.add(new JodaDateTimeFromStringConverter());
        converters.add(new JodaDateTimeToStringConverter());
        converters.add(new JodaLocalTimeFromStringConverter());
        converters.add(new JodaLocalTimeToStringConverter());
        /*converters.add(new MyLocalDateFromStringConverter());
        converters.add(new MyLocalDateToStringConverter());         */

        converters.add(new LocalDateReadConverter());
        converters.add(new LocalDateWriteConverter());
        return new CustomConversions(converters);
    }

    /**
     * Mongo converter mapping mongo converter.
     *
     * @return the mapping mongo converter
     */
    @Bean
    public MappingMongoConverter mongoConverter() {
        MongoMappingContext mappingContext = new MongoMappingContext();

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        mongoConverter.setCustomConversions(customConversions());
        return mongoConverter;
    }

    /**
     * Mongo mongo.
     *
     * @return the mongo
     * @throws java.net.UnknownHostException the unknown host exception
     */
    /* @Bean
    @Override
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        MongoClientOptions options = MongoClientOptions.builder().build();


        LOGGER.info(" Connecting to Mongo DB ");
        LOGGER.info(" Connecting to Mongo DB database " + this.getDatabaseName());
        LOGGER.info(" Connecting to Mongo DB host: " + mongoHostname);
        LOGGER.info(" Connecting to Mongo DB nonce: " + mongoPasswort);
        LOGGER.info(" Connecting to Mongo DB username: " + mongoUsername);
        LOGGER.info(" Connecting to Mongo DB username: " + mongoPort);


        MongoCredential mongoCredential = MongoCredential.createCredential(this.mongoUsername, this.mongoDatabasename, this.mongoPasswort.toCharArray());
        ServerAddress serverAddress = new ServerAddress(this.mongoHostname, this.mongoPort);
        MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(mongoCredential));
        // Set the write Concern HERE !
        mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        // Mongo DB Factory
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
                mongoClient, mongoDatabasename);

        return simpleMongoDbFactory;
    }
    */
    /*
    @Bean
    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
    */
/**
 * Gets user credentials.
 *
 * @return the user credentials
 */
/*
    @Override
    protected UserCredentials getUserCredentials() {

        return new UserCredentials(mongoUsername, mongoPasswort);
    }*/
}

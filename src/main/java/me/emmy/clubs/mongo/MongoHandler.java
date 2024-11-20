package me.emmy.clubs.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

/**
 * @author hieu
 * @since 05/10/2023
 */

@Getter
public class MongoHandler {
    private final MongoClient client;
    private final MongoDatabase database;

    /**
     * Constructor for the MongoHandler class.
     *
     * @param uri True if the connection string is a URI, false otherwise
     * @param connectionString The connection string
     * @param host The host of the MongoDB server
     * @param port The port of the MongoDB server
     * @param database The database to connect to
     * @param authentication True if the connection requires authentication, false otherwise
     * @param username The username for the connection
     * @param password The password for the connection
     */
    public MongoHandler(boolean uri, String connectionString, String host, int port, String database, boolean authentication, String username, String password){
        if (uri){
            this.client = new MongoClient(new MongoClientURI(connectionString));
        } else {
            if (authentication){
                this.client = new MongoClient(new ServerAddress(host, port), MongoCredential.createCredential(username, database, password.toCharArray()), MongoClientOptions.builder().build());
            } else{
                this.client = new MongoClient(host, port);
            }
        }
        this.database = this.client.getDatabase(database);
    }
}
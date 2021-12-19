package eco.bike.utils;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import eco.bike.entity.User;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MongoDB {

    // Replace the uri string with your MongoDB deployment's connection string

    public MongoDB() {
    }

    /**
     * Ping admin .\ /. make sure you got the correct tokens.
     */
    public void ping_admin() {
        try (MongoClient mongoClient = MongoClients.create( Configs.MONGODB_URI )) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    /**
     * Index a set of collection to MongoDB
     * @param databaseName: {@link Configs}
     * @param collectionNames: list of collections' name
     */
    public void init(String databaseName, List<String> collectionNames) {
        try (MongoClient mongoClient = MongoClients.create( Configs.MONGODB_URI )) {
            MongoDatabase database = mongoClient.getDatabase( databaseName );
            collectionNames.forEach(database::createCollection);
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    public Document findOneUser(String email, String password) {
        try (MongoClient mongoClient = MongoClients.create( Configs.MONGODB_URI )) {
            MongoDatabase db = mongoClient.getDatabase( Configs.DATABASE_NAME );
            MongoCollection<Document> document = db.getCollection("users");

            return  document.find(
                    Filters.and(
                            Filters.eq("email", email),
                            Filters.eq("password", password)
                    )
            ).first();
        }
    }

    public void insertOneUser(User user) {
        try (MongoClient mongoClient = MongoClients.create( Configs.MONGODB_URI )) {
            MongoDatabase database = mongoClient.getDatabase( Configs.DATABASE_NAME );
            MongoCollection<Document> collection = database.getCollection("users");
            try {
                InsertOneResult result = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("userId", user.getUserId())
                        .append("phoneNumber", user.getPhoneNumber())
                        .append("province", user.getProvince())
                        .append("address", user.getAddress())
                        .append("dateOfBirth", user.getDateOfBirth())
                        .append("email", user.getEmail())
                        .append("password", user.getPassword())
                        .append("bikeRentalIds", user.getBikeRentalIds()));
                System.out.println("Success! Inserted document id: " + result.getInsertedId());
            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
            }
        }
    }

    public void updateOneUser(User user) {
        try (MongoClient mongoClient = MongoClients.create( Configs.MONGODB_URI )) {
            MongoDatabase database = mongoClient.getDatabase( Configs.DATABASE_NAME );
            MongoCollection<Document> collection = database.getCollection("users");
            Document query = new Document().append("userId",  user.getUserId());
            Bson updates = Updates.combine(
                    Updates.set("bikeRentalIds", user.getBikeRentalIds()));
            UpdateOptions options = new UpdateOptions().upsert(true);
            try {
                UpdateResult result = collection.updateOne(query, updates, options);
                System.out.println("Modified document count: " + result.getModifiedCount());
                System.out.println("Upsert-ed id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
        }
    }

    /**
     * Import your json file to MongoDB (a document each line)
     * @param jsonFilePath: appended to ~/eco-bike
     */
    public void importJsonData(String jsonFilePath) {
        MongoClient client = MongoClients.create( Configs.MONGODB_URI );

        MongoDatabase database = client.getDatabase( Configs.DATABASE_NAME );
        MongoCollection<org.bson.Document> coll = database.getCollection( Configs.COLLECTION_NAME );

        try {

            //drop previous import
            coll.drop();

            //Bulk Approach:
            int count = 0;
            int batch = 100;
            List<InsertOneModel<Document>> docs = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + jsonFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    docs.add(new InsertOneModel<>(Document.parse(line)));
                    count++;
                    if (count == batch) {
                        coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
                        docs.clear();
                        count = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (count > 0) {
                BulkWriteResult bulkWriteResult=  coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
                System.out.println("Inserted" + bulkWriteResult);
            }

        } catch (MongoWriteException e) {
            System.out.println("Error");
        }

    }
}

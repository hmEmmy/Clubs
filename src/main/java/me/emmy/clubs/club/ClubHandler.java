package me.emmy.clubs.club;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.emmy.clubs.Clubs;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * @author hieu
 * @since 21/10/2023
 */

@Getter
public class ClubHandler {
    private final MongoCollection<Document> collection;

    /**
     * Constructor for the ClubHandler class
     */
    public ClubHandler() {
        this.collection = Clubs.getInstance().getMongoHandler().getDatabase().getCollection("Clubs");
    }

    /**
     * Get the club by its lowercase name
     *
     * @param lowercaseName The lowercase name of the club
     * @return The club with the lowercase name
     */
    public Club getClubByLowercaseName(String lowercaseName){
        Bson filter = Filters.eq("lowercaseName", lowercaseName);
        Document document = this.collection.find(filter).first();
        if (document == null) return null;
        return new Club(document.getString("name"), document.getString("lowercaseName"));
    }

    /**
     * Delete the club by its lowercase name
     *
     * @param lowercaseName The lowercase name of the club
     */
    public void deleteClubByLowercaseName(String lowercaseName){
        Bson filter = Filters.eq("lowercaseName", lowercaseName);
        collection.findOneAndDelete(filter);
    }
}
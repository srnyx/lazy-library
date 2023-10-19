package xyz.srnyx.lazylibrary;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;

import org.bson.conversions.Bson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * A wrapper for {@link MongoCollection} with some useful shortcuts/methods
 *
 * @param   <T> the type of the collection
 */
public class LazyCollection<T> {
    /**
     * The {@link MongoCollection} instance
     */
    @NotNull public final MongoCollection<T> collection;

    /**
     * Constructs a new {@link LazyCollection} instance
     *
     * @param   database    the {@link MongoDatabase} instance
     * @param   name        the name of the collection
     * @param   clazz       the class of the collection
     */
    public LazyCollection(@NotNull MongoDatabase database, @NotNull String name, @NotNull Class<T> clazz) {
        collection = database.getCollection(name, clazz);
    }

    /**
     * Finds one document in the collection
     *
     * @param   filter  the filter to apply
     *
     * @return          the document found, or null if none was found
     */
    @Nullable
    public T findOne(@NotNull Bson filter) {
        return collection.find(filter).first();
    }

    /**
     * Finds one document in the collection
     *
     * @param   field   the field to filter by
     * @param   value   the value of the field to filter by
     *
     * @return          the document found, or null if none was found
     */
    @Nullable
    public T findOne(@NotNull String field, @Nullable Object value) {
        return collection.find(Filters.eq(field, value)).first();
    }

    /**
     * Finds multiple documents in the collection
     *
     * @param   filter  the filter to apply
     *
     * @return          the documents found
     */
    @NotNull
    public List<T> findMany(@NotNull Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }

    /**
     * Updates a document in the collection
     *
     * @param   filter  the filter to apply
     * @param   update  the update to apply
     */
    public void updateOne(@NotNull Bson filter, @NotNull Bson update) {
        collection.updateOne(filter, update);
    }

    /**
     * Upserts a document in the collection
     *
     * @param   filter  the filter to apply
     * @param   update  the update to apply
     */
    public void upsertOne(@NotNull Bson filter, @NotNull Bson update) {
        collection.updateOne(filter, update, new UpdateOptions().upsert(true));
    }

    /**
     * Updates a document in the collection and returns the updated document
     *
     * @param   filter  the filter to apply
     * @param   update  the update to apply
     *
     * @return          the updated document
     */
    @Nullable
    public T findOneAndUpdate(@NotNull Bson filter, @NotNull Bson update) {
        return collection.findOneAndUpdate(filter, update, new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER));
    }

    /**
     * Updates a document in the collection (or inserts it if it doesn't exist) and returns the updated document
     *
     * @param   filter  the filter to apply
     * @param   update  the update to apply
     *
     * @return          the updated or inserted document
     */
    @Nullable
    public T findOneAndUpsert(@NotNull Bson filter, @NotNull Bson update) {
        return collection.findOneAndUpdate(filter, update, new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER)
                .upsert(true));
    }

    /**
     * Deletes a document in the collection
     *
     * @param   filter  the filter to apply
     */
    public void deleteOne(@NotNull Bson filter) {
        collection.deleteOne(filter);
    }

    /**
     * Deletes a document in the collection
     *
     * @param   field   the field to filter by
     * @param   value   the value of the field to filter by
     */
    public void deleteOne(@NotNull String field, @Nullable Object value) {
        deleteOne(Filters.eq(field, value));
    }
}

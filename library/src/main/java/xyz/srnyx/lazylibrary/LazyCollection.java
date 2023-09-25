package xyz.srnyx.lazylibrary;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

import org.bson.conversions.Bson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class LazyCollection<T> {
    @NotNull public final MongoCollection<T> collection;

    public LazyCollection(@NotNull MongoDatabase database, @NotNull String name, @NotNull Class<T> clazz) {
        collection = database.getCollection(name, clazz);
    }

    @Nullable
    public T findOne(@NotNull Bson filter) {
        return collection.find(filter).first();
    }

    @Nullable
    public T findOne(@NotNull String field, @Nullable Object value) {
        return collection.find(Filters.eq(field, value)).first();
    }

    @NotNull
    public List<T> findMany(@NotNull Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }

    public void updateOne(@NotNull Bson filter, @NotNull Bson update) {
        collection.updateOne(filter, update);
    }

    @Nullable
    public T findOneAndUpdate(@NotNull Bson filter, @NotNull Bson update) {
        return collection.findOneAndUpdate(filter, update, getReturnAfter());
    }

    @Nullable
    public T findOneAndUpsert(@NotNull Bson filter, @NotNull Bson update) {
        return collection.findOneAndUpdate(filter, update, getUpsert());
    }

    public void deleteOne(@NotNull Bson filter) {
        collection.deleteOne(filter);
    }

    public void deleteOne(@NotNull String field, @Nullable Object value) {
        deleteOne(Filters.eq(field, value));
    }

    @NotNull
    private static FindOneAndUpdateOptions getReturnAfter() {
        return new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
    }

    @NotNull
    private static FindOneAndUpdateOptions getUpsert() {
        return getReturnAfter().upsert(true);
    }
}

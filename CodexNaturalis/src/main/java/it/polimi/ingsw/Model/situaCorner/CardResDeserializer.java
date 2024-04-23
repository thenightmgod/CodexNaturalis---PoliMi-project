package it.polimi.ingsw.Model.situaCorner;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * This class is a custom deserializer for Gson library, responsible for deserializing JSON elements
 * into instances of classes that implement the {@link CardRes} interface.
 */
public class CardResDeserializer implements JsonDeserializer<CardRes> {

    /**
     * Deserializes a JSON element into an instance of a class that implements the {@link CardRes} interface.
     *
     * @param json       The JSON element being deserialized.
     * @param typeOfT    The specific genericized type expected to be deserialized.
     * @param context    The context for deserialization that is passed to a custom deserializer during invocation of its methods.
     * @return           An instance of a class that implements the {@link CardRes} interface, deserialized from the provided JSON element.
     * @throws JsonParseException If there is an error while parsing the JSON element.
     */
    public CardRes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        return Stream.of(CornerState.values(), it.polimi.ingsw.Model.situaCorner.Objects.values(), Resources.values())
                .flatMap(Stream :: of)
                .filter(x ->x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }

}


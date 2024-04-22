package it.polimi.ingsw.Model.situaCorner;
import it.polimi.ingsw.Model.situaCorner.CardRes;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
public class CardResAdapter extends TypeAdapter<CardRes> {
    @Override
    public void write(JsonWriter out, CardRes value) throws IOException {
        // Implementazione non necessaria per la scrittura in JSON
    }

    @Override
    public CardRes read(JsonReader in) throws IOException {
        // Leggi il tipo e il valore dell'enum dal JSON
        String type = "";
        String value = "";

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "type":
                    type = in.nextString();
                    break;
                case "value":
                    value = in.nextString();
                    break;
                default:
                    in.skipValue(); // Ignora i campi non necessari
                    break;
            }
        }
        in.endObject();

        // Verifica il tipo dell'enum e restituisci l'istanza appropriata di CardRes
        switch (type) {
            case "CORNER_STATE":
                return CornerState.valueOf(value);
            case "OBJECTS":
                return Objects.valueOf(value);
            case "RESOURCES":
                return Resources.valueOf(value);
            // Aggiungi altri casi per eventuali altri tipi di enum
            default:
                throw new IOException("Unknown enum type: " + type);
        }
    }
}

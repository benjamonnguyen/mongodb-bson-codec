package me.benjinguyen.mongodb.bson.codec;

import org.bson.BsonDocument;
import org.bson.BsonDocumentReader;
import org.bson.BsonDocumentWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.IntegerCodec;
import org.bson.codecs.StringCodec;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("unchecked")
class MapCodecTest {

    Map<Integer, String> map = new HashMap<>();

    MapCodec<Integer, String> sut = new MapCodec<>((Class<Map<Integer, String>>) map.getClass(),
            new IntegerCodec(), new StringCodec());

    Map<Integer, String> integerToString = Map.of(0, "foo", 99999, "bar", -1, "baz");

    @Test
    void encode_decode() {
        BsonDocument document = new BsonDocument();
        sut.encode(new BsonDocumentWriter(document),
                integerToString,
                EncoderContext.builder().build());
        var res = sut.decode(new BsonDocumentReader(document),
                DecoderContext.builder().build());

        assertEquals(integerToString, res);
    }

}
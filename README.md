MapCodec to fix mongoDB driver issue `Maps MUST have string keys, found class X instead`

Based on [Yuna Braska's solution](https://stackoverflow.com/a/67849755) with additional handling of codecs that do not encode/decode BsonString by using PropertyEditors

### Usage:

```java
import me.benjinguyen.mongodb.bson.codec.MapCodec;

public class MapCodecProvider implements PropertyCodecProvider {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> Codec<T> get(final TypeWithTypeParameters<T> type, final PropertyCodecRegistry registry) {
        if (Map.class.isAssignableFrom(type.getType()) && type.getTypeParameters().size() == 2) {
            return new MapCodec(
                    type.getType(),
                    registry.get(type.getTypeParameters().get(0)),
                    registry.get(type.getTypeParameters().get(1)));
        }
        return null;
    }
}
```

A 4th constructor argument can be provided to register additional PropertyEditors

```java
import me.benjinguyen.mongodb.bson.codec.MapCodec;

import java.util.Collections;

public class MapCodecProvider implements PropertyCodecProvider {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> Codec<T> get(final TypeWithTypeParameters<T> type, final PropertyCodecRegistry registry) {
        if (Map.class.isAssignableFrom(type.getType()) && type.getTypeParameters().size() == 2) {
            return new MapCodec(
                    type.getType(),
                    registry.get(type.getTypeParameters().get(0)),
                    registry.get(type.getTypeParameters().get(1)),
                    Collections.singletonMap(Foo.class, FooEditor.class));
        }
        return null;
    }
}
```

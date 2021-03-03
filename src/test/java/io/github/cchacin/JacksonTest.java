package io.github.cchacin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class JacksonTest implements WithAssertions {

    record Person(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            String address,
            Date birthday,
            List<String> achievements
    ) {
    }

    static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    void serializeRecord() throws Exception {
        // Given
        var person = new Person(
                "John",
                "Doe",
                "USA",
                new Date(981291289182L),
                List.of("Speaker")
        );

        var json = """
                {
                  "first_name" : "John",
                  "last_name" : "Doe",
                  "address" : "USA",
                  "birthday" : 981291289182,
                  "achievements" : [ "Speaker" ]
                }""";

        // When
        var serialized = mapper.writeValueAsString(person);

        // Then
        assertThat(serialized).isEqualTo(json);
    }

    @Test
    void deserializeRecord() throws Exception {
        // Given
        var person = new Person(
                "John",
                "Doe",
                "USA",
                new Date(981291289182L),
                List.of("Speaker")
        );

        var json = """
                {
                  "first_name" : "John",
                  "last_name" : "Doe",
                  "address" : "USA",
                  "birthday" : 981291289182,
                  "achievements" : [ "Speaker" ]
                }""";

        // When
        var deserialized = mapper.readValue(json, Person.class);

        // Then
        assertThat(deserialized).isEqualTo(person);
    }
}

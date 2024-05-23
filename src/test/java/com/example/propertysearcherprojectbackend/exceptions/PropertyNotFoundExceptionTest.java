package com.example.propertysearcherprojectbackend.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PropertyNotFoundExceptionTest {

    @Test
    public void shouldThrowPropertyNotFoundException() {
        //Given
        String expectedMessage = "Property with id %s not found";

        //When & Then
        assertThatThrownBy(() -> {
            throw new PropertyNotFoundException(expectedMessage);
        }).isInstanceOf(PropertyNotFoundException.class)
                .hasMessage(expectedMessage);
    }
}
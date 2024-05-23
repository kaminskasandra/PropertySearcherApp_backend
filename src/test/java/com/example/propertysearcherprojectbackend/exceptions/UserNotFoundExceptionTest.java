package com.example.propertysearcherprojectbackend.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserNotFoundExceptionTest {

    @Test
    public void shouldThrowUserNotFoundException() {
        //Given
        String expectedMessage = "User with id %s not found";

        //When & Then
        assertThatThrownBy(() -> {
            throw new UserNotFoundException(expectedMessage);
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessage(expectedMessage);
    }
}


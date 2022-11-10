package com.sa22.LMASpringData.repositories;

import com.sa22.LMASpringData.entities.AuthorEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AuthorRepositoryTest {


    private AuthorRepository underTest;

    public AuthorRepositoryTest(AuthorRepository underTest) {
        this.underTest = underTest;
    }


    @Test
    public void testThatOptionalHasValue() {
        String name = "John";
        AuthorEntity author = new AuthorEntity(name);

        underTest.save(author);

      Optional<AuthorEntity> expected = underTest.findByAuthorName(name);

      assertThat(expected).isNotNull();


    }
}
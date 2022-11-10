package com.sa22.LibraryManagementSpring.author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class AuthorRepository implements AuthorDAO{

    private static final Logger log = LoggerFactory.getLogger(AuthorRepository.class);

    RowMapper<Author> rowMapper = (rs, rowNum) -> {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setName(rs.getString("name"));
        return author;
    };
    private JdbcTemplate jdbcTemplate;

    private AuthorRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> list() {
        String sql = "SELECT id, name FROM author;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Object o) {

    }

    @Override
    public Optional get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Object o, int id) {

    }

    @Override
    public void delete(int id) {

    }
}

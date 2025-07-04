package com.j797.edu_system.repository;

import com.j797.edu_system.model.Teacher;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
//@NoArgsConstructor
public class TeacherRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Teacher> mapper = (resultSet, rowNum) ->
            Teacher.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .build();

    public List<Teacher> findAll() {
        return jdbcTemplate.query("SELECT * FROM teacher ORDER BY name", mapper);
//        jdbcTemplate.query 로 rows, rowNum 반환 -> RowMapper가 받아서 Teacher에 매핑
    }

    public Teacher findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM teacher WHERE id=?", mapper, id);
        //        하나 찾을때는 queryForObject 쓰고 바인딩은 세번재 arg부터
    }

    public int save(Teacher teacher) {
        return jdbcTemplate.update(
                "INSERT INTO teacher (name) VALUES (?)",
                teacher.getName()
        );
    }

    public int update(Teacher teacher) {
        return jdbcTemplate.update(
                "UPDATE teacher SET name = ? WHERE id = ?", teacher.getName(), teacher.getId()
                //                바인딩은 순서대로
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                "DELETE FROM teacher WHERE id = ?",
                id
        );
    }

}

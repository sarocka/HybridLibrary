package com.hybridit.HybridLibrary.dto;

import javax.persistence.Column;
import java.util.Objects;

public class AuthorDTO {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return id.equals(authorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

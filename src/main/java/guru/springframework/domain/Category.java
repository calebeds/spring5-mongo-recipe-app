package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Document
public class Category {
    @Id
    private String id = UUID.randomUUID().toString();
    private String description;
    private Set<Recipe> recipes;
}

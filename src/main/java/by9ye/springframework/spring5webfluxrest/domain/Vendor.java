package by9ye.springframework.spring5webfluxrest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    private String id;

    private String lastName;
    private String firstName;
}

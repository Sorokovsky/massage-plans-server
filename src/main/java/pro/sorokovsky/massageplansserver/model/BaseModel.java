package pro.sorokovsky.massageplansserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}

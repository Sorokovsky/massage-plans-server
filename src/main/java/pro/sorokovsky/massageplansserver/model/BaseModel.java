package pro.sorokovsky.massageplansserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}

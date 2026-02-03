package pro.sorokovsky.massageplansserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class UserModel extends BaseModel {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
}

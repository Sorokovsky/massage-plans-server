package pro.sorokovsky.massageplansserver.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserModel extends BaseModel {
    private String email;
    private String password;
    private String fistName;
    private String lastName;
    private String middleName;
}

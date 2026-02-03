package pro.sorokovsky.massageplansserver.mapper;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pro.sorokovsky.massageplansserver.contract.GetUserPayload;
import pro.sorokovsky.massageplansserver.entity.UserEntity;
import pro.sorokovsky.massageplansserver.model.Authorities;
import pro.sorokovsky.massageplansserver.model.UserModel;

import java.util.List;

@Component
public class UserMapper {
    public UserModel toModel(UserEntity entity) {
        return UserModel
                .builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .authorities(entity.getAuthorities() == null ? List.of(new SimpleGrantedAuthority(Authorities.USER)) : entity.getAuthorities())
                .build();
    }

    public UserEntity toEntity(UserModel model) {
        return UserEntity
                .builder()
                .id(model.getId())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .email(model.getEmail())
                .password(model.getPassword())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .middleName(model.getMiddleName())
                .authorities(model.getAuthorities() == null ? List.of(new SimpleGrantedAuthority(Authorities.USER)) : model.getAuthorities())
                .build();
    }

    public GetUserPayload toGet(UserModel model) {
        return new GetUserPayload(
                model.getId(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getEmail(),
                model.getFirstName(),
                model.getLastName(),
                model.getMiddleName()
        );
    }
}

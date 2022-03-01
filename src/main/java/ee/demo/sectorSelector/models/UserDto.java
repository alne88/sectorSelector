package ee.demo.sectorSelector.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {

    private Long id;

    @NonNull
    @Size(min=4, max=16)
    private String username;

    @NonNull
    @Size(min=4, max=64)
    private String password;

    @NonNull
    private UserRole role;

    @NonNull
    private Boolean termsAgreed;

    public enum UserRole {
        ROLE_ADMIN, ROLE_USER
    }
}

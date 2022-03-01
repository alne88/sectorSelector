package ee.demo.sectorSelector.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserCreateDto {

    @NonNull
    @Size(min=4, max=16)
    private String username;

    @NonNull
    @Size(min=4, max=64)
    private String password;

}

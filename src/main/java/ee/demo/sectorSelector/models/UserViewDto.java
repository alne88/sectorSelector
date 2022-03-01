package ee.demo.sectorSelector.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
public class UserViewDto {

    @NonNull
    @Valid
    private Long userId;

    @NonNull
    @Size(min=4, max=16)
    @Valid
    private String username;

    @AssertTrue(message = "Agreeing to terms is required")
    @Valid
    private boolean termsAgreed;

    @NonNull
    @Valid
    private List<String> sectors;

    public UserViewDto(Long userId, String username, Boolean termsAgreed, List<String> sectors) {
        this.userId = userId;
        this.username = username;
        this.termsAgreed = termsAgreed;
        this.sectors = sectors;
    }

}

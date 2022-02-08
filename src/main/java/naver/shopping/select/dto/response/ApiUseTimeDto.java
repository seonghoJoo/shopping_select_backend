package naver.shopping.select.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ApiUseTimeDto {
    private String username;
    private Long totalUseTime;
}

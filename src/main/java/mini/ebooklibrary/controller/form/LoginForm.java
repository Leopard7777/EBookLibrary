package mini.ebooklibrary.controller.form;

import lombok.Data;
import lombok.Getter;

@Data
public class LoginForm {
    private String userId;
    private String password;
}

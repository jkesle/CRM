package jkesle.crm.dto;

public class AuthDTO {
    private final String username;
    private final String password;

    private AuthDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static class AuthDTOBuilder {
        private String username;
        private String password;

        public AuthDTOBuilder withUsername(String uname) {
            username = uname;
            return this;
        }

        public AuthDTOBuilder withPassword(String pword) {
            password = pword;
            return this;
        }

        public AuthDTO build() {
            return new AuthDTO(
                username,
                password
            );
        }

    }
}

package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String lastName;
    private int age;
    private String shippingAddress;
    private String email;
    private String password;
    private UserRole userRole;
}

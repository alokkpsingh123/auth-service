package org.alok.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenDto {
    String token;
    String userId;
    String userName;
    String error;
}

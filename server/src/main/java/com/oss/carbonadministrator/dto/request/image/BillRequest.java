package com.oss.carbonadministrator.dto.request.image;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    @Email
    private String email;

    private int year;

    private int month;
}

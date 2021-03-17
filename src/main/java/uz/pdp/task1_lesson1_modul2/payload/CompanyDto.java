package uz.pdp.task1_lesson1_modul2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    @NotNull(message = "corpName can not be empty !")
    private String corpName;

    @NotNull(message = "directorName can not be empty !")
    private String directorName;

    @NotNull(message = "street can not be empty !")
    private String street;

    @NotNull(message = "homeNumber can not be empty !")
    private Integer homeNumber;
}

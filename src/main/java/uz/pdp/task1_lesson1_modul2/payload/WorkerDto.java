package uz.pdp.task1_lesson1_modul2.payload;

import lombok.Data;

@Data
public class WorkerDto {

    private String name;

    private String phoneNumber;

    private Integer addressId;

    private String Street;

    private Integer HomeNumber;

    private Integer departmentId;
}

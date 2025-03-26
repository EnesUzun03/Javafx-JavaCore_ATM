package com.enesuzun.ibb_ecodation_javafx.dto;

import lombok.*;

//LOMBOK
// Getter-setter
@Getter
@Setter
//Parametreli Constructer
@AllArgsConstructor
//parametresiz Constructer
@NoArgsConstructor
@ToString
@Builder


//UserDTO
public class UserDTO {
    //Fields
    private int id;
    private String userName;
    private String password;
    private String email;

}//END CLASS

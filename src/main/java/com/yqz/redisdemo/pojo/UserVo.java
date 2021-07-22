package com.yqz.redisdemo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    public  static final String Table = "t_user";

    private String name;
    private String address;
    private Integer age;
}

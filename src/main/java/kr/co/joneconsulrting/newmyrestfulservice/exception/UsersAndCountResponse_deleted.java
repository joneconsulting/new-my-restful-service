package kr.co.joneconsulrting.newmyrestfulservice.exception;

import kr.co.joneconsulrting.newmyrestfulservice.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
//@Data
//@Builder
public class UsersAndCountResponse {
    private int count;
    private List<User> users;
}
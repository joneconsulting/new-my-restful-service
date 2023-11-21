package kr.co.joneconsulrting.newmyrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData {
    private int count;
    private List<User> users;
}

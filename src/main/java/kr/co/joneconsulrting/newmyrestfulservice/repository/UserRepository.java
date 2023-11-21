package kr.co.joneconsulrting.newmyrestfulservice.repository;

import kr.co.joneconsulrting.newmyrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}

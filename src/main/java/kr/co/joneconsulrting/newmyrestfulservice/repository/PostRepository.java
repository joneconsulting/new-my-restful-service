package kr.co.joneconsulrting.newmyrestfulservice.repository;

import kr.co.joneconsulrting.newmyrestfulservice.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}

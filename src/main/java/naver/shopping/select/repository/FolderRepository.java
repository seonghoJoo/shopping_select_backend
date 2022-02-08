package naver.shopping.select.repository;

import naver.shopping.select.model.Folder;
import naver.shopping.select.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);

    List<Folder> findAllByUserAndNameIn(User user, List<String> names);
}
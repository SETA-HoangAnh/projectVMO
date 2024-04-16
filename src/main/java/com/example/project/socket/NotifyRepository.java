package com.example.project.socket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {

    @Query("select new com.example.project.socket.NotifyDTO(" +
            "n.notifyId, n.notifyContent, n.createdAt, n.updatedAt, u.userId " +
            ")" +
            "from Notify n left join n.users u where u.userId = ?1 ORDER BY n.notifyId DESC")
    List<NotifyDTO> findAllNotifyByUser(Long userId);

    @Query("select new com.example.project.socket.NotifyDTO(" +
            "n.notifyId, n.notifyContent, n.createdAt, n.updatedAt, n.users.userId " +
            ")" +
            "from Notify n where n.notifyId = ?1")
    NotifyDTO findNotifyById(Long userId);
}

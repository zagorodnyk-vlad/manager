package com.man.ger.manager.dao;


import com.man.ger.manager.entity.Category;
import com.man.ger.manager.entity.CategoryEnumStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

     @Query("select cat from Category cat where cat.category=:name")
    Category findByName(@Param("name")String name);

    @Transactional
    @Modifying
    @Query("delete from Category c where c.category=:name")
    void deleteByName (@Param("name")String name);

    @Transactional
    @Modifying
    @Query("update Category c set c.status=:status where c.category=:name")
    void updateStatus(@Param("name")String name, @Param("status") CategoryEnumStatus status);


}
